package com.nicok.pathguide.services.http;

import android.content.Context;
import android.os.AsyncTask;

import com.nicok.pathguide.activities.R;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class LoadMapHttpService extends HttpService<Void, Void, String> {

    public LoadMapHttpService(HttpServiceListener listener, Context context) {
        super(listener, context);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int resource = R.string.load_map_url;

        return super.get(context.getString(resource));
    }

}
