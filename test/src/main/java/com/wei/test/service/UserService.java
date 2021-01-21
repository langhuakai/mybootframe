package com.wei.test.service;

import com.wei.framework.beans.Bean;

@Bean
public class UserService {

    public String getInfo(String name) {
        return name + " is handsome!";
    }
}
