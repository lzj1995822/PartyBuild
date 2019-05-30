package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.DistLearningActivityVideoVO;
import com.cloudkeeper.leasing.identity.vo.VideoListVO;

import java.util.List;

/**
 * 远教视频 service
 * @author lxw
 */
public interface DistLearningActivityVideoService extends BaseService<DistLearningActivityVideo> {

    List<VideoListVO> getVideo();
}
