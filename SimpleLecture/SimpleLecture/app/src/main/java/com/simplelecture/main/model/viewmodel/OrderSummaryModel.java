package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by M1032185 on 7/30/2016.
 */
public class OrderSummaryModel implements Serializable {


    private String SubTotalPrice;
    private String TaxPrice;
    private String TotalOrderPrice;
    private List<OrderSummaryListModel> orderSummaryListModel;
    private PromoDetails promoDetails;
    private boolean IsContainsCourseMaterial;

    public OrderSummaryModel() {
    }

    public OrderSummaryModel(String subTotalPrice, String taxPrice, String totalOrderPrice, List<OrderSummaryListModel> orderSummaryListModel, PromoDetails promoDetails, boolean isContainsCourseMaterial) {
        SubTotalPrice = subTotalPrice;
        TaxPrice = taxPrice;
        TotalOrderPrice = totalOrderPrice;
        this.orderSummaryListModel = orderSummaryListModel;
        this.promoDetails = promoDetails;
        IsContainsCourseMaterial = isContainsCourseMaterial;
    }

    public String getSubTotalPrice() {
        return SubTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        SubTotalPrice = subTotalPrice;
    }

    public String getTaxPrice() {
        return TaxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        TaxPrice = taxPrice;
    }

    public String getTotalOrderPrice() {
        return TotalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        TotalOrderPrice = totalOrderPrice;
    }

    public List<OrderSummaryListModel> getOrderSummaryListModel() {
        return orderSummaryListModel;
    }

    public void setOrderSummaryListModel(List<OrderSummaryListModel> orderSummaryListModel) {
        this.orderSummaryListModel = orderSummaryListModel;
    }

    public PromoDetails getPromoDetails() {
        return promoDetails;
    }

    public void setPromoDetails(PromoDetails promoDetails) {
        this.promoDetails = promoDetails;
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
                ", TaxPrice='" + TaxPrice + '\'' +
                ", TotalOrderPrice='" + TotalOrderPrice + '\'' +
                ", orderSummaryListModel=" + orderSummaryListModel +
                ", promoDetails=" + promoDetails +
                ", IsContainsCourseMaterial=" + IsContainsCourseMaterial +
                '}';
    }
}