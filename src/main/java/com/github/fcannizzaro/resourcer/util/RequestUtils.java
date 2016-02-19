package com.github.fcannizzaro.resourcer.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Francesco Cannizzaro
 */
public class RequestUtils {

    /**
     * Make a GET request
     */
    public static String get(String path) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null)
                response.append(line)
                        .append('\n');

            rd.close();

            connection.disconnect();

            return response.toString();

        } catch (Exception e) {

            if (connection != null)
                connection.disconnect();

            e.printStackTrace();
            return null;
        }
    }
}


