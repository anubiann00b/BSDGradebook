package me.shreyasr.bsdgradebook.gradebook;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.bsdgradebook.R;
import me.shreyasr.bsdgradebook.course.Course;

class ClassAdapter extends RecyclerView.Adapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v;
        }
    }

    private List<Course.Assignment> data = new ArrayList<>();
    private final View.OnClickListener onClickListener = new OnClassClickListener();

    public ClassAdapter(List<Course.Assignment> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_card, parent, false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.assignment_row_assignment)).setText(data.get(position).name);
        ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.assignment_row_percent)).setText(data.get(position).percent);
        ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.assignment_row_grade)).setText(data.get(position).grade);
        ((TextView)((ViewHolder)holder).mCardView.findViewById(R.id.assignment_row_points)).setText(data.get(position).maxPoints);
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    private class OnClassClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View view) {
            //Toast.makeText(ApplicationWrapper.getInstance(), "Clicked row", Toast.LENGTH_SHORT).show();
        }
    }
}
