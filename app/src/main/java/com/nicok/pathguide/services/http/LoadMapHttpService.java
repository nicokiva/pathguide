package com.nicok.pathguide.services.http;

import android.content.Context;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.businessDefinitions.MapDefinition;

import java.util.Optional;

public class LoadMapHttpService extends HttpService<Void, Void, Optional<MapDefinition>> {

    public LoadMapHttpService(HttpServiceListener listener, Context context) {
        super(listener, context);
    }

    @Override
    protected Optional<MapDefinition> doInBackground(Void... voids) {
        int resource = R.string.load_map_url;

        return tryGet(context.getString(resource), MapDefinition.class);
    }

}
