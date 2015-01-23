package bsd.gradebook.course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                assignments.add(new Assignment(assignment.getString("name"), assignment.getString("date"),
                        assignment.getString("grade"), assignment.getString("max")));
            }
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
        public final String max;

        public Assignment(String name, String date, String grade, String max) {
            this.name = name;
            this.date = date;
            this.grade = grade;
            this.max = max;
        }
    }
}
