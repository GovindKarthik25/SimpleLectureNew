package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by M1032185 on 7/30/2016.
 */
public class OrderSummaryModel implements Serializable {


    private String SubTotalPrice;
    private String PromocodeDiscountPrice;
    private String TaxPrice;
    private String TotalPrice;
    private List<OrderSummaryListModel> orderSummaryListModel;
    private boolean IsContainsCourseMaterial;

    public OrderSummaryModel() {
    }

    public OrderSummaryModel(String subTotalPrice, String promocodeDiscountPrice, String taxPrice, String totalPrice, List<OrderSummaryListModel> orderSummaryListModel, boolean isContainsCourseMaterial) {
        SubTotalPrice = subTotalPrice;
        PromocodeDiscountPrice = promocodeDiscountPrice;
        TaxPrice = taxPrice;
        TotalPrice = totalPrice;
        this.orderSummaryListModel = orderSummaryListModel;
        IsContainsCourseMaterial = isContainsCourseMaterial;
    }

    public String getSubTotalPrice() {
        return SubTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        SubTotalPrice = subTotalPrice;
    }

    public String getPromocodeDiscountPrice() {
        return PromocodeDiscountPrice;
    }

    public void setPromocodeDiscountPrice(String promocodeDiscountPrice) {
        PromocodeDiscountPrice = promocodeDiscountPrice;
    }

    public String getTaxPrice() {
        return TaxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        TaxPrice = taxPrice;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public List<OrderSummaryListModel> getOrderSummaryListModel() {
        return orderSummaryListModel;
    }

    public void setOrderSummaryListModel(List<OrderSummaryListModel> orderSummaryListModel) {
        this.orderSummaryListModel = orderSummaryListModel;
    }

    public boolean isContainsCourseMaterial() {
        return IsContainsCourseMaterial;
    }

    public void setContainsCourseMaterial(boolean containsCourseMaterial) {
        IsContainsCourseMaterial = containsCourseMaterial;
    }

    @Override
    public String toString() {
        return "OrderSummaryModel{" +
                "SubTotalPrice='" + SubTotalPrice + '\'' +
                ", PromocodeDiscountPrice='" + PromocodeDiscountPrice + '\'' +
                ", TaxPrice='" + TaxPrice + '\'' +
                ", TotalPrice='" + TotalPrice + '\'' +
                ", orderSummaryListModel=" + orderSummaryListModel +
                ", IsContainsCourseMaterial=" + IsContainsCourseMaterial +
                '}';
    }
}