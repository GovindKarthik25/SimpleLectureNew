package com.simplelecture.main.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseFaqs;
import com.simplelecture.main.model.viewmodel.CourseMaterials;
import com.simplelecture.main.model.viewmodel.CourseMonths;
import com.simplelecture.main.model.viewmodel.courseFeatures;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raos on 7/10/2016.
 */
public class CourseDetailsController {

    private CourseDetailsResponseModel courseDetailsResponseModel;
    private List<courseFeatures> courseFeaturesLstArray;
    private List<CourseCombos> courseCombosLstArray;
    private JsonArray jarray;
    private ArrayList<CourseFaqs> courseFaqsLstArray;

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private ArrayList<CourseMaterials> courseMaterialsLstArray;
    private ArrayList<CourseMonths> courseMonthsLstArray;


    public CourseDetailsController() {
    }

    public CourseDetailsResponseModel getCourseDetails(String response) {
        try {

            courseDetailsResponseModel = gson.fromJson(response, CourseDetailsResponseModel.class);
            JSONObject jSONObject = new JSONObject(response);

            String myCoursesContent = jSONObject.getString("courseFeatures");
            jarray = parser.parse(myCoursesContent).getAsJsonArray();

            courseFeaturesLstArray = new ArrayList<courseFeatures>();
            for (JsonElement obj : jarray) {
                courseFeatures courseFeaturesObj = gson.fromJson(obj, courseFeatures.class);
                courseFeaturesLstArray.add(courseFeaturesObj);
            }

            String courseCombosContent = jSONObject.getString("courseCombos");
            //Log.i("courseCombosContent", courseCombosContent.toString());
            if (courseCombosContent != null && !courseCombosContent.equals("null")) {
                jarray = parser.parse(courseCombosContent).getAsJsonArray();

                courseCombosLstArray = new ArrayList<CourseCombos>();
                for (JsonElement obj : jarray) {

                    CourseCombos courseCombosObj = gson.fromJson(obj, CourseCombos.class);
                    courseCombosLstArray.add(courseCombosObj);
                }
                courseDetailsResponseModel.setCourseCombos(courseCombosLstArray);

            }
            courseDetailsResponseModel.setCourseFeature(courseFeaturesLstArray);


            courseFaqsLstArray = new ArrayList<CourseFaqs>();

            String courseFaqsContent = jSONObject.getString("courseFaqs");
            jarray = parser.parse(courseFaqsContent).getAsJsonArray();

            for (JsonElement obj : jarray) {
                CourseFaqs CourseFaqsobj = gson.fromJson(obj, CourseFaqs.class);
                courseFaqsLstArray.add(CourseFaqsobj);
            }

            courseDetailsResponseModel.setCourseFaqs(courseFaqsLstArray);

            String courseMaterialsContent = jSONObject.getString("CourseMaterials");
            jarray = parser.parse(courseMaterialsContent).getAsJsonArray();

            courseMaterialsLstArray = new ArrayList<CourseMaterials>();
            for (JsonElement obj : jarray) {
                CourseMaterials courseMaterialsObj = gson.fromJson(obj, CourseMaterials.class);
                courseMaterialsLstArray.add(courseMaterialsObj);
            }

            courseDetailsResponseModel.setCourseMaterials(courseMaterialsLstArray);

            String monthsContent = jSONObject.getString("Months");
            jarray = parser.parse(monthsContent).getAsJsonArray();

            courseMonthsLstArray = new ArrayList<CourseMonths>();
            for (JsonElement obj : jarray) {
                CourseMonths courseMonthsObj = gson.fromJson(obj, CourseMonths.class);
                courseMonthsLstArray.add(courseMonthsObj);
            }

            courseDetailsResponseModel.setCourseMonths(courseMonthsLstArray);


        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e.toString());
        }
        return courseDetailsResponseModel;
    }

    public List<ChaptersResponseModel> getChaptersResponse(String response) {

        List<ChaptersResponseModel> chaptersResponseModelLstArray;
        try {

            jarray = parser.parse(response).getAsJsonArray();

            chaptersResponseModelLstArray = new ArrayList<ChaptersResponseModel>();
            for (JsonElement obj : jarray) {
                ChaptersResponseModel chaptersResponseModelobj = gson.fromJson(obj, ChaptersResponseModel.class);
                chaptersResponseModelLstArray.add(chaptersResponseModelobj);
            }


        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e.toString());
        }
        return chaptersResponseModelLstArray;
    }

}
