package bsd.gradebook.fragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import bsd.gradebook.ClassManager;
import bsd.gradebook.R;

public class GradesViewAdapter extends RecyclerView.Adapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v;
        }
    }

    List<ClassManager> data = new ArrayList<>();

    public GradesViewAdapter(List<ClassManager> data) {
        this.data = data;
    }

    @Override
    public GradesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.class_row_class)).setText(data.get(position).getCourseName());
            ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.class_row_grade)).setText(data.get(position).getGrade().grade);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
