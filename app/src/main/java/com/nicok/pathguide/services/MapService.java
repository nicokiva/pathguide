package com.nicok.pathguide.services;

import android.content.Context;

import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;
import com.nicok.pathguide.services.http.HttpService;
import com.nicok.pathguide.services.http.LoadMapHttpService;

import java.io.IOException;

public class MapService {

    private Context context;
    private SerializeWrapper serializeWrapper = new SerializeWrapper();


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
        void onSuccess(MapDefinition map);
        void onFail(Exception e);
    }

    public void load(LoadMapServiceListener listener) {
        new LoadMapHttpService((HttpService.HttpServiceListener<String>) body -> {
            try {
                listener.onSuccess(serializeWrapper.deserialize(body, MapDefinition.class));
            } catch (IOException e) {
                listener.onFail(e);
            }
        }, this.context).execute();
    }
}
