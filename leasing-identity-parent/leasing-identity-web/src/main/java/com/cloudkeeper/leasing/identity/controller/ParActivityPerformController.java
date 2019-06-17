package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformSearchable;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import com.cloudkeeper.leasing.identity.vo.TownDetailVO;
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
 * 任务执行记录 controller
 * @author lxw
 */
@Api(value = "任务执行记录", tags = "任务执行记录")
@RequestMapping("/parActivityPerform")
public interface ParActivityPerformController {

    /**
     * 查询
     * @param id 任务执行记录id
     * @return 任务执行记录 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<ParActivityPerformVO> findOne(@ApiParam(value = "任务执行记录id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param parActivityPerformDTO 任务执行记录 DTO
     * @return 任务执行记录 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<ParActivityPerformVO> add(@ApiParam(value = "任务执行记录 DTO", required = true) @RequestBody @Validated ParActivityPerformDTO parActivityPerformDTO);

    /**
     * 更新
     * @param id 任务执行记录id
     * @param parActivityPerformDTO 任务执行记录 DTO
     * @return 任务执行记录 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<ParActivityPerformVO> update(@ApiParam(value = "任务执行记录id", required = true) @PathVariable String id,
        @ApiParam(value = "任务执行记录 DTO", required = true) @RequestBody @Validated ParActivityPerformDTO parActivityPerformDTO);

    /**
     * 删除
     * @param id 任务执行记录id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "任务执行记录id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param parActivityPerformSearchable 任务执行记录查询条件
     * @param sort 排序条件
     * @return 任务执行记录 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<ParActivityPerformVO>> list(@ApiParam(value = "任务执行记录查询条件", required = true) @RequestBody ParActivityPerformSearchable parActivityPerformSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param parActivityPerformSearchable 任务执行记录查询条件
     * @param pageable 分页条件
     * @return 任务执行记录 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<ParActivityPerformVO>> page(@ApiParam(value = "任务执行记录查询条件", required = true) @RequestBody ParActivityPerformSearchable parActivityPerformSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

//    /**
//     * 查询
//     * @param activityId 活动id
//     * @param orgId 组织id
//     * @return 任务执行记录 VO
//     */
//    @ApiOperation(value = "查询跟踪", notes = "查询跟踪", position = 1)
//    @PostMapping("/{id}track")
//    Result<Page<ParActivityPerformVO>> listAll(@ApiParam(value = "活动id", required = true) @PathVariable String activityId,@ApiParam(value = "组织id", required = true) @PathVariable String orgId, @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 列表查询
     * @param activityId 任务执行记录查询条件
     * @return 任务执行记录 VO 集合
     */
    @ApiOperation(value = "列表查询完成比例", notes = "完成比例", position = 5)
    @PostMapping("/{activityId}perList")
    Result<List<PassPercentVO>> percent(@ApiParam(value = "活动ID", required = true) @PathVariable String activityId);

    /**
     * 列表查询各镇详情
     * @param activityId 任务执行记录查询条件
     * @param town 镇名
     * @return 任务执行记录 VO 集合
     */
    @ApiOperation(value = "列表查询完成比例", notes = "完成比例", position = 5)
    @PostMapping("/{activityId}&{town}townDetailList")
    Result<List<TownDetailVO>> townDetail(@ApiParam(value = "活动ID", required = true) @PathVariable String activityId,
                                          @ApiParam(value = "活动ID", required = true) @PathVariable String town);

    /**
     * 列表查询各镇详情
     * @param
     * @return 任务执行记录 VO 集合
     */
    @ApiOperation(value = "审核", notes = "审核", position = 6)
    @PostMapping("/check")
    Result<ParActivityPerformVO> check(@ApiParam(value = "审核记录 DTO", required = true) @RequestBody @Validated ParActivityPerformDTO parActivityPerformDTO);

    /**
     * 活动执行次数
     * @param
     * @return 任务执行记录 VO 集合
     */
    @ApiOperation(value = "执行次数", notes = "执行次数", position = 6)
    @PostMapping("/countall")
    Result<Long> countAll();


}
