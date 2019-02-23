package com.nicok.pathguide.fragments.selectDestinationList;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.fragments.selectDestinationList.Fragment.OnListFragmentInteractionListener;
import com.nicok.pathguide.activities.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<NodeDefinition> mValues;
    private final OnListFragmentInteractionListener mListener;

    public RecyclerViewAdapter(List<NodeDefinition> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_destination, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.description.setText(mValues.get(position).description);
        holder.extra.setText(mValues.get(position).extra);

        Integer icon = mValues.get(position).getIcon();
        if (icon != null) {
            holder.icon.setImageResource(icon);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView description;
        public final TextView extra;
        public final ImageView icon;
        public NodeDefinition mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            description = (TextView) view.findViewById(R.id.tv_description);
            extra = (TextView) view.findViewById(R.id.tv_extra);
            icon = (ImageView) view.findViewById(R.id.img_icon);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + description.getText() + "'";
        }
    }
}
