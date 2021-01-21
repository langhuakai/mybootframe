package com.wei.framework.web.handler;

import com.wei.framework.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MappingHandler {

    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handle(ServletRequest request, ServletResponse response) throws
            IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String reqUri = ((HttpServletRequest)request).getRequestURI();
        if (!this.uri.equals(reqUri)) {
            return false;
        }
        Object[] parameters = new Object[args.length];
        for (int i = 0; i < args.length; i ++) {
            parameters[i] = request.getParameter(args[i]);
        }
       // Object ctl = controller.newInstance();
        Object ctl = BeanFactory.getBean(controller);
        Object respons  = method.invoke(ctl, parameters);
        response.getWriter().write(respons.toString());
        return true;
    }
}
