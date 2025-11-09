package it.smartcommunitylabdhub.purchases.models.dtos;

public class PaymentInfoDTO {
    
    private String paymentId;
    private String status;
    private String message;
    private Double amount;
    private String currency;

    // Constructors
    public PaymentInfoDTO() {}

    public PaymentInfoDTO(String paymentId, String status, String message, Double amount, String currency) {
        this.paymentId = paymentId;
        this.status = status;
        this.message = message;
        this.amount = amount;
        this.currency = currency;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}