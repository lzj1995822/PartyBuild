package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.exaexamine.ExaExamineDTO;
import com.cloudkeeper.leasing.identity.dto.exaexamine.ExaExamineSearchable;
import com.cloudkeeper.leasing.identity.vo.ExaExamineVO;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;
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
 * 考核审核 controller
 * @author lxw
 */
@Api(value = "考核审核", tags = "考核审核")
@RequestMapping("/exaExamine")
public interface ExaExamineController {

    /**
     * 查询
     * @param id 考核审核id
     * @return 考核审核 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<ExaExamineVO> findOne(@ApiParam(value = "考核审核id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param exaExamineDTO 考核审核 DTO
     * @return 考核审核 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<ExaExamineVO> add(@ApiParam(value = "考核审核 DTO", required = true) @RequestBody @Validated ExaExamineDTO exaExamineDTO);

    /**
     * 更新
     * @param id 考核审核id
     * @param exaExamineDTO 考核审核 DTO
     * @return 考核审核 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<ExaExamineVO> update(@ApiParam(value = "考核审核id", required = true) @PathVariable String id,
        @ApiParam(value = "考核审核 DTO", required = true) @RequestBody @Validated ExaExamineDTO exaExamineDTO);

    /**
     * 删除
     * @param id 考核审核id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "考核审核id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param exaExamineSearchable 考核审核查询条件
     * @param sort 排序条件
     * @return 考核审核 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<ExaExamineVO>> list(@ApiParam(value = "考核审核查询条件", required = true) @RequestBody ExaExamineSearchable exaExamineSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param exaExamineSearchable 考核审核查询条件
     * @param pageable 分页条件
     * @return 考核审核 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<ExaExamineVO>> page(@ApiParam(value = "考核审核查询条件", required = true) @RequestBody ExaExamineSearchable exaExamineSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);


    /**
     * 列表查询各镇分数
     * @return 考核审核分数 VO 集合
     */
    @ApiOperation(value = "列表查询各镇分数", notes = "", position = 5)
    @PostMapping("/scoreTown")
    Result<List<ExamScoreVO>> scoreTown(@ApiParam(value = "年份", required = true)String year);

    /**
     * 列表查询各村分数
     * @return 考核审核分数 VO 集合
     */
    @ApiOperation(value = "列表查询各村分数", notes = "", position = 5)
    @PostMapping("/scoreCun")
    Result<List<ExamScoreVO>> scoreCun(@ApiParam(value = "镇名", required = true) @PathVariable String townName);
}
