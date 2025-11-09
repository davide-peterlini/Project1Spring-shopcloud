package it.smartcommunitylabdhub.purchases.services;

import it.smartcommunitylabdhub.purchases.feigns.OpenFeignPaymentService;
import it.smartcommunitylabdhub.purchases.models.Item;
import it.smartcommunitylabdhub.purchases.models.Order;
import it.smartcommunitylabdhub.purchases.models.dtos.CartDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.OrderDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentInfoDTO;
import it.smartcommunitylabdhub.purchases.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OpenFeignPaymentService paymentService;

    public OrderService(OrderRepository orderRepository, 
                       CartService cartService, 
                       OpenFeignPaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.paymentService = paymentService;
    }

    public List<OrderDTO> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(this::convertToDTO);
    }

    public OrderDTO createOrder(String userId, PaymentDTO paymentDTO) {
        // Get cart for user
        Optional<CartDTO> cartDTO = cartService.getCartByUserId(userId);
        
        if (cartDTO.isEmpty() || cartDTO.get().getItems() == null || cartDTO.get().getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty for user: " + userId);
        }

        // Process payment
        PaymentInfoDTO paymentInfo;
        try {
            paymentInfo = paymentService.processPayment(paymentDTO);
        } catch (Exception e) {
            throw new RuntimeException("Payment processing failed: " + e.getMessage());
        }
        
        if (!"SUCCESS".equals(paymentInfo.getStatus())) {
            throw new RuntimeException("Payment failed: " + paymentInfo.getMessage());
        }

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(cartDTO.get().getTotalPrice());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentId(paymentInfo.getPaymentId());

        // Convert cart items to order items
        List<Item> orderItems = cartDTO.get().getItems().stream()
                .map(itemDTO -> {
                    Item item = new Item();
                    item.setProductId(itemDTO.getProductId());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setPrice(itemDTO.getPrice());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear cart after successful order
        try {
            cartService.deleteCartByUserId(userId);
        } catch (Exception e) {
            // Log error but don't fail the order
            System.err.println("Failed to clear cart for user " + userId + ": " + e.getMessage());
        }

        return convertToDTO(savedOrder);
    }

    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            try {
                order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
                Order savedOrder = orderRepository.save(order);
                return convertToDTO(savedOrder);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid order status: " + status);
            }
        }
        
        throw new RuntimeException("Order not found with id: " + orderId);
    }

    public void cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            
            if (order.getStatus() == Order.OrderStatus.PENDING || order.getStatus() == Order.OrderStatus.CONFIRMED) {
                order.setStatus(Order.OrderStatus.CANCELLED);
                orderRepository.save(order);
            } else {
                throw new RuntimeException("Cannot cancel order with status: " + order.getStatus());
            }
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    public List<OrderDTO> getOrdersByStatus(String status) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderRepository.findByStatus(orderStatus);
            return orders.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus() != null ? order.getStatus().toString() : null);
        dto.setOrderDate(order.getOrderDate());
        dto.setPaymentId(order.getPaymentId());
        
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            List<OrderDTO.ItemDTO> itemDTOs = order.getItems().stream()
                    .map(this::convertItemToDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        } else {
            dto.setItems(new ArrayList<>());
        }
        
        return dto;
    }

    private OrderDTO.ItemDTO convertItemToDTO(Item item) {
        OrderDTO.ItemDTO dto = new OrderDTO.ItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }
}