package bsd.gradebook.course;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import bsd.gradebook.app.login.ConnectionManager;

public class CoursesManager {

    private static final CoursesManager cm = new CoursesManager();

    public static CoursesManager getInstance() {
        return cm;
    }

    List<Course> classes;

    public void initialize() {
        if (classes != null)
            return;
        classes = new ArrayList<>();
        try {
            Log.d("JSON", ConnectionManager.cache.toString());
            JSONArray courses = ConnectionManager.cache.getJSONArray("courses");
            for (int i=0;i<courses.length();i++) {
                classes.add(new Course(courses.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getSemesterOne() {
        List<Course> semesterOneCourses = new ArrayList<>();
        for (Course c : classes) {
            if (c.isFirstSemester())
                semesterOneCourses.add(c);
        }
        return semesterOneCourses;
    }

    public List<Course> getSemesterTwo() {
        List<Course> semesterTwoCourses = new ArrayList<>();
        for (Course c : classes) {
            if (c.isSecondSemester())
                semesterTwoCourses.add(c);
        }
        return semesterTwoCourses;
    }

    public List<Course> getSemester(boolean firstSemester) {
        return firstSemester ? getSemesterOne() : getSemesterTwo();
    }
}
