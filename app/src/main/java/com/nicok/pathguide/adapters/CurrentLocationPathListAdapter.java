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

public class CurrentLocationPathListAdapter extends ArrayAdapter<NodeDefinition> {

    private static class ViewHolder {
        TextView description;
        TextView instructions;
        TextView extra;
        ImageView icon;
    }

    private List<NodeDefinition> data = null;
    Context context;

    public CurrentLocationPathListAdapter(Context context, int resource, List<NodeDefinition> data) {
        super(context, resource, data);

        this.data = data;
        this.context = context;
    }

    private String getInstructions(NodeDefinition entity, int position) {
        int nextPosition = position + 1;
        if (data == null || data.size() == nextPosition) {
            return null;
        }

        NodeDefinition nextEntity = getItem(nextPosition);

        return entity.getAdjacentNodes().get(nextEntity).getInstructions();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NodeDefinition entity = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.activity_current_location_path_list_row, parent, false);

            viewHolder.description = convertView.findViewById(R.id.tv_description);
            viewHolder.extra = convertView.findViewById(R.id.tv_extra);
            viewHolder.instructions = convertView.findViewById(R.id.tv_instructions);
            viewHolder.icon = convertView.findViewById(R.id.img_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int color = position == 0 ? R.color.colorSelected : R.color.colorUnselected;

        convertView.setBackgroundColor(getContext().getColor(color));
        viewHolder.description.setText(entity.description);
        viewHolder.extra.setText(entity.extra);

        String instructions = getInstructions(entity, position);
        if (instructions != null) {
            viewHolder.instructions.setText(instructions);
        }

        Integer icon = entity.getIcon();
        if (icon != null) {
            viewHolder.icon.setImageResource(icon);
        }

        return convertView;
    }

}