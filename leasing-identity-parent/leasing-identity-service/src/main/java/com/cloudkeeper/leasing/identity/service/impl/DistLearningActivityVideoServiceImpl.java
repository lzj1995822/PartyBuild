package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import com.cloudkeeper.leasing.identity.repository.DistLearningActivityVideoRepository;
import com.cloudkeeper.leasing.identity.service.DistLearningActivityVideoService;
import com.cloudkeeper.leasing.identity.vo.DistLearningActivityVideoVO;
import com.cloudkeeper.leasing.identity.vo.VideoListVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

/**
 * 远教视频 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DistLearningActivityVideoServiceImpl extends BaseServiceImpl<DistLearningActivityVideo> implements DistLearningActivityVideoService {

    /** 远教视频 repository */
    private final DistLearningActivityVideoRepository distLearningActivityVideoRepository;

    @Override
    protected BaseRepository<DistLearningActivityVideo> getBaseRepository() {
        return distLearningActivityVideoRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("videoUrl", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("lengthOfTime", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("videoCover", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    public List<VideoListVO> getVideo(){

        ObjectMapper param = new ObjectMapper();
        String fullUrl = null;
        try {
            fullUrl = "http://122.97.218.162:18306/JRPartyService/ThirdParty.svc/SHCSListCPVideo?cpCode=JTEDU";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("Accept", MediaType.APPLICATION_JSON_UTF8.toString());
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity responseEntity = restTemplate.exchange(fullUrl, HttpMethod.GET, entity,String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseEntity.getBody().toString());
            JsonNode OneListNode = rootNode.path("data");
            JsonNode TwoListNode = OneListNode.path("video");
            String logJson = mapper.writeValueAsString(TwoListNode);
            JavaType logType = mapper.getTypeFactory().constructParametricType(List.class, VideoListVO.class);
            List<VideoListVO> logList = mapper.readValue(logJson, logType);
            return logList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
