package callapi.demo;

import callapi.demo.config.Configenv;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
       APIHelper apiHelper = new APIHelper();

            String input = "{\n" +
                    "    \"username\": \"admin\",\n" +
                    "    \"password\": \"admin\"\n" +
                    "}";
            System.out.println(apiHelper.Post("auth/login",input));

    }
}