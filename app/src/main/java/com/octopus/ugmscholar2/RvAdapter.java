package com.octopus.ugmscholar2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by octopus on 20/04/16.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    // Usually involves inflating a layout from XML and returning the holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView title, author;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);

        }
    }


    private List<ItemData> datas;

    // Pass in the contact array into the constructor
    public RvAdapter(List<ItemData> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View rvView = inflater.inflate(R.layout.rv_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(rvView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder viewHolder, int position) {
        ItemData data = datas.get(position);

        TextView title = viewHolder.title;
        title.setText(data.getTitle());
        TextView author = viewHolder.author;
        author.setText("Diposting oleh : "+ data.getAuthor() + " pada  "+ data.getTgl());
    }

    @Override
    public int getItemCount() {
        return datas.size();

    }
}