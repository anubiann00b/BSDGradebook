package me.shreyasr.bsdgradebook.gradebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shreyasr.bsdgradebook.ApplicationWrapper;
import me.shreyasr.bsdgradebook.R;
import me.shreyasr.bsdgradebook.course.CoursesManager;

public class GradebookFragment extends Fragment {

    public static final String SEMESTER = "SEMESTER";
    private static final boolean SEMESTER_ONE = true;
    public static final boolean SEMESTER_TWO = false;

    private boolean firstSemester = true;

    public GradebookFragment() {
    }

    public void setArguments(Bundle args) {
        firstSemester = args.getBoolean(SEMESTER, SEMESTER_ONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gradebook, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.grades_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationWrapper.getInstance()));

        CoursesManager.getInstance().initialize();
        recyclerView.setAdapter(new GradebookAdapter(CoursesManager.getInstance().getSemester(firstSemester), recyclerView, this, firstSemester));

        return rootView;
    }
}
