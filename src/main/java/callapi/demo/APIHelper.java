package callapi.demo;


import callapi.demo.config.Configenv;

import java.io.IOException;
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

    public String Login() throws MalformedURLException, IOException {
        /*
         * Open an HTTP Connection to the Logon.ashx page
         */
        HttpURLConnection httpcon = (HttpURLConnection) ((new URL(BaseUrl + "Logon.ashx").openConnection()));
        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestMethod("POST");
        httpcon.connect();
        /*
         * Output user credentials over HTTP Output Stream
         */
        byte[] outputBytes = "{'username': 'sysadmin', 'password':'apple'}".getBytes("UTF-8");
        OutputStream os = httpcon.getOutputStream();
        os.write(outputBytes);
        os.close();
        /*
         * Call Function setCookie and pass the HttpUrlConnection. Set Function
         * will return a Cookie String used to authenticate user.
         */
        return setCookie(httpcon);
    }

    public String setCookie(HttpURLConnection httpcon) {
        /*
         * Process the HTTP Response Cookies from successful credentials
         */
        String headerName;
        ArrayList<String> cookies = new ArrayList<String>();
        for (int i = 1; (headerName = httpcon.getHeaderFieldKey(i)) != null; i++) {
            if (headerName.equals("Set-Cookie") && httpcon.getHeaderField(i) != "null") {
                cookies.add(httpcon.getHeaderField(i));
            }
        }
        httpcon.disconnect();
        /*
         * Filter cookies, create Session_ID Cookie
         */
        String cookieName = cookies.get(0);
        String cookie2 = cookies.get(1);
        String cookie1 = cookieName.substring(cookieName.indexOf("="), cookieName.indexOf(";") + 2);
        cookie2 = cookie2.substring(0, cookie2.indexOf(";"));
        cookieName = cookieName.substring(0, cookieName.indexOf("="));
        String cookie = cookieName + cookie1 + cookie2;
        return cookie;
    }


}