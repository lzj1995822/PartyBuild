package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.ResponseMessageConstants;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.DistLearningActivityVideoController;
import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo.DistLearningActivityVideoDTO;
import com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo.DistLearningActivityVideoSearchable;
import com.cloudkeeper.leasing.identity.service.DistLearningActivityVideoService;
import com.cloudkeeper.leasing.identity.vo.DistLearningActivityVideoVO;
import com.cloudkeeper.leasing.identity.vo.VideoListVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 远教视频 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DistLearningActivityVideoControllerImpl implements DistLearningActivityVideoController {

    /** 远教视频 service */
    private final DistLearningActivityVideoService distLearningActivityVideoService;

    @Override
    public Result<DistLearningActivityVideoVO> findOne(@ApiParam(value = "远教视频id", required = true) @PathVariable String id) {
        Optional<DistLearningActivityVideo> distLearningActivityVideoOptional = distLearningActivityVideoService.findOptionalById(id);
        return distLearningActivityVideoOptional.map(distLearningActivityVideo -> Result.of(distLearningActivityVideo.convert(DistLearningActivityVideoVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<DistLearningActivityVideoVO> add(@ApiParam(value = "远教视频 DTO", required = true) @RequestBody @Validated DistLearningActivityVideoDTO distLearningActivityVideoDTO) {
        DistLearningActivityVideo distLearningActivityVideo = distLearningActivityVideoService.save(distLearningActivityVideoDTO.convert(DistLearningActivityVideo.class));
        return Result.ofAddSuccess(distLearningActivityVideo.convert(DistLearningActivityVideoVO.class));
    }

    @Override
    public Result<DistLearningActivityVideoVO> update(@ApiParam(value = "远教视频id", required = true) @PathVariable String id,
        @ApiParam(value = "远教视频 DTO", required = true) @RequestBody @Validated DistLearningActivityVideoDTO distLearningActivityVideoDTO) {
        Optional<DistLearningActivityVideo> distLearningActivityVideoOptional = distLearningActivityVideoService.findOptionalById(id);
        if (!distLearningActivityVideoOptional.isPresent()) {
            return Result.ofLost();
        }
        DistLearningActivityVideo distLearningActivityVideo = distLearningActivityVideoOptional.get();
        BeanUtils.copyProperties(distLearningActivityVideoDTO, distLearningActivityVideo);
        distLearningActivityVideo = distLearningActivityVideoService.save(distLearningActivityVideo);
        return Result.ofUpdateSuccess(distLearningActivityVideo.convert(DistLearningActivityVideoVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "远教视频id", required = true) @PathVariable String id) {
        distLearningActivityVideoService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<DistLearningActivityVideoVO>> list(@ApiParam(value = "远教视频查询条件", required = true) @RequestBody DistLearningActivityVideoSearchable distLearningActivityVideoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<DistLearningActivityVideo> distLearningActivityVideoList = distLearningActivityVideoService.findAll(distLearningActivityVideoSearchable, sort);
        List<DistLearningActivityVideoVO> distLearningActivityVideoVOList = DistLearningActivityVideo.convert(distLearningActivityVideoList, DistLearningActivityVideoVO.class);
        return Result.of(distLearningActivityVideoVOList);
    }

    @Override
    public Result<Page<DistLearningActivityVideoVO>> page(@ApiParam(value = "远教视频查询条件", required = true) @RequestBody DistLearningActivityVideoSearchable distLearningActivityVideoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<DistLearningActivityVideo> distLearningActivityVideoPage = distLearningActivityVideoService.findAll(distLearningActivityVideoSearchable, pageable);
        Page<DistLearningActivityVideoVO> distLearningActivityVideoVOPage = DistLearningActivityVideo.convert(distLearningActivityVideoPage, DistLearningActivityVideoVO.class);
        return Result.of(distLearningActivityVideoVOPage);
    }
    @Override
    public Result<List<VideoListVO>> getVideo(){
        return Result.of(ResponseMessageConstants.UPDATE_SUCCESS,distLearningActivityVideoService.getVideo());
    }
}
