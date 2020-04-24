package com.example.medicalapp.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrl {
    public String ReadTheURL(String placeURL)throws IOException{
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(placeURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            connection.disconnect();
        }

        return data;
    }
}
