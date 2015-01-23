package bsd.gradebook.gradebook;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import bsd.gradebook.ApplicationWrapper;
import bsd.gradebook.R;
import bsd.gradebook.course.Course;
import bsd.gradebook.course.CoursesManager;

public class ClassFragment extends DialogFragment {

    public static final String COURSE_INDEX = "COURSE_INDEX";

    public static final String SEMESTER = "SEMESTER";
    public static final boolean SEMESTER_ONE = true;
    public static final boolean SEMESTER_TWO = false;

    Course course;

    public ClassFragment() {
    }

    public void setArguments(Bundle args) {
        course = CoursesManager.getInstance().getSemester(args.getBoolean(SEMESTER, true)).get(args.getInt(COURSE_INDEX));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int styleNormal = DialogFragment.STYLE_NORMAL;
        setStyle(styleNormal, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.class_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationWrapper.getInstance()));
        recyclerView.setAdapter(new ClassAdapter(course.getAssignments(), recyclerView, this));

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
