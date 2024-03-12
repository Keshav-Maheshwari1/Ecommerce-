package com.project.ecommerce.controllers;

import com.project.ecommerce.payments.Root;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class PaymentController {
    // Constructor with dependency injection for RazorpayClient
    @Value("${rzp_key_id}")
    private String keyId;
    @Value("${rzp_key_secret}")
    private String secretKey;

    Logger logger = LoggerFactory.getLogger(PaymentController.class);


    private final Map<Object,Object>  mapPayment = new HashMap<>();

    // Generating a unique order id for the given order
    @GetMapping("/payment/{amount}")
    public ResponseEntity<String> payment(@PathVariable String amount){
        try{

            Date date = new Date();

            RazorpayClient razorpayClient = new RazorpayClient(keyId,secretKey);
            JSONObject orderRequest = new JSONObject();
            Map<Object, Object> mapOrder = new HashMap<>();

            orderRequest.put("amount", amount);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt", "order_receipt_11");
            mapOrder.put("driverName","Piyush");
            mapOrder.put("DriverContact","9999999999");
            mapOrder.put("CreatedAt", date.toString());
            orderRequest.put("notes",new JSONObject(mapOrder));

            Order order = razorpayClient.orders.create(orderRequest);
            String orderId = order.get("id");

            return new ResponseEntity<>(orderId, HttpStatus.ACCEPTED);
        }catch(RazorpayException e){
            return new ResponseEntity<>("Payment Failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/razorpay-webhook")
    public ResponseEntity<String> handleRazorpayWebhook(@RequestBody(required = false) Root webPayload,
                                                        @RequestHeader(value = "x-razorpay-signature",required = false) String signature) {
        try {

            mapPayment.put("payment_id", webPayload.payload.payment.entity.id);
            mapPayment.put("order_id", webPayload.payload.payment.entity.getOrder_id());
            mapPayment.put("amount", webPayload.payload.payment.entity.getAmount());
            mapPayment.put("status", webPayload.payload.payment.entity.getStatus());
            mapPayment.put("payment_type", webPayload.payload.payment.entity.getMethod());
            mapPayment.put("invoice_id", webPayload.payload.payment.entity.getInvoice_id());
            mapPayment.put("captured",webPayload.payload.payment.entity.isCaptured());
            mapPayment.put("fee", webPayload.payload.payment.entity.getFee());
            mapPayment.put("tax", webPayload.payload.payment.entity.getTax());
            System.out.println(mapPayment);
            return new ResponseEntity<>("Payment Successfully Accepted", HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order")
    public ResponseEntity<Map<Object,Object>> getOrderStatus(){
        try{
            logger.info(mapPayment.toString());
            return new ResponseEntity<>(mapPayment,HttpStatus.OK);
        }catch (Exception e){
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
