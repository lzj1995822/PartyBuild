package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectSearchable;
import com.cloudkeeper.leasing.identity.vo.ExamScoreDetailVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
import com.cloudkeeper.leasing.base.model.Result;
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
 * 任务对象 controller
 * @author lxw
 */
@Api(value = "任务对象", tags = "任务对象")
@RestController
@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RequestMapping("/parActivityObject")
public interface ParActivityObjectController {

    /**
     * 查询
     * @param id 任务对象id
     * @return 任务对象 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<ParActivityObjectVO> findOne(@ApiParam(value = "任务对象id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param parActivityObjectDTO 任务对象 DTO
     * @return 任务对象 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<ParActivityObjectVO> add(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO);

    /**
     * 更新
     * @param id 任务对象id
     * @param parActivityObjectDTO 任务对象 DTO
     * @return 任务对象 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<ParActivityObjectVO> update(@ApiParam(value = "任务对象id", required = true) @PathVariable String id,
        @ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO);

    /**
     * 删除
     * @param id 任务对象id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "任务对象id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param parActivityObjectSearchable 任务对象查询条件
     * @param sort 排序条件
     * @return 任务对象 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<ParActivityObjectVO>> list(@ApiParam(value = "任务对象查询条件", required = true) @RequestBody ParActivityObjectSearchable parActivityObjectSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param parActivityObjectSearchable 任务对象查询条件
     * @param pageable 分页条件
     * @return 任务对象 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<ParActivityObjectVO>> page(@ApiParam(value = "任务对象查询条件", required = true) @RequestBody ParActivityObjectSearchable parActivityObjectSearchable,
                                           @ApiParam(value = "分页参数", required = true) Pageable pageable);


    @ApiOperation(value = "根据组织id和活动id查", position = 6)
    @Authorization(required = false)
    @PostMapping("/findByOrganizationIdAndActivityId")
    Result<ParActivityObjectVO> findByOrganizationIdAndActivityId(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO);

    @GetMapping("/init")
    Result<Boolean> init();

    /**
     * 执行
     * @param parActivityObjectDTO 活动id
     * @param  sort 组织ID
     * @return 任务对象 VO 分页
     */
    @ApiOperation(value = "执行", notes = "activityId 活动id，organizationId 组织ID", position = 6)
    @Authorization(required = false)
    @PostMapping("/execute")
    Result<List<ParActivityObjectVO>> execute(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO,
                                              @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 结束执行
     * @param parActivityObjectDTO 活动id
     * @param  sort 组织ID
     * @return 任务对象 VO 分页
     */
    @ApiOperation(value = "结束执行", notes = "activityId 活动id，organizationId 组织ID", position = 6)
    @Authorization(required = false)
    @PostMapping("/executeOver")
    Result<List<ParActivityObjectVO>> executeOver(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO,
                                              @ApiParam(value = "排序条件", required = true) Sort sort);

    @GetMapping("/number/{number}")
    Result<List<ParActivityObjectVO>> TVIndexDetailList(@PathVariable String number);

    @GetMapping("/checkNumber/organizationId{organizationId}")
    Result<Integer> waitCheckNumber(@PathVariable String organizationId);

    @ApiOperation(value="考核具体信息",notes= "查询" ,position = 0)
    @PostMapping("/examScoreDetail")
    Result<List<ExamScoreDetailVO>> examScoreDetail(@ApiParam(value="组织名字",required = true) String districtName,@ApiParam(value="年份",required=true) String year);


    @ApiOperation(value = "机关PC端执行任务", notes = "activityId 活动id，organizationId 组织ID", position = 6)
    @PostMapping("/officeExecute/{id}id")
    Result<ParActivityObjectVO> officeExecute(@PathVariable String id);
}
