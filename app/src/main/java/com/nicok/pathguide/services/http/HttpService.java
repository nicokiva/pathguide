package com.nicok.pathguide.services.http;

import android.content.Context;
import android.os.AsyncTask;

import com.nicok.pathguide.activities.R;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public abstract class HttpService<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    public interface HttpServiceListener<Result> {
        void onSuccess(Result body);
    }

    protected HttpServiceListener listener;
    protected Context context;
    public HttpService(HttpServiceListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public String get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }

    @Override
    protected void onPostExecute(Result s) {
        super.onPostExecute(s);

        this.listener.onSuccess(s);
    }

}
