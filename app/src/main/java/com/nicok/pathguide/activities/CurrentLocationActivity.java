package com.nicok.pathguide.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nicok.pathguide.adapters.CurrentLocationPathListAdapter;
import com.nicok.pathguide.adapters.DestinationRowAdapter;
import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.constants.ExtrasParameterNames;

import java.util.Arrays;
import java.util.stream.Collectors;

import androidx.appcompat.widget.Toolbar;

public class CurrentLocationActivity extends AppPathGuideActivity {

    Toolbar mTopToolbar;

    ListView availableDestinationPathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        startServiceAndBind();

        this.availableDestinationPathList = findViewById(R.id.available_destination_path_list);
//        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(mTopToolbar);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_current_location, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_favorite) {
//            Toast.makeText(CurrentLocationActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onMessageReceived(Bundle bundle) {
        NodeDefinition[] nodes = (NodeDefinition[])bundle.getSerializable(ExtrasParameterNames.NODES_ENTITY_DATA);
        EdgeDefinition edge = (EdgeDefinition)bundle.getSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA);

        if (nodes == null || edge == null) {
            findViewById(R.id.activity_current_location_path).setVisibility(View.GONE);
            findViewById(R.id.activity_current_location_unknown).setVisibility(View.VISIBLE);

            return;
        }

        findViewById(R.id.activity_current_location_path).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_current_location_unknown).setVisibility(View.GONE);

        CurrentLocationPathListAdapter adapter = new CurrentLocationPathListAdapter(this, android.R.layout.simple_list_item_1, Arrays.stream(nodes).collect(Collectors.toList()));
        availableDestinationPathList.setAdapter(adapter);
//        destinationsList.setOnItemClickListener(this::onItemClickListener);
    }

    @Override
    protected void onServiceLoaded() { }
}
