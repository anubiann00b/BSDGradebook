package bsd.gradebook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bsd.gradebook.ApplicationWrapper;
import bsd.gradebook.CoursesManager;
import bsd.gradebook.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradeViewFragment extends Fragment {

    public static final String FIRST_SEMESTER = "FIRST_SEMESTER";
    boolean firstSemester = true;

    public GradeViewFragment() {
    }

    public void setArguments(Bundle args) {
        firstSemester = args.getBoolean(FIRST_SEMESTER, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gradebook, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.grades_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationWrapper.getInstance()));

        CoursesManager.getInstance().initialize();

        if (firstSemester)
            recyclerView.setAdapter(new GradesViewAdapter(CoursesManager.getInstance().getSemesterOne(), recyclerView));
        else
            recyclerView.setAdapter(new GradesViewAdapter(CoursesManager.getInstance().getSemesterTwo(), recyclerView));
        return rootView;
    }
}
