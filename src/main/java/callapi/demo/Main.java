package callapi.demo;

import callapi.demo.enity.HttpObject;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {

        APIHelper apiHelper = new APIHelper();

        String input = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"admin1\"\n" +
                "}";
        String respone = apiHelper.Post("auth/login", input);
        System.out.println(respone);
        Gson g = new Gson();
        HttpObject s = g.fromJson(respone, HttpObject.class);
        System.out.println(s);
    }
}