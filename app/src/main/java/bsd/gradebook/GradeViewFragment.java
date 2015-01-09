package bsd.gradebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradeViewFragment extends Fragment {

    public GradeViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gradebook, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.grades_list_view);

        ArrayList<String> classes = new ArrayList<>();
        try {
            JSONArray courses = ConnectionManager.cache.getJSONArray("courses");
            for (int i=0;i<courses.length();i++) {
                classes.add(courses.getJSONObject(i).getString("course") + " " + courses.getJSONObject(i).getJSONObject("firstSemester").getString("grade"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setAdapter(new ArrayAdapter<String>(ApplicationWrapper.getInstance(), R.layout.class_row, classes));
        return rootView;
    }
}
