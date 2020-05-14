package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskSearchable;
import com.cloudkeeper.leasing.identity.vo.CadreTaskVO;
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
import java.util.Map;

/**
 * 村书记模块任务 controller
 * @author asher
 */
@Api(value = "村书记模块任务", tags = "村书记模块任务")
@RequestMapping("/cadreTask")
public interface CadreTaskController {

    /**
     * 查询
     * @param id 村书记模块任务id
     * @return 村书记模块任务 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<CadreTaskVO> findOne(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param cadreTaskDTO 村书记模块任务 DTO
     * @return 村书记模块任务 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<CadreTaskVO> add(@ApiParam(value = "村书记模块任务 DTO", required = true) @RequestBody @Validated CadreTaskDTO cadreTaskDTO);

    /**
     * 更新
     * @param id 村书记模块任务id
     * @param cadreTaskDTO 村书记模块任务 DTO
     * @return 村书记模块任务 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<CadreTaskVO> update(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id,
        @ApiParam(value = "村书记模块任务 DTO", required = true) @RequestBody @Validated CadreTaskDTO cadreTaskDTO);

    /**
     * 删除
     * @param id 村书记模块任务id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param cadreTaskSearchable 村书记模块任务查询条件
     * @param sort 排序条件
     * @return 村书记模块任务 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<CadreTaskVO>> list(@ApiParam(value = "村书记模块任务查询条件", required = true) @RequestBody CadreTaskSearchable cadreTaskSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param cadreTaskSearchable 村书记模块任务查询条件
     * @param pageable 分页条件
     * @return 村书记模块任务 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<CadreTaskVO>> page(@ApiParam(value = "村书记模块任务查询条件", required = true) @RequestBody CadreTaskSearchable cadreTaskSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);


    @ApiOperation(value = "获取当前的任务", notes = "获取当前的任务", position = 6)
    @PostMapping("/getCurrentTask/{type}")
    Result<CadreTaskVO> getCurrentTask(@PathVariable String type, String year);

    @ApiOperation(value = "各个镇基本信息修改完成情况（统计汇总）", notes = "统计汇总", position = 6)
    @PostMapping("/list/completion")
    Result<Map<String,List>> activitiesCompletion(String year,String objectType,String taskType);

}
