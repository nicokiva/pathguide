package com.nicok.pathguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.business_definitions.BaseEntityDefinition;

import java.util.List;

public class BaseEntityAdapter extends ArrayAdapter<BaseEntityDefinition> {

    private static class ViewHolder {
        TextView description;
    }


    private List<BaseEntityDefinition> data = null;
    Context context;

    public BaseEntityAdapter(Context context, int resource, List<BaseEntityDefinition > data) {
        super(context, resource, data);

        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseEntityDefinition entity = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.row_destination, parent, false);

            viewHolder.description = convertView.findViewById(R.id.tv_description);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.description.setText(entity.description);

        return convertView;
    }

}