package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 9/28/2016.
 */
public class PlaceOrderResponseModel implements Serializable {

    private int OrderId;
    private String PaymentUrl;

    public PlaceOrderResponseModel() {
    }

    public PlaceOrderResponseModel(int orderId, String paymentUrl) {
        OrderId = orderId;
        PaymentUrl = paymentUrl;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getPaymentUrl() {
        return PaymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        PaymentUrl = paymentUrl;
    }

    @Override
    public String toString() {
        return "PlaceOrderResponseModel{" +
                "OrderId=" + OrderId +
                ", PaymentUrl='" + PaymentUrl + '\'' +
                '}';
    }
}
