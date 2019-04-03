package com.nicok.pathguide.services;

import android.os.AsyncTask;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpService extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://jsonplaceholder.typicode.com/todos/1";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);

        return null;
    }

}
