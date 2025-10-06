package it.smartcommunitylabdhub.purchases.services;


import it.smartcommunitylabdhub.purchases.feigns.OpenFeignCatalogService;
import it.smartcommunitylabdhub.purchases.feigns.OpenFeignPaymentService;
import it.smartcommunitylabdhub.purchases.models.Cart;
import it.smartcommunitylabdhub.purchases.models.Order;
import it.smartcommunitylabdhub.purchases.models.Product;
import it.smartcommunitylabdhub.purchases.models.dtos.CardDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.OrderDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentInfoDTO;
import it.smartcommunitylabdhub.purchases.repositories.OrderRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OpenFeignCatalogService catalogService;

    @Autowired
    OpenFeignPaymentService paymentService;

    @Autowired
    CartService cartService;

    @Autowired
    private RestTemplate restTemplate;

    public OrderDTO checkout(
            @NonNull String userId, CardDTO card) {
        // get Cart id
        Cart cart = cartService.getCart(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Make Order (status=pending)
        Order order = orderRepository.save(Order.builder()
                .userId(userId)
                .items(cart.getItems())
                .totalPrice(cart.getTotalPrice())
                .status("pending")
                .build());

        // Store Order
        orderRepository.save(order);

        // Call Payment Service con OpenFeign
        PaymentDTO payment = paymentService.doPayment(
                PaymentInfoDTO.builder()
                        .userId(userId)
                        .orderId(order.getId())
                        .paymentMethod("card")
                        .card(card)
                        .build()
        );


        if(payment.getStatus().equals("paid")) {
            // Update Order with payment status=paid
            order.setStatus(payment.getStatus());
            orderRepository.save(order);

            // aggiornare la quantita dei prodotti.
            order.getItems().forEach(item -> {
                Product product = catalogService.getProductFromCatalog(item.getProduct().getId());
                catalogService.changeProductAvailability(
                        item.getProduct().getId(),
                        product.getAvailability() - item.getQuantity());
            });

            // ritorna un orderDTO con i payment info
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setUserId(order.getUserId());
            orderDTO.setItems(order.getItems());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setPayment(payment);

            return orderDTO;
        }
        else {
            throw new RuntimeException("Payment failed");
        }
    }

    public List<OrderDTO> getOrders() {

        return orderRepository.findAll()
                .stream().map(order -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setId(order.getId());
                    orderDTO.setUserId(order.getUserId());
                    orderDTO.setItems(order.getItems());
                    orderDTO.setTotalPrice(order.getTotalPrice());
                    orderDTO.setStatus(order.getStatus());
                    return orderDTO;
                }).toList();
    }

    public OrderDTO getOrder(String userId, String orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setId(order.getId());
                    orderDTO.setUserId(order.getUserId());
                    orderDTO.setItems(order.getItems());
                    orderDTO.setTotalPrice(order.getTotalPrice());
                    orderDTO.setStatus(order.getStatus());
                    return orderDTO;
                }).orElse(null);
    }
}
