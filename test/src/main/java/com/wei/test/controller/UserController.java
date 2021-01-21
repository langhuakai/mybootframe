package com.wei.test.controller;

import com.wei.framework.beans.Autowired;
import com.wei.framework.web.mvcannotation.Controller;
import com.wei.framework.web.mvcannotation.RequestMapping;
import com.wei.framework.web.mvcannotation.RequestParam;
import com.wei.test.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(@RequestParam("name") String name, @RequestParam("desc") String desc) {
        System.out.println("test访问了");
        System.out.println("name>>>>>>>"+name);
        return userService.getInfo(name);
    }
}
