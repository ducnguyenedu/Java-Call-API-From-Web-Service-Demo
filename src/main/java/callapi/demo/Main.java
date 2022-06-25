package callapi.demo;

import callapi.demo.config.Configenv;

public class Main {
    public static void main(String[] args) {
        Configenv configenv = Configenv.configure().load();
        System.out.println("Hello world!");
        System.out.println(configenv.get("MY_ENV"));
    }
}