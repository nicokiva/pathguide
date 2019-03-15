package com.nicok.pathguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;

import java.util.List;

public class DestinationRowAdapter extends ArrayAdapter<NodeDefinition> {

    private static class ViewHolder {
        TextView description;
        TextView extra;
        ImageView icon;
    }

    private List<NodeDefinition> data = null;
    Context context;

    public DestinationRowAdapter(Context context, int resource, List<NodeDefinition > data) {
        super(context, resource, data);

        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NodeDefinition entity = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.row_destination, parent, false);

            viewHolder.description = convertView.findViewById(R.id.tv_description);
            viewHolder.extra = convertView.findViewById(R.id.tv_extra);
            viewHolder.icon = convertView.findViewById(R.id.img_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.description.setText(entity.description);
        viewHolder.extra.setText(entity.extra);
        Integer icon = entity.getIcon();
        if (icon != null) {
            viewHolder.icon.setImageResource(icon);
        }

        return convertView;
    }

}