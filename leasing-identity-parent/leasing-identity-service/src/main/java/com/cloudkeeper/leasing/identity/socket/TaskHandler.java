package com.cloudkeeper.leasing.identity.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;

public class TaskHandler implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DatagramPacket datagramPacket;

    public void setDatagramPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {
        byte[] receiveBytes = datagramPacket.getData();
        String receiveStr = new String(receiveBytes);
        logger.info("接受到的信息为：" + receiveStr);
    }
}
