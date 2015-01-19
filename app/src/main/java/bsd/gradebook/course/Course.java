package bsd.gradebook.course;

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
}
