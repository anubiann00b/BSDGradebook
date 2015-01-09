package bsd.gradebook.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bsd.gradebook.R;

public class GradesViewAdapter extends RecyclerView.Adapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    List<String> objects = new ArrayList<>();

    public GradesViewAdapter(List<String> values) {
        objects = values;
    }

    @Override
    public GradesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_row, parent, false);
        return new ViewHolder((TextView)view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).mTextView.setText(objects.get(position));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
