package bsd.gradebook.course;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {

    JSONObject classRoot;

    public Course(JSONObject classRoot) {
        this.classRoot = classRoot;
    }

    public String getCourseName() throws JSONException {
        return classRoot.getString("course");
    }

    public Grade getGrade() throws JSONException {
        return new Grade(classRoot.getJSONObject("firstSemester").getString("grade"));
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
            return !classRoot.getJSONObject("firstSemester").getString("grade").isEmpty();
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

                try {
                    double gradeNum = Double.parseDouble(gradeStr);
                    int maxNum = Integer.parseInt(maxStr);

                    double score = gradeNum/maxNum;
                    DecimalFormat format = new DecimalFormat("##0.00");
                    String scoreStr = format.format(score*1000);

                    gradeStr = scoreStr;
                } catch (NumberFormatException e) {
                    Log.d("NUMBER FORMAT", e.toString());
                }

                assignments.add(new Assignment(assignment.getString("name"), assignment.getString("date"),
                        gradeStr, maxStr));
            }
            Collections.reverse(assignments);
            return assignments;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Grade {

        public String grade;
        public String letterGrade;

        public Grade(String gradeString) {
            if (gradeString.isEmpty()) {
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
        public final String grade;
        public final String points;

        public Assignment(String name, String date, String grade, String points) {
            this.name = name;
            this.date = date;
            this.grade = grade;
            this.points = points;
        }
    }
}
