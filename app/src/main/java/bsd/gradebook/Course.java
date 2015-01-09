package bsd.gradebook;

import org.json.JSONException;
import org.json.JSONObject;

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

    public int getPeriod() throws JSONException {
        return classRoot.getInt("period");
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
        try {
            return !classRoot.getJSONObject("secondSemester").getString("grade").isEmpty();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class Grade {

        public String grade;
        public String letterGrade;

        public Grade(String gradeString) {
            if (gradeString.isEmpty()) {
                grade = "00.00";
                letterGrade = "Q";
            } else {
                grade = gradeString.substring(0, gradeString.indexOf('/'));
                letterGrade = gradeString.substring(gradeString.indexOf('/')+1);
            }
        }
    }
}
