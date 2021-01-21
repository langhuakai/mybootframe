package com.wei.framework.starter;

import com.wei.framework.beans.BeanFactory;
import com.wei.framework.core.ClassScanner;
import com.wei.framework.web.handler.HandlerManagger;
import com.wei.framework.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.util.List;

public class MyBootApplication {

    public static void run(Class<?> cls, String[] args) {
        System.out.println("My spring boot framework application");
        TomcatServer tomcatServer = new TomcatServer(args);
        System.out.println(cls.getResource(""));
        try {
            tomcatServer.startServer();
            List<Class<?>> classes = ClassScanner.scannerClasses(cls.getPackage().getName());
            BeanFactory.initBean(classes);
            classes.forEach(it -> System.out.println(it.getName()) );
            HandlerManagger.resolveMappingHandler(classes);
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
