package bsd.gradebook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import bsd.gradebook.ApplicationWrapper;
import bsd.gradebook.ClassManager;
import bsd.gradebook.ConnectionManager;
import bsd.gradebook.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradeViewFragment extends Fragment {

    public GradeViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gradebook, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.grades_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationWrapper.getInstance()));

        ArrayList<ClassManager> classes = new ArrayList<>();
        try {
            JSONArray courses = ConnectionManager.cache.getJSONArray("courses");
            for (int i=0;i<courses.length();i++) {
                classes.add(new ClassManager(courses.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(new GradesViewAdapter(classes));
        return rootView;
    }
}
