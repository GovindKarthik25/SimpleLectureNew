package com.simplelecture.main.model.viewmodel;

/**
 * Created by Raos on 9/28/2016.
 */
public class CheckOrderStatusResponseModel {

    private String OrderId;
    private String OrderStatusId;
    private String OrderStatus;

    public CheckOrderStatusResponseModel() {
    }

    public CheckOrderStatusResponseModel(String orderId, String orderStatusId, String orderStatus) {
        OrderId = orderId;
        OrderStatusId = orderStatusId;
        OrderStatus = orderStatus;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderStatusId() {
        return OrderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        OrderStatusId = orderStatusId;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "CheckOrderStatusResponseModel{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderStatusId='" + OrderStatusId + '\'' +
                ", OrderStatus='" + OrderStatus + '\'' +
                '}';
    }
}
