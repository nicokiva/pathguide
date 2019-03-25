package com.nicok.pathguide.viewHandlers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.fragments.dialog.DialogFragmentBase;
import com.nicok.pathguide.fragments.dialog.selectDestinationDialog.Fragment;
import com.nicok.pathguide.viewHandlers.adapters.DestinationRowAdapter;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class DestinationViewHandler extends ViewHandlerBase implements IViewHandler {

    public interface DestinationViewHandlerListener {
        void onSelectedItem(NodeDefinition destination);
    }

    ListView destinationsList;
    private DestinationViewHandlerListener listener;

    public DestinationViewHandler(Context context, View view, DestinationViewHandlerListener listener) {
        super(context);

        this.listener = listener;
        this.destinationsList = view.findViewById(R.id.available_destination_list);
    }

    public IViewHandler setView(List<NodeDefinition> nodes) {
        DestinationRowAdapter adapter = new DestinationRowAdapter(this.context, android.R.layout.simple_list_item_1, nodes);
        destinationsList.setAdapter(adapter);
        destinationsList.setOnItemClickListener(this::onItemClickListener);

        return this;
    }

    private void onItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        NodeDefinition itemValue = (NodeDefinition)destinationsList.getItemAtPosition(position);

        Bundle data = new Bundle();
        data.putString(ExtrasParameterNames.ENTITY_NAME, itemValue.description);
        data.putInt(ExtrasParameterNames.ENTITY_ICON, itemValue.getIcon());
        data.putSerializable(ExtrasParameterNames.ENTITY_DATA, itemValue);

        DialogFragment dialog = new Fragment()
            .setListener(new DialogFragmentBase.DialogFragmentBaseListener() {
                @Override
                public void onDialogPositiveClick(@Nullable Serializable entityData) {
                    if (entityData == null) {
                        return;
                    }

                    listener.onSelectedItem((NodeDefinition) entityData);
                }

                @Override
                public void onDialogNegativeClick() { }
            });
        dialog.setArguments(data);
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "SelectDestinationDialogFragment");
    }

}
