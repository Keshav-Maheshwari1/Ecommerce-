package com.project.ecommerce.controllers;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class PaymentController {
    // Constructor with dependency injection for RazorpayClient
    @Value("${rzp_key_id}")
    private String keyId;
    @Value("${rzp_key_secret}")
    private String secretKey;

    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    // Generating a unique order id for the given order
    @GetMapping("/payment/{amount}")
    public ResponseEntity<String> payment(@PathVariable String amount){
        try{
            RazorpayClient razorpayClient = new RazorpayClient(keyId,secretKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt", "order_receipt_11");
            Order order = razorpayClient.orders.create(orderRequest);
            String orderId = order.get("id");
            return new ResponseEntity<>(orderId, HttpStatus.ACCEPTED);
        }catch(RazorpayException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
    @PostMapping("/razorpay-webhook")
    public ResponseEntity<String> handleRazorpayWebhook(@RequestBody(required = false) String webhookPayload,
                                                        @RequestHeader(value = "x-razorpay-signature",required = false) String signature) {
        try {
            logger.info("Hook Called");
            return new ResponseEntity<>("Payment Successfully Accepted", HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
