package bsd.gradebook.gradebook;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import bsd.gradebook.ApplicationWrapper;
import bsd.gradebook.R;
import bsd.gradebook.course.AssignmentSorter;
import bsd.gradebook.course.Course;
import bsd.gradebook.course.CoursesManager;

public class ClassFragment extends DialogFragment {

    public static final String COURSE_INDEX = "COURSE_INDEX";

    public static final String SEMESTER = "SEMESTER";
    public static final boolean SEMESTER_ONE = true;
    public static final boolean SEMESTER_TWO = false;

    Course course;
    private ClassAdapter classAdapter;

    public ClassFragment() {
    }

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
        classAdapter = new ClassAdapter(course.getAssignments(), recyclerView, this);
        recyclerView.setAdapter(classAdapter);

        ImageButton sortByName = (ImageButton) rootView.findViewById(R.id.button_sort_name);
        sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classAdapter.sort(AssignmentSorter.AlphabeticalAssignmentSorter.getInstance());
            }
        });

        return rootView;
    }

    public void onResume() {
        super.onResume();

    }
}
