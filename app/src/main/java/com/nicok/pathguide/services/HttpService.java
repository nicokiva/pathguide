package com.nicok.pathguide.services;

import android.os.AsyncTask;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class HttpService extends AsyncTask<String, Void, String> {

    public interface HttpServiceListener {
        void onSuccess(String body);
    }

    private HttpServiceListener listener;
    public HttpService(HttpServiceListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        String fooResourceUrl = "https://api.myjson.com/bins/qa0ds";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);

        return response.getBody();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        this.listener.onSuccess(s);
    }
}
