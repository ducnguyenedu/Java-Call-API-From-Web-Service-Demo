package callapi.demo;


import callapi.demo.config.Configenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class APIHelper {
    private String BaseUrl;

    /*
     *Constructor sets BaseUrl, ResponderUrl and ResponderParameters properties
     */
    public APIHelper() {

        Configenv configenv = Configenv.configure().load();
        this.BaseUrl = configenv.get("API_KEY");
    }


    public String Post(String URL, String BODY)  {
        String respone="";
        try {
        /*
         * Open an HTTP Connection to the Logon.ashx page
         */
        HttpURLConnection conn = (HttpURLConnection) ((new URL(BaseUrl + URL).openConnection()));
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");



            OutputStream os = conn.getOutputStream();
            os.write(BODY.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
               respone=respone+output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();
            return "Hi I am Here"+ e;
        } catch (IOException e) {

            e.printStackTrace();
            return "Hi I am Here"+ e;
        }
        return respone;
    }

}