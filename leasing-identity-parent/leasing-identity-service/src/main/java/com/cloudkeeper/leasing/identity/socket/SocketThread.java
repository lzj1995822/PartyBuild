package com.cloudkeeper.leasing.identity.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SocketThread extends Thread {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static ExecutorService threadPool = Executors.newFixedThreadPool(30);

    private static final Integer PORT = 1019;

    private DatagramSocket ds = null;

    public SocketThread() {
        if (ds == null) {
            try {
                ds = new DatagramSocket(PORT);
                log.info("创建UDP连接成功");
            } catch (SocketException e) {
                e.printStackTrace();
                log.error("创建UDP连接失败", e);
            }
        }
    }

    @Override
    public void run() {
        DatagramPacket receive = null;
        log.info("服务已启动，进入长连接状态等待请求中");
        while (true) {
            byte[] receiveData = new byte[1024];
            receive = new DatagramPacket(receiveData, receiveData.length);

            try {
                ds.receive(receive);
                TaskHandler taskHandler = new TaskHandler();
                taskHandler.setDatagramPacket(receive);
                threadPool.execute(taskHandler);
            } catch (IOException e) {
                log.error("服务端异常",e);
            }
        }
    }

    public void destroySocket() {
        if (null != ds && !ds.isClosed()) {
            ds.close();
        }
    }

    public static void main(String[] args) {
        try {
            String msg = "";
            DatagramSocket daSocket = new DatagramSocket();
            //查询白名单
            msg = "{\"sign\":\"whiteList\",\"operator_system\":\"\"}";

            //访问服务地址
            String IP = "172.16.1.92";
            int port = 1019;
            byte[] by = msg.getBytes();
            //将服务器IP转化为InetAddress对象
            try {
                InetAddress server = InetAddress.getByName(IP);
                DatagramPacket sendDp = new DatagramPacket(by,by.length,server,port);
                try {

                    daSocket.send(sendDp);
                    //获取服务器返回信息
//                    byte[] byResp = new byte[1024];
//                    DatagramPacket receive = new DatagramPacket(byResp,byResp.length);
//                    //接收数据
//                    daSocket.receive(receive);
//                    //输出内容
//                    byte[] b = receive.getData();
//                    int len = receive.getLength();
//                    String s = new String(b,0,len);
//                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
