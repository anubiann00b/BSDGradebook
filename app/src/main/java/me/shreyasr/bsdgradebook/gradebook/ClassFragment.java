package me.shreyasr.bsdgradebook.gradebook;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shreyasr.bsdgradebook.ApplicationWrapper;
import me.shreyasr.bsdgradebook.R;
import me.shreyasr.bsdgradebook.course.Course;
import me.shreyasr.bsdgradebook.course.CoursesManager;

public class ClassFragment extends DialogFragment {

    public static final String COURSE_INDEX = "COURSE_INDEX";

    public static final String SEMESTER = "SEMESTER";

    private Course course;

    public void setArguments(Bundle args) {
        course = CoursesManager.getInstance().getSemester(args.getBoolean(SEMESTER, true)).get(args.getInt(COURSE_INDEX));
    }

    @Override
    public void onActivityCreated(Bundle args) {
        super.onActivityCreated(args);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.class_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationWrapper.getInstance()));
        ClassAdapter classAdapter = new ClassAdapter(course.getAssignments());
        recyclerView.setAdapter(classAdapter);

        return rootView;
    }
}
