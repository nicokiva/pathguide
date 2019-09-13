package com.nicok.pathguide.services.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;


import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Optional;

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

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public <T> Optional<T> get(String url, Class<T> clazz) {
        if (!this.isConnected()) {
            return Optional.empty();
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        ResponseEntity<T> response = restTemplate.getForEntity(url, clazz);
        return Optional.of(response.getBody());
    }


    @Override
    protected void onPostExecute(Result s) {
        super.onPostExecute(s);

        this.listener.onSuccess(s);
    }

}
