package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.pardifficulty.ParDifficultyDTO;
import com.cloudkeeper.leasing.identity.dto.pardifficulty.ParDifficultySearchable;
import com.cloudkeeper.leasing.identity.vo.ParDifficultyVO;
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
 * 困难党员 controller
 * @author cqh
 */
@Api(value = "困难党员", tags = "困难党员")
@RequestMapping("/parDifficulty")
public interface ParDifficultyController {

    /**
     * 查询
     * @param id 困难党员id
     * @return 困难党员 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<ParDifficultyVO> findOne(@ApiParam(value = "困难党员id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param parDifficultyDTO 困难党员 DTO
     * @return 困难党员 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<ParDifficultyVO> add(@ApiParam(value = "困难党员 DTO", required = true) @RequestBody @Validated ParDifficultyDTO parDifficultyDTO);

    /**
     * 更新
     * @param id 困难党员id
     * @param parDifficultyDTO 困难党员 DTO
     * @return 困难党员 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<ParDifficultyVO> update(@ApiParam(value = "困难党员id", required = true) @PathVariable String id,
        @ApiParam(value = "困难党员 DTO", required = true) @RequestBody @Validated ParDifficultyDTO parDifficultyDTO);

    /**
     * 删除
     * @param id 困难党员id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "困难党员id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param parDifficultySearchable 困难党员查询条件
     * @param sort 排序条件
     * @return 困难党员 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<ParDifficultyVO>> list(@ApiParam(value = "困难党员查询条件", required = true) @RequestBody ParDifficultySearchable parDifficultySearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param parDifficultySearchable 困难党员查询条件
     * @param pageable 分页条件
     * @return 困难党员 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<ParDifficultyVO>> page(@ApiParam(value = "困难党员查询条件", required = true) @RequestBody ParDifficultySearchable parDifficultySearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

}