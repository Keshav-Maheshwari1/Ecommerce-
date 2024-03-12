package com.project.ecommerce.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    public String id;
    public String entity;
    public String name;
    public String last4;
    public String network;
    public String type;
    public Object issuer;
    public boolean international;
    public boolean emi;
    public String sub_type;
    public Object token_iin;
}
