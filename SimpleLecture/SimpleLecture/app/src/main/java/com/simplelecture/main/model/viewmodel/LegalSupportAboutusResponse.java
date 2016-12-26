package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;

/**
 * Created by Raos on 12/25/2016.
 */

public class LegalSupportAboutusResponse implements Serializable {

    private String PageUrlSupport;
    private String PageUrlAboutUs;
    private String PageUrlTermsAndConditions;
    private String PageUrlDisclaimer;
    private String PageUrlPrivacyPolicy;
    private String PageUrlCancellationAndRefundPolicy;
    private String PageUrlShippingAndDeliveryPolicy;


    public LegalSupportAboutusResponse() {
    }

    public LegalSupportAboutusResponse(String pageUrlSupport, String pageUrlAboutUs, String pageUrlTermsAndConditions, String pageUrlDisclaimer, String pageUrlPrivacyPolicy, String pageUrlCancellationAndRefundPolicy, String pageUrlShippingAndDeliveryPolicy) {
        PageUrlSupport = pageUrlSupport;
        PageUrlAboutUs = pageUrlAboutUs;
        PageUrlTermsAndConditions = pageUrlTermsAndConditions;
        PageUrlDisclaimer = pageUrlDisclaimer;
        PageUrlPrivacyPolicy = pageUrlPrivacyPolicy;
        PageUrlCancellationAndRefundPolicy = pageUrlCancellationAndRefundPolicy;
        PageUrlShippingAndDeliveryPolicy = pageUrlShippingAndDeliveryPolicy;
    }

    public String getPageUrlSupport() {
        return PageUrlSupport;
    }

    public void setPageUrlSupport(String pageUrlSupport) {
        PageUrlSupport = pageUrlSupport;
    }

    public String getPageUrlAboutUs() {
        return PageUrlAboutUs;
    }

    public void setPageUrlAboutUs(String pageUrlAboutUs) {
        PageUrlAboutUs = pageUrlAboutUs;
    }

    public String getPageUrlTermsAndConditions() {
        return PageUrlTermsAndConditions;
    }

    public void setPageUrlTermsAndConditions(String pageUrlTermsAndConditions) {
        PageUrlTermsAndConditions = pageUrlTermsAndConditions;
    }

    public String getPageUrlDisclaimer() {
        return PageUrlDisclaimer;
    }

    public void setPageUrlDisclaimer(String pageUrlDisclaimer) {
        PageUrlDisclaimer = pageUrlDisclaimer;
    }

    public String getPageUrlPrivacyPolicy() {
        return PageUrlPrivacyPolicy;
    }

    public void setPageUrlPrivacyPolicy(String pageUrlPrivacyPolicy) {
        PageUrlPrivacyPolicy = pageUrlPrivacyPolicy;
    }

    public String getPageUrlCancellationAndRefundPolicy() {
        return PageUrlCancellationAndRefundPolicy;
    }

    public void setPageUrlCancellationAndRefundPolicy(String pageUrlCancellationAndRefundPolicy) {
        PageUrlCancellationAndRefundPolicy = pageUrlCancellationAndRefundPolicy;
    }

    public String getPageUrlShippingAndDeliveryPolicy() {
        return PageUrlShippingAndDeliveryPolicy;
    }

    public void setPageUrlShippingAndDeliveryPolicy(String pageUrlShippingAndDeliveryPolicy) {
        PageUrlShippingAndDeliveryPolicy = pageUrlShippingAndDeliveryPolicy;
    }

    @Override
    public String toString() {
        return "LegalSupportAboutusResponse{" +
                "PageUrlSupport='" + PageUrlSupport + '\'' +
                ", PageUrlAboutUs='" + PageUrlAboutUs + '\'' +
                ", PageUrlTermsAndConditions='" + PageUrlTermsAndConditions + '\'' +
                ", PageUrlDisclaimer='" + PageUrlDisclaimer + '\'' +
                ", PageUrlPrivacyPolicy='" + PageUrlPrivacyPolicy + '\'' +
                ", PageUrlCancellationAndRefundPolicy='" + PageUrlCancellationAndRefundPolicy + '\'' +
                ", PageUrlShippingAndDeliveryPolicy='" + PageUrlShippingAndDeliveryPolicy + '\'' +
                '}';
    }
}

