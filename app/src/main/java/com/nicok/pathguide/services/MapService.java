package com.nicok.pathguide.services;

import android.content.Context;
import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.services.http.HttpService;
import com.nicok.pathguide.services.http.LoadMapHttpService;

import java.util.Optional;


public class MapService {

    private Context context;


    public MapService(Context context) {
        this.context = context;
    }

    private static MapService _instance = null;

    public static MapService getInstance(Context context) {
        if (_instance == null) {
            _instance = new MapService(context);
        }

        return _instance;
    }

    public interface LoadMapServiceListener {
        void onSuccess(Optional<MapDefinition> map);
    }

    public void load(LoadMapServiceListener listener) {
        new LoadMapHttpService(
            (HttpService.HttpServiceListener<Optional<MapDefinition>>) body -> listener.onSuccess(body),
            this.context
        ).execute();
    }
}
