package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.identity.domain.ParCamera;
import com.cloudkeeper.leasing.identity.service.EasyNVRService;
import com.cloudkeeper.leasing.identity.service.FdfsService;
import com.cloudkeeper.leasing.identity.service.ParCameraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EasyNVRServiceImpl implements EasyNVRService {

    private final static String USERNAME = "admin";

    private final static String PASSWORD = "f4158d08e56abe5f7044c7ce485859cf";

    private final static String LOGIN_URL_SUFFIX = "/api/v1/login";

    private final static String CATCH_IMG_URL_SUFFIX = "/api/v1/getsnap";

    private final static String CHANNEL_URL_SUFFIX = "/api/v1/getchannels";

    private final static String TOKEN_KEY = "easyNVRToken";

    private final ParCameraService parCameraService;

    private final Logger logger = LoggerFactory.getLogger(EasyNVRServiceImpl.class);

    private final RedisTemplate redisTemplate;

    private RestTemplate restTemplate = new RestTemplate();

    private final FdfsService fdfsService;

    @Override
    public String login(String ipPort, String username, String password) {
        String easyNVRToken = null;
        try {
            String reqUrl = "http://".concat(ipPort).concat(LOGIN_URL_SUFFIX).concat("?username=").concat(username)
                    .concat("&password=").concat(password).concat("&_=").concat(String.valueOf(Calendar.getInstance().getTimeInMillis()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("Accept", MediaType.APPLICATION_JSON_UTF8.toString());
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity responseEntity = restTemplate.exchange(reqUrl, HttpMethod.GET, entity, String.class);
            HttpStatus statusCode = responseEntity.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody().toString());
                JsonNode body = jsonNode.path("EasyDarwin").path("Body");
                JsonNode tokenNode = body.path("Token");
                JsonNode timeOut = body.path("TokenTimeout");
                easyNVRToken = objectMapper.writeValueAsString(tokenNode);
                Long timeOutSecond = Long.valueOf(objectMapper.writeValueAsString(timeOut));
                String port = ipPort.split(":")[1];
                redisTemplate.opsForValue().set(TOKEN_KEY + port, easyNVRToken, timeOutSecond, TimeUnit.SECONDS);
            } else {
                logger.error("EasyNVR鉴权接口访问失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return easyNVRToken;
    }

    @Override
    public String catchPic(String boxNumber)  {
        String fullUrl = getFullUrl(boxNumber);
        String ipPort = getIPPort(fullUrl);
        String port = ipPort.split(":")[1];
        String channelNumber = getChannelNumber(fullUrl);
        String token = (String) redisTemplate.opsForValue().get(TOKEN_KEY + port);
        if (StringUtils.isEmpty(token)) {
            token = login(ipPort, USERNAME, PASSWORD);
        }
        if (StringUtils.isEmpty(token)) {
            logger.warn("令牌为空");
            return null;
        }
        if (!isOnline(ipPort, channelNumber, token)) {
            logger.warn("摄像头不在线");
            return null;
        }
        String reqUrl = "http://".concat(ipPort).concat(CATCH_IMG_URL_SUFFIX).concat("?channel=").concat(channelNumber);
        HttpHeaders headers = new HttpHeaders();
        List cookies = new ArrayList();
        cookies.add("token=" + token);
        headers.put(HttpHeaders.COOKIE, cookies);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(reqUrl, HttpMethod.GET, entity, byte[].class);
        byte[] bytes = responseEntity.getBody();
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        String picUrl = null;
        try {
            picUrl = fdfsService.uploadFile(stream, Long.valueOf(bytes.length), "11.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picUrl;
    }

    /**
     * 监控点是否在线
     * @param ipPort ip 加  端口
     * @param channelNumber
     * @param token 令牌
     * @return
     */
    private Boolean isOnline(String ipPort, String channelNumber, String token) {
        String reqUrl = "http://".concat(ipPort).concat(CHANNEL_URL_SUFFIX).concat("?channel=").concat(channelNumber);
        HttpHeaders headers = new HttpHeaders();
        List cookies = new ArrayList();
        cookies.add("token=" + token);
        headers.put(HttpHeaders.COOKIE, cookies);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(reqUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Boolean online =  false;
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode channelNode = jsonNode.path("EasyDarwin").path("Body").path("Channels");
            if (channelNode.size() == 0) {
                logger.warn("通道不存在");
                return null;
            }
            JsonNode onlineNode = channelNode.get(0).path("Online");
            online = objectMapper.writeValueAsString(onlineNode).equals("1");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return online;
    }
    /**
     * 获取摄像头流地址
     *
     * @param boxNumber 机顶盒号
     * @return
     */
    private String getFullUrl(String boxNumber) {
        ParCamera byNumber = parCameraService.findByNumber(boxNumber);
        if (StringUtils.isEmpty(boxNumber)) {
            logger.warn("监控点不存在");
            return null;
        }
        return byNumber.getIP();
    }

    /**
     * 获取ip和port
     *
     * @param fullUrl 流地址
     * @return 获取ip和port
     */
    private String getIPPort(@NonNull String fullUrl) {
        String[] split = fullUrl.split("/");
        return split[2];
    }

    /**
     * 获取通道号
     *
     * @param fullUrl 流地址
     * @return 通道号
     */
    private String getChannelNumber(@NonNull String fullUrl) {
        String[] split = fullUrl.split("/");
        String streamUrl = split[4];
        return streamUrl.split("_")[1];
    }

}
