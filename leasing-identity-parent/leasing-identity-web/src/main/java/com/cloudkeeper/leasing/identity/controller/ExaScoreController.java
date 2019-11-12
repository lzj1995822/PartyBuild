package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.exascore.ExaScoreDTO;
import com.cloudkeeper.leasing.identity.dto.exascore.ExaScoreSearchable;
import com.cloudkeeper.leasing.identity.vo.*;
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
 * 考核积分 controller
 * @author lxw
 */
@Api(value = "考核积分", tags = "考核积分")
@RequestMapping("/exaScore")
public interface ExaScoreController {

    /**
     * 查询
     * @param id 考核积分id
     * @return 考核积分 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<ExaScoreVO> findOne(@ApiParam(value = "考核积分id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param exaScoreDTO 考核积分 DTO
     * @return 考核积分 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<ExaScoreVO> add(@ApiParam(value = "考核积分 DTO", required = true) @RequestBody @Validated ExaScoreDTO exaScoreDTO);

    /**
     * 更新
     * @param id 考核积分id
     * @param exaScoreDTO 考核积分 DTO
     * @return 考核积分 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<ExaScoreVO> update(@ApiParam(value = "考核积分id", required = true) @PathVariable String id,
        @ApiParam(value = "考核积分 DTO", required = true) @RequestBody @Validated ExaScoreDTO exaScoreDTO);

    /**
     * 删除
     * @param id 考核积分id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "考核积分id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param exaScoreSearchable 考核积分查询条件
     * @param sort 排序条件
     * @return 考核积分 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<ExaScoreVO>> list(@ApiParam(value = "考核积分查询条件", required = true) @RequestBody ExaScoreSearchable exaScoreSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param exaScoreSearchable 考核积分查询条件
     * @param pageable 分页条件
     * @return 考核积分 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<ExaScoreVO>> page(@ApiParam(value = "考核积分查询条件", required = true) @RequestBody ExaScoreSearchable exaScoreSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 列表查询各村分数
     * @return 考核审核分数 VO 集合
     */
    @ApiOperation(value = "列表查询各村分数", notes = "", position = 5)
    @PostMapping("/scoreCunPercentAll")
    Result<List<ExamScoreVO>> scoreCun(@ApiParam(value = "分页参数", required = true) Pageable pageable,@RequestParam String sort, @RequestParam String year);

    /**
     * 列表查询各镇分数
     * @return 考核审核分数 VO 集合
     */
    @ApiOperation(value = "列表查询各镇分数", notes = "", position = 5)
    @PostMapping("/percentTown")
    Result<List<ExamScorePercentVO>> percentTown(@ApiParam(value = "年份", required = true)String year);

    /**
     * 列表查询各村分数
     *  @param year 镇名
     * @param townName 年份
     * @return 考核审核分数 VO 集合
     */
    @ApiOperation(value = "列表查询各村分数", notes = "", position = 5)
    @PostMapping("/percentCun{year}Y{townName}T")
    Result<List<ExamScorePercentVO>> percentCun(@ApiParam(value = "年份", required = true) @PathVariable String year, @ApiParam(value = "镇名", required = true) @PathVariable String townName);

    /**
     * 列表查询总数据
     *  @param year 镇名
     * @return 总数据 VO 集合
     */
    @ApiOperation(value = "列表查询各村分数", notes = "", position = 5)
    @PostMapping("/examScoreAll")
     Result<List<ActivityExamVO>> examScoreAll(@ApiParam(value = "分页参数", required = true) Pageable pageable, @ApiParam(value = "年份", required = true)String year,
                                               @ApiParam(value = "搜索", required = true)String search, @ApiParam(value = "搜索", required = true)String districtType);
}
