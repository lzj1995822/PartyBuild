package com.cloudkeeper.leasing.identity.listener;

import com.cloudkeeper.leasing.identity.service.impl.ImageRedisHandler;
import com.cloudkeeper.leasing.identity.socket.SocketThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SocketListen implements ServletContextListener {

    private SocketThread socketThread;

    private Logger log = LoggerFactory.getLogger(SocketListen.class);

    private ImageRedisHandler imageRedisHandler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Socket服务初始化中");
        if(socketThread == null){
            socketThread = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(SocketThread.class);
            socketThread.start();
        }
        log.info("Redis图片缓存器");
        if (imageRedisHandler == null){
            imageRedisHandler = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(ImageRedisHandler.class);
            imageRedisHandler.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("将Socket服务关闭...");
        if(null != socketThread && !socketThread.isInterrupted()){
            socketThread.destroySocket();
            socketThread.interrupt();
        }
    }
}
