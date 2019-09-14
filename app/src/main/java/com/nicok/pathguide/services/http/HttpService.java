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

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public <T> Optional<T> tryGet(String url, Class<T> clazz) {
        if (!this.isConnected(this.context)) {
            return Optional.empty();
        }

        return Optional.of(get(url, clazz));
    }

    private <T> T get(String url, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        ResponseEntity<T> response = restTemplate.getForEntity(url, clazz);
        return response.getBody();
    }


    @Override
    protected void onPostExecute(Result s) {
        super.onPostExecute(s);

        this.listener.onSuccess(s);
    }

}
