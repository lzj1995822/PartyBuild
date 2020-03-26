package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationDTO;
import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationSearchable;
import com.cloudkeeper.leasing.identity.vo.KPIEvaluationVO;
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
 * 综合评议 controller
 * @author yujian
 */
@Api(value = "综合评议", tags = "综合评议")
@RequestMapping("/kPIEvaluation")
public interface KPIEvaluationController {

    /**
     * 查询
     * @param id 综合评议id
     * @return 综合评议 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<KPIEvaluationVO> findOne(@ApiParam(value = "综合评议id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param kPIEvaluationDTO 综合评议 DTO
     * @return 综合评议 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<KPIEvaluationVO> add(@ApiParam(value = "综合评议 DTO", required = true) @RequestBody @Validated KPIEvaluationDTO kPIEvaluationDTO);

    /**
     * 更新
     * @param id 综合评议id
     * @param kPIEvaluationDTO 综合评议 DTO
     * @return 综合评议 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<KPIEvaluationVO> update(@ApiParam(value = "综合评议id", required = true) @PathVariable String id,
        @ApiParam(value = "综合评议 DTO", required = true) @RequestBody @Validated KPIEvaluationDTO kPIEvaluationDTO);

    /**
     * 删除
     * @param id 综合评议id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "综合评议id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param kPIEvaluationSearchable 综合评议查询条件
     * @param sort 排序条件
     * @return 综合评议 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<KPIEvaluationVO>> list(@ApiParam(value = "综合评议查询条件", required = true) @RequestBody KPIEvaluationSearchable kPIEvaluationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param kPIEvaluationSearchable 综合评议查询条件
     * @param pageable 分页条件
     * @return 综合评议 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<KPIEvaluationVO>> page(@ApiParam(value = "综合评议查询条件", required = true) @RequestBody KPIEvaluationSearchable kPIEvaluationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 新增
     * @param kPIEvaluationDTO 综合评议 DTO
     * @return 综合评议 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/addEvaluations")
    Result<Object> addEvaluations(@ApiParam(value = "综合评议 DTO", required = true) @RequestBody @Validated List<KPIEvaluationDTO> kPIEvaluationDTO);

    @ApiOperation(value = "根据任务ID获取数据", notes = "根据任务ID获取数据", position = 2)
    @PostMapping("/getEvaluations")
    Result<Object> getEvaluations(@RequestBody KPIEvaluationSearchable kpiEvaluationSearchable);

}
