package com.nicok.pathguide.services;

import android.content.Context;

import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.helpers.reader.FileReader;
import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

import java.io.IOException;

public class MapService {

    private Context context;
    private HttpService httpService;
    private IReader reader = new FileReader();
    private SerializeWrapper serializeWrapper = new SerializeWrapper();

    LoadMapServiceListener listener;

    public MapService(Context context) {
        this.httpService = new HttpService();

        this.listener = listener;
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
        public void onSuccess(MapDefinition map);
        public void onFail();
    }

    public void load(LoadMapServiceListener listener) {
//        httpService.execute();

        String serializedMap = null;
        try {
            serializedMap = reader.readAsset("map.json", context);
            listener.onSuccess(serializeWrapper.deserialize(serializedMap, MapDefinition.class));
        } catch (IOException e) {
//            return null;
        }
    }

}
