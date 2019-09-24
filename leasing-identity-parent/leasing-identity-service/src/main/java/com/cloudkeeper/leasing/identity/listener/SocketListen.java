package com.cloudkeeper.leasing.identity.listener;

import com.cloudkeeper.leasing.identity.repository.PeopleStreamRepository;
import com.cloudkeeper.leasing.identity.service.impl.ImageRedisHandler;
import com.cloudkeeper.leasing.identity.socket.SocketOneThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SocketListen implements ServletContextListener {

    private SocketOneThread socketOneThread;

    private Logger log = LoggerFactory.getLogger(SocketListen.class);

    private ImageRedisHandler imageRedisHandler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        PeopleStreamRepository bean = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(PeopleStreamRepository.class);
        log.info("Socket服务初始化中");
        if (socketOneThread == null){
            socketOneThread = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(SocketOneThread.class);
            socketOneThread.start();
            socketOneThread.setPeopleStreamRepository(bean);
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
        if(null != socketOneThread && !socketOneThread.isInterrupted()){
            socketOneThread.destroySocket();
            socketOneThread.interrupt();
        }
    }
}
