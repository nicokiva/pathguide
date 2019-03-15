package com.nicok.pathguide.services;

import android.os.AsyncTask;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

//public class HttpService extends AsyncTask<String, Void, Void> {
//
//    public static void get(String url) {
//
//        // Create a new RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Add the String message converter
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//
//        // Make the HTTP GET request, marshaling the response to a String
//        String result = restTemplate.getForObject(url, String.class, "Android");
//    }
//
//}
