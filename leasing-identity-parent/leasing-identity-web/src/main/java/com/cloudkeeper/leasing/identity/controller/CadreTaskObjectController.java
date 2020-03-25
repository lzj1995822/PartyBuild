package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.cadretaskobject.CadreTaskObjectDTO;
import com.cloudkeeper.leasing.identity.dto.cadretaskobject.CadreTaskObjectSearchable;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
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
 * 村书记模块发布任务对象记录 controller
 * @author asher
 */
@Api(value = "村书记模块发布任务对象记录", tags = "村书记模块发布任务对象记录")
@RequestMapping("/cadreTaskObject")
public interface CadreTaskObjectController {

    /**
     * 查询
     * @param id 村书记模块发布任务对象记录id
     * @return 村书记模块发布任务对象记录 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<CadreTaskObjectVO> findOne(@ApiParam(value = "村书记模块发布任务对象记录id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param cadreTaskObjectDTO 村书记模块发布任务对象记录 DTO
     * @return 村书记模块发布任务对象记录 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<CadreTaskObjectVO> add(@ApiParam(value = "村书记模块发布任务对象记录 DTO", required = true) @RequestBody @Validated CadreTaskObjectDTO cadreTaskObjectDTO);

    /**
     * 更新
     * @param id 村书记模块发布任务对象记录id
     * @param cadreTaskObjectDTO 村书记模块发布任务对象记录 DTO
     * @return 村书记模块发布任务对象记录 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<CadreTaskObjectVO> update(@ApiParam(value = "村书记模块发布任务对象记录id", required = true) @PathVariable String id,
        @ApiParam(value = "村书记模块发布任务对象记录 DTO", required = true) @RequestBody @Validated CadreTaskObjectDTO cadreTaskObjectDTO);

    /**
     * 删除
     * @param id 村书记模块发布任务对象记录id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "村书记模块发布任务对象记录id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param cadreTaskObjectSearchable 村书记模块发布任务对象记录查询条件
     * @param sort 排序条件
     * @return 村书记模块发布任务对象记录 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<CadreTaskObjectVO>> list(@ApiParam(value = "村书记模块发布任务对象记录查询条件", required = true) @RequestBody CadreTaskObjectSearchable cadreTaskObjectSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param cadreTaskObjectSearchable 村书记模块发布任务对象记录查询条件
     * @param pageable 分页条件
     * @return 村书记模块发布任务对象记录 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<CadreTaskObjectVO>> page(@ApiParam(value = "村书记模块发布任务对象记录查询条件", required = true) @RequestBody CadreTaskObjectSearchable cadreTaskObjectSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 走任务流程
     * @return 村书记模块发布任务对象记录 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "走任务流程", position = 6)
    @PostMapping("/progressNext")
    Result<CadreTaskObjectVO> progressNext(@ApiParam(value = "走任务流程", required = true) String taskObjectId, String isSuccess, String auditor, String auditAdvice);

}