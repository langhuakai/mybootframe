package com.wei.framework.web.server;

import com.wei.framework.web.servlet.DispatcherServlet;
import com.wei.framework.web.servlet.TomcatServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;

public class TomcatServer {

    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        // 实例化tomcat
        tomcat = new Tomcat();
        tomcat.setPort(7632);
        tomcat.start();
        // 实例化context容器
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        /*TomcatServlet tomcatServlet = new TomcatServlet();
        Tomcat.addServlet(context, "tomcatServlet", tomcatServlet).setAsyncSupported(true);*/

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet).setAsyncSupported(true);

        // 添加URL映射
        /*context.addServletMappingDecoded("/test", "tomcatServlet");*/
        context.addServletMappingDecoded("/", "dispatcherServlet");
        tomcat.getHost().addChild(context);
        // 设置守护线程防止tomcat中途退出
        Thread awaitThread = new Thread("tomcat_await_thread.") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
