package com.example.StreamCraft;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootTest
class SpringSecurity04ApplicationTests {

    @Test
    void contextLoads() throws IOException {

        try {


            URL url = new URL("https://9c21-114-199-10-135.ngrok-free.app/test/endpoint");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");

            int response = httpURLConnection.getResponseCode();
            System.out.println("Get Response :" + response);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder body = new StringBuilder();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
                bufferedReader.close();
            }
            System.out.println("response Body :" + body.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        }


}
