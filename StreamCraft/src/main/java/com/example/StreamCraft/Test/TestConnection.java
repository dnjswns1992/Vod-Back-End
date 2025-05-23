package com.example.StreamCraft.Test;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class SpringSecurity04ApplicationTests {

    public static void main(String[] args) {


         {

            try {

                URL url = new URL("http://ssl.ahri.world/info/location?latitude=20.51&longitude=30.21");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Accept","application/json");

                httpURLConnection.setRequestMethod("GET");

                int response = httpURLConnection.getResponseCode();

                System.out.println("Get Response :" + response);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    body.append(line);
                }

                JSONObject jsonObject = new JSONObject(body.toString());

                String someValue = jsonObject.getString("User");

                System.out.println(someValue);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
