package com.simplelecture.main.model.viewmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raos on 6/28/2016.
 */
public class HomePageResponseModel implements Serializable {

    private int MyCoursesCount;
    private int MyCartCount;
    private List<HomeBannersModel> bannersLst;
    private List<CourseCombos> courseCombosLst;
    private List<HomeCoursesModel> coursesLst;
    private List<HomePopularCoursesModel> homePopularCoursesModelLst;
    private List<HomeTestimonialsModel> homeTestimonialsModelLst;
    private String PageUrlSupport;
    private String PageUrlAboutUs;
    private String PageUrlTermsAndConditions;
    private String PageUrlDisclaimer;
    private String PageUrlPrivacyPolicy;
    private String PageUrlCancellationAndRefundPolicy;
    private String PageUrlShippingAndDeliveryPolicy;
    private boolean IsMobileVerified;



    public HomePageResponseModel() {
    }

    public HomePageResponseModel(int myCoursesCount, int myCartCount, List<HomeBannersModel> bannersLst, List<CourseCombos> courseCombosLst, List<HomeCoursesModel> coursesLst, List<HomePopularCoursesModel> homePopularCoursesModelLst, List<HomeTestimonialsModel> homeTestimonialsModelLst, String pageUrlSupport, String pageUrlAboutUs, String pageUrlTermsAndConditions, String pageUrlDisclaimer, String pageUrlPrivacyPolicy, String pageUrlCancellationAndRefundPolicy, String pageUrlShippingAndDeliveryPolicy, boolean isMobileVerified) {
        MyCoursesCount = myCoursesCount;
        MyCartCount = myCartCount;
        this.bannersLst = bannersLst;
        this.courseCombosLst = courseCombosLst;
        this.coursesLst = coursesLst;
        this.homePopularCoursesModelLst = homePopularCoursesModelLst;
        this.homeTestimonialsModelLst = homeTestimonialsModelLst;
        PageUrlSupport = pageUrlSupport;
        PageUrlAboutUs = pageUrlAboutUs;
        PageUrlTermsAndConditions = pageUrlTermsAndConditions;
        PageUrlDisclaimer = pageUrlDisclaimer;
        PageUrlPrivacyPolicy = pageUrlPrivacyPolicy;
        PageUrlCancellationAndRefundPolicy = pageUrlCancellationAndRefundPolicy;
        PageUrlShippingAndDeliveryPolicy = pageUrlShippingAndDeliveryPolicy;
        IsMobileVerified = isMobileVerified;
    }

    public int getMyCoursesCount() {
        return MyCoursesCount;
    }

    public void setMyCoursesCount(int myCoursesCount) {
        MyCoursesCount = myCoursesCount;
    }

    public int getMyCartCount() {
        return MyCartCount;
    }

    public void setMyCartCount(int myCartCount) {
        MyCartCount = myCartCount;
    }

    public List<HomeBannersModel> getBannersLst() {
        return bannersLst;
    }

    public void setBannersLst(List<HomeBannersModel> bannersLst) {
        this.bannersLst = bannersLst;
    }

    public List<CourseCombos> getCourseCombosLst() {
        return courseCombosLst;
    }

    public void setCourseCombosLst(List<CourseCombos> courseCombosLst) {
        this.courseCombosLst = courseCombosLst;
    }

    public List<HomeCoursesModel> getCoursesLst() {
        return coursesLst;
    }

    public void setCoursesLst(List<HomeCoursesModel> coursesLst) {
        this.coursesLst = coursesLst;
    }

    public List<HomePopularCoursesModel> getHomePopularCoursesModelLst() {
        return homePopularCoursesModelLst;
    }

    public void setHomePopularCoursesModelLst(List<HomePopularCoursesModel> homePopularCoursesModelLst) {
        this.homePopularCoursesModelLst = homePopularCoursesModelLst;
    }

    public List<HomeTestimonialsModel> getHomeTestimonialsModelLst() {
        return homeTestimonialsModelLst;
    }

    public void setHomeTestimonialsModelLst(List<HomeTestimonialsModel> homeTestimonialsModelLst) {
        this.homeTestimonialsModelLst = homeTestimonialsModelLst;
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

    public boolean isMobileVerified() {
        return IsMobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        IsMobileVerified = mobileVerified;
    }

    @Override
    public String toString() {
        return "HomePageResponseModel{" +
                "MyCoursesCount=" + MyCoursesCount +
                ", MyCartCount=" + MyCartCount +
                ", bannersLst=" + bannersLst +
                ", courseCombosLst=" + courseCombosLst +
                ", coursesLst=" + coursesLst +
                ", homePopularCoursesModelLst=" + homePopularCoursesModelLst +
                ", homeTestimonialsModelLst=" + homeTestimonialsModelLst +
                ", PageUrlSupport='" + PageUrlSupport + '\'' +
                ", PageUrlAboutUs='" + PageUrlAboutUs + '\'' +
                ", PageUrlTermsAndConditions='" + PageUrlTermsAndConditions + '\'' +
                ", PageUrlDisclaimer='" + PageUrlDisclaimer + '\'' +
                ", PageUrlPrivacyPolicy='" + PageUrlPrivacyPolicy + '\'' +
                ", PageUrlCancellationAndRefundPolicy='" + PageUrlCancellationAndRefundPolicy + '\'' +
                ", PageUrlShippingAndDeliveryPolicy='" + PageUrlShippingAndDeliveryPolicy + '\'' +
                ", IsMobileVerified=" + IsMobileVerified +
                '}';
    }
}
