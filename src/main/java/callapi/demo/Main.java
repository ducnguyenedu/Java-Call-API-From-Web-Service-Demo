package callapi.demo;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        APIHelper apiHelper = new APIHelper();

        String input = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"admin1\"\n" +
                "}";
        String respone = apiHelper.Post("auth/login", input);
        System.out.println(respone);
        HashMap<String, String> JsonMap = apiHelper.toJsonMap(respone);
        for (String key : JsonMap.keySet()) {
            String value = JsonMap.get(key).toString();
            System.out.println(key + " " + value);
        }
    }
}