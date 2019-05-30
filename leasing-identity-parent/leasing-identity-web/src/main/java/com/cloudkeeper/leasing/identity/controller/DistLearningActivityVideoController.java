package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo.DistLearningActivityVideoDTO;
import com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo.DistLearningActivityVideoSearchable;
import com.cloudkeeper.leasing.identity.vo.DistLearningActivityVideoVO;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.VideoListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远教视频 controller
 * @author lxw
 */
@Api(value = "远教视频", tags = "远教视频")
@RequestMapping("/distLearningActivityVideo")
public interface DistLearningActivityVideoController {

    /**
     * 查询
     * @param id 远教视频id
     * @return 远教视频 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<DistLearningActivityVideoVO> findOne(@ApiParam(value = "远教视频id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param distLearningActivityVideoDTO 远教视频 DTO
     * @return 远教视频 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<DistLearningActivityVideoVO> add(@ApiParam(value = "远教视频 DTO", required = true) @RequestBody @Validated DistLearningActivityVideoDTO distLearningActivityVideoDTO);

    /**
     * 更新
     * @param id 远教视频id
     * @param distLearningActivityVideoDTO 远教视频 DTO
     * @return 远教视频 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<DistLearningActivityVideoVO> update(@ApiParam(value = "远教视频id", required = true) @PathVariable String id,
        @ApiParam(value = "远教视频 DTO", required = true) @RequestBody @Validated DistLearningActivityVideoDTO distLearningActivityVideoDTO);

    /**
     * 删除
     * @param id 远教视频id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "远教视频id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param distLearningActivityVideoSearchable 远教视频查询条件
     * @param sort 排序条件
     * @return 远教视频 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<DistLearningActivityVideoVO>> list(@ApiParam(value = "远教视频查询条件", required = true) @RequestBody DistLearningActivityVideoSearchable distLearningActivityVideoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param distLearningActivityVideoSearchable 远教视频查询条件
     * @param pageable 分页条件
     * @return 远教视频 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<DistLearningActivityVideoVO>> page(@ApiParam(value = "远教视频查询条件", required = true) @RequestBody DistLearningActivityVideoSearchable distLearningActivityVideoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 列表查询

     * @return 远教视频 VO 集合
     */
    @ApiOperation(value = "列表查询视频接口", position = 5)
    @GetMapping("/videoList")
    Result<List<VideoListVO>> getVideo();


}
