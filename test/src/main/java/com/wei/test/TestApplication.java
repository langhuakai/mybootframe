package com.wei.test;
import com.wei.framework.starter.MyBootApplication;

public class TestApplication {

    public static void main(String[] args) {
        System.out.println("Hello world");
        MyBootApplication.run(TestApplication.class, args);
    }
}
