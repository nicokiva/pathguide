package com.nicok.pathguide.services;

import android.content.Context;

import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

import java.io.IOException;

public class MapService {

    private Context context;
    private HttpService httpService;
    private SerializeWrapper serializeWrapper = new SerializeWrapper();

    private LoadMapServiceListener listener;

    public MapService(Context context) {
        this.context = context;
    }

    private static MapService _instance = null;
    public static MapService getInstance() {
        if (_instance == null) {
            throw new IllegalArgumentException();
        }

        return _instance;
    }

    public static MapService getInstance(Context context) {
        if (_instance == null) {
            _instance = new MapService(context);
        }

        return _instance;
    }

    public interface LoadMapServiceListener {
        void onSuccess(MapDefinition map);
        void onFail();
    }

    public void load(LoadMapServiceListener listener) {
        new HttpService(new HttpService.HttpServiceListener() {
            @Override
            public void onSuccess(String body) {
//                serializedMap = reader.readAsset("map.json", context);
                try {

                    listener.onSuccess(serializeWrapper.deserialize(body, MapDefinition.class));
                } catch (IOException e) {
//            return null;
                }
            }

            }
        ).execute();
    }
}
