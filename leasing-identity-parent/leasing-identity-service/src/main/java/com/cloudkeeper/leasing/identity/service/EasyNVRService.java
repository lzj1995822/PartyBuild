package com.cloudkeeper.leasing.identity.service;

public interface EasyNVRService {

    String login(String ipPort, String username, String password);

    /**
     * 抓图
     * @param boxNumber 机顶盒序列号
     * @return 图片地址
     */
    String catchPic(String boxNumber,String activityId, String organizationId);


}
