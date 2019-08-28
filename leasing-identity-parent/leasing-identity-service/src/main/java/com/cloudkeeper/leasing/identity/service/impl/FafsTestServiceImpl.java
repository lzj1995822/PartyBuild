package com.cloudkeeper.leasing.identity.service.impl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class FafsTestServiceImpl {
    static {    // 连接超时的时限，单位为毫秒
        ClientGlobal.setG_connect_timeout(2000);
        // 网络超时的时限，单位为毫秒
        ClientGlobal.setG_network_timeout(30000);
        ClientGlobal.setG_anti_steal_token(false);
        // 字符集
        ClientGlobal.setG_charset("UTF-8");
        ClientGlobal.setG_secret_key(null);
        // HTTP访问服务的端口号
        ClientGlobal.setG_tracker_http_port(8888);
        // Tracker服务器列表
        InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
        tracker_servers[0] = new InetSocketAddress("172.16.0.199", 22122);
        ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
    }
    public String testUpload(MultipartFile multipartFile) {

        try {
            System.out.println(new Date().getTime());
            byte[] fileBuff = multipartFile.getBytes();

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            NameValuePair nvp[] = new NameValuePair[] { new NameValuePair("age", "18"),
                    new NameValuePair("sex", "male") };
            String fileIds[] = storageClient.upload_file(fileBuff, null, nvp);

            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);

            System.out.println("路径: " + fileIds[1]);
            return "http://122.97.218.162:8888/"+fileIds[0]+"/"+fileIds[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (MyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
