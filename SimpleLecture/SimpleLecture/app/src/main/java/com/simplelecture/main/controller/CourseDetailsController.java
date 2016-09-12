package com.simplelecture.main.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.model.viewmodel.Answer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseFaqs;
import com.simplelecture.main.model.viewmodel.CourseMaterials;
import com.simplelecture.main.model.viewmodel.CourseMonths;
import com.simplelecture.main.model.viewmodel.courseFeatures;

import org.json.JSONArray;
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


           /* String courseFaqsContent = jSONObject.getString("courseFaqs");
            jarray = parser.parse(courseFaqsContent).getAsJsonArray();*/

            courseFaqsLstArray = new ArrayList<CourseFaqs>();
            ArrayList<Answer> answerModelLstArray = new ArrayList<Answer>();

           /* for (JsonElement obj : jarray) {
                CourseFaqs courseFaqsObj = gson.fromJson(obj, CourseFaqs.class);
                courseFaqsLstArray.add(courseFaqsObj);
            }*/


            JSONArray eventDetails = jSONObject.getJSONArray("courseFaqs");

            for (int i = 0; i < eventDetails.length(); i++) {
                JSONObject eachData = eventDetails.getJSONObject(i);
                CourseFaqs courseFaqsObj = new CourseFaqs();
                courseFaqsObj.setId(eachData.getString("Id"));
                courseFaqsObj.setName(eachData.getString("Name"));
                courseFaqsLstArray.add(courseFaqsObj);
              //  Log.i("Answer", eachData.getString("Answer"));
                String courseFaqsContent = eachData.getString("Answer");
                jarray = parser.parse(courseFaqsContent).getAsJsonArray();
              //  JSONArray answer = eachData.getJSONArray("Answer");

                for (JsonElement obj : jarray) {
                    Answer answerModelObj = gson.fromJson(obj, Answer.class);
                    answerModelLstArray.add(answerModelObj);
                }
               /* for (int j = 0; j < jarray.size(); j++) {
                    JSONObject eachDataAnswer = answer.getJSONObject(j);
                    Answer answerModelObj = new Answer();

                    answerModelObj.setId(eachDataAnswer.getString("Id"));
                    answerModelObj.setName(eachDataAnswer.getString("Name"));
                    answerModelLstArray.add(answerModelObj);

                }*/

            }

            for (int i = 0; i < courseFaqsLstArray.size(); i++) {
                for (int k = 0; k < answerModelLstArray.size(); k++) {
                    if (courseFaqsLstArray.get(i).getId() == answerModelLstArray.get(k).getId()) {
                        courseFaqsLstArray.get(i).setAnswerModel(answerModelLstArray);
                    }
                }
            }

            Log.i("courseFaqsLstArray", courseFaqsLstArray.toString());


          /*  ArrayList<Answer> answerModelLstArray = new ArrayList<Answer>();

            JSONArray dataContent = jSONObject.getJSONArray("Answer");
            for(int i=0;i<dataContent.length();i++){
                JSONObject eachData = dataContent.getJSONObject(i);
                Answer answerModelObj = new Answer();
                answerModelObj.setId(eachData.getString("Id"));
                answerModelObj.setName(eachData.getString("Name"));
                answerModelLstArray.add(answerModelObj);

            }*/

            courseFaqsLstArray.get(0).setAnswerModel(answerModelLstArray);
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
