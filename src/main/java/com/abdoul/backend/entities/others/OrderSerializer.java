package com.abdoul.backend.entities.others;

import java.io.IOException;

import com.abdoul.backend.entities.Order;
import com.abdoul.backend.entities.OrderProduct;
import com.abdoul.backend.entities.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class OrderSerializer extends JsonSerializer<Order> {
    @Override
    public void serialize(Order order, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", order.getId().toString());
        jsonGenerator.writeStringField("orderDate", order.getOrderDate().toString());
        jsonGenerator.writeStringField("status", order.getStatus().name());
        jsonGenerator.writeNumberField("amount", order.getAmount());

        if(order.getOrderProducts() != null){
            jsonGenerator.writeArrayFieldStart("orderProducts");
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("id", orderProduct.getId().toString());
                jsonGenerator.writeStringField("productId", orderProduct.getProduct().getId().toString());
                jsonGenerator.writeStringField("productName", orderProduct.getProduct().getName());
                jsonGenerator.writeNumberField("price", orderProduct.getProduct().getPrice());
                if (!orderProduct.getProduct().getImages().isEmpty()) {
                    jsonGenerator.writeStringField("image", orderProduct.getProduct().getImages().get(0).getUrl());
                } else {
                    jsonGenerator.writeStringField("image", ""); // or handle it as you prefer
                }
                jsonGenerator.writeNumberField("quantity", orderProduct.getQuantity());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }else{
            jsonGenerator.writeArrayFieldStart("orderProducts");
            jsonGenerator.writeEndArray();
        }
        
        jsonGenerator.writeObjectFieldStart("user");
        jsonGenerator.writeStringField("id", order.getUser().getUserId().toString());
        jsonGenerator.writeStringField("firstname", order.getUser().getFirstname());
        jsonGenerator.writeStringField("lastname", order.getUser().getLastname());
        jsonGenerator.writeStringField("phonenumber", order.getUser().getPhoneNumber());
        jsonGenerator.writeStringField("profile", order.getUser().getProfileImage());
        /*  */
        if(order.getUser().getDefaultAddress() != null){
            jsonGenerator.writeObjectFieldStart("address");
            jsonGenerator.writeStringField("id", order.getUser().getDefaultAddress().getId().toString());
            jsonGenerator.writeStringField("city", order.getUser().getDefaultAddress().getCity());
            jsonGenerator.writeStringField("country", order.getUser().getDefaultAddress().getCountry());
            jsonGenerator.writeStringField("state", order.getUser().getDefaultAddress().getState());
            jsonGenerator.writeStringField("street", order.getUser().getDefaultAddress().getStreet());
            jsonGenerator.writeStringField("zipcode", order.getUser().getDefaultAddress().getZipCode());
            jsonGenerator.writeEndObject();
        }else{
            jsonGenerator.writeStringField("address", null);
        }
        
        /*  */
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}
