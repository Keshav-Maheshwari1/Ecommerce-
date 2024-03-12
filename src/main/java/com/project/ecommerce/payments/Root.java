package com.project.ecommerce.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Root {
    public String entity;
    public String account_id;
    public String event;
    public ArrayList<String> contains;
    public PaymentPayload payload;
    public int created_at;
}
