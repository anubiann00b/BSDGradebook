package bsd.gradebook.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import bsd.gradebook.Course;
import bsd.gradebook.CoursesManager;
import bsd.gradebook.R;

public class ClassFragment extends DialogFragment {

    public static final String COURSE_INDEX = "COURSE_INDEX";
    public static final String SEMESTER_ONE = "SEMESTER_ONE";

    Course course;

    public ClassFragment() {
    }

    public void setArguments(Bundle args) {
        course = CoursesManager.getInstance().getSemester(args.getBoolean(SEMESTER_ONE, true)).get(args.getInt(COURSE_INDEX));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class, container, false);

        TextView text = (TextView) rootView.findViewById(R.id.class_text_view);
        try {
            text.setText(course.getCourseName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
