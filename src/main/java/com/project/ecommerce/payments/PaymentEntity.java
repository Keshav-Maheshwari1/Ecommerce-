package com.project.ecommerce.payments;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentEntity {
    public String id;
    public String entity;
    public int amount;
    public String currency;
    public String status;
    public String order_id;
    public Object invoice_id;
    public boolean international;
    public String method;
    public int amount_refunded;
    public Object refund_status;
    public boolean captured;
    public String description;
    public String card_id;
    public Card card;
    public Object bank;
    public Object wallet;
    public Object vpa;
    public String email;
    public String contact;
    public Notes notes;
    public int fee;
    public int tax;
    public Object error_code;
    public Object error_description;
    public Object error_source;
    public Object error_step;
    public Object error_reason;
    public AcquirerData acquirer_data;
    public int created_at;
    public Object reward;
    public int base_amount;
}
