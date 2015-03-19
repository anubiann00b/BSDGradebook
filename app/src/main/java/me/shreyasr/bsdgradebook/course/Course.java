package me.shreyasr.bsdgradebook.course;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {

    private final JSONObject classRoot;

    public Course(JSONObject classRoot) {
        this.classRoot = classRoot;
    }

    public String getCourseName() throws JSONException {
        return classRoot.getString("course");
    }

    public Grade getGrade() throws JSONException {
        return new Grade(classRoot.getJSONObject(isFirstSemester()?"firstSemester":"secondSemester").getString("grade"));
    }

    public int getPeriod() {
        try {
            return classRoot.getInt("period");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isFirstSemester() {
        try {
            return classRoot.getJSONObject("firstSemester").getString("grade").length() != 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isSecondSemester() {
        return !isFirstSemester();
    }

    public List<Assignment> getAssignments() {
        try {
            JSONArray classes = classRoot.getJSONObject(isFirstSemester()?"firstSemester":"secondSemester").getJSONObject("assignments").getJSONArray("assignments");

            List<Assignment> assignments = new ArrayList<>();
            for (int i=0;i<classes.length();i++) {
                JSONObject assignment = classes.getJSONObject(i);

                String max = assignment.getString("max");
                String maxStr = max.substring(0, max.indexOf('.'));
                String gradeStr = assignment.getString("grade");
                String scoreStr = gradeStr;

                try {
                    double gradeNum = Double.parseDouble(gradeStr);
                    int maxNum = Integer.parseInt(maxStr);

                    double score = gradeNum/maxNum;
                    DecimalFormat format = new DecimalFormat("##0");
                    scoreStr = format.format(score*100);
                } catch (NumberFormatException e) {
                    Log.d("NUMBER FORMAT", e.toString());
                }

                assignments.add(new Assignment(assignment.getString("name"), assignment.getString("date"),
                        scoreStr, gradeStr, "/" + maxStr));
            }
            Collections.reverse(assignments);
            return assignments;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Grade {

        public final String grade;
        public final String letterGrade;

        public Grade(String gradeString) {
            if (gradeString.length() == 0) {
                grade = "";
                letterGrade = "";
            } else {
                grade = gradeString.substring(0, gradeString.indexOf('/'));
                letterGrade = gradeString.substring(gradeString.indexOf('/')+1);
            }
        }
    }

    public class Assignment {

        public final String name;
        public final String date;
        public final String percent;
        public final String grade;
        public final String maxPoints;

        public Assignment(String name, String date, String percent, String grade, String maxPoints) {
            this.name = name;
            this.date = date;
            this.percent = percent;
            this.grade = grade;
            this.maxPoints = maxPoints;
        }
    }
}
