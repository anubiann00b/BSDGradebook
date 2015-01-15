package bsd.gradebook.fragment;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONException;

import bsd.gradebook.ApplicationWrapper;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
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

    public void onResume() {
        super.onResume();
        WindowManager wm = (WindowManager) ApplicationWrapper.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) (size.x * 0.9);
        int height = (int) (size.y * 0.9);
        getDialog().getWindow().setLayout(width, height);
    }
}
