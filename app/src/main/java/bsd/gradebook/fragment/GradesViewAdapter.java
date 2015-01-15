package bsd.gradebook.fragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import bsd.gradebook.ApplicationWrapper;
import bsd.gradebook.Course;
import bsd.gradebook.R;

public class GradesViewAdapter extends RecyclerView.Adapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v;
        }
    }

    List<Course> data = new ArrayList<>();
    final View.OnClickListener onClickListener = new OnGradeClickListener();
    RecyclerView recyclerView;

    public GradesViewAdapter(List<Course> data, RecyclerView recyclerView) {
        this.data = data;
        this.recyclerView = recyclerView;
    }

    @Override
    public GradesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_row, parent, false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.class_row_class)).setText(data.get(position).getCourseName());
            ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.class_row_grade)).setText(data.get(position).getGrade().grade);
            ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.class_row_grade_letter)).setText(data.get(position).getGrade().letterGrade);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class OnGradeClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View view) {
            int itemPosition = recyclerView.getChildPosition(view);
            Course course = data.get(itemPosition);
            try {
                Toast.makeText(ApplicationWrapper.getInstance(), course.getCourseName(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
