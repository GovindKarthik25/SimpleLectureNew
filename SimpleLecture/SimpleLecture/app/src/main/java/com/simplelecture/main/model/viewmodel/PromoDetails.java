package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 9/5/2016.
 */
public class PromoDetails implements Serializable {


    private String PromoDiscountPercentage;
    private String PromoDiscountPrice;
    private String FreeTrailValidity;
    private boolean IsFreeTrail;

    public PromoDetails(String promoDiscountPercentage, String promoDiscountPrice, String freeTrailValidity, boolean isFreeTrail) {
        PromoDiscountPercentage = promoDiscountPercentage;
        PromoDiscountPrice = promoDiscountPrice;
        FreeTrailValidity = freeTrailValidity;
        IsFreeTrail = isFreeTrail;
    }

    public String getPromoDiscountPercentage() {
        return PromoDiscountPercentage;
    }

    public void setPromoDiscountPercentage(String promoDiscountPercentage) {
        PromoDiscountPercentage = promoDiscountPercentage;
    }

    public String getFreeTrailValidity() {
        return FreeTrailValidity;
    }

    public void setFreeTrailValidity(String freeTrailValidity) {
        FreeTrailValidity = freeTrailValidity;
    }

    public String getPromoDiscountPrice() {
        return PromoDiscountPrice;
    }

    public void setPromoDiscountPrice(String promoDiscountPrice) {
        PromoDiscountPrice = promoDiscountPrice;
    }

    public boolean isFreeTrail() {
        return IsFreeTrail;
    }

    public void setFreeTrail(boolean freeTrail) {
        IsFreeTrail = freeTrail;
    }

    @Override
    public String toString() {
        return "PromoDetails{" +
                "PromoDiscountPercentage='" + PromoDiscountPercentage + '\'' +
                ", PromoDiscountPrice='" + PromoDiscountPrice + '\'' +
                ", FreeTrailValidity='" + FreeTrailValidity + '\'' +
                ", IsFreeTrail=" + IsFreeTrail +
                '}';
    }
}
