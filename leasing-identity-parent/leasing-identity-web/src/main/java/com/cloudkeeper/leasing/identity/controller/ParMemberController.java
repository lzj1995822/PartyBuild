package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.identity.dto.parmember.ParMemberDTO;
import com.cloudkeeper.leasing.identity.dto.parmember.ParMemberSearchable;
import com.cloudkeeper.leasing.identity.vo.ParMemberChartsVo;
import com.cloudkeeper.leasing.identity.vo.ParMemberVO;
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
 * 党员管理 controller
 * @author cqh
 */
@Api(value = "党员管理", tags = "党员管理")
@RequestMapping("/parMember")
public interface ParMemberController {

    /**
     * 查询
     * @param id 党员管理id
     * @return 党员管理 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<ParMemberVO> findOne(@ApiParam(value = "党员管理id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param parMemberDTO 党员管理 DTO
     * @return 党员管理 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<ParMemberVO> add(@ApiParam(value = "党员管理 DTO", required = true) @RequestBody @Validated ParMemberDTO parMemberDTO);

    /**
     * 更新
     * @param id 党员管理id
     * @param parMemberDTO 党员管理 DTO
     * @return 党员管理 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<ParMemberVO> update(@ApiParam(value = "党员管理id", required = true) @PathVariable String id,
        @ApiParam(value = "党员管理 DTO", required = true) @RequestBody @Validated ParMemberDTO parMemberDTO);

    /**
     * 删除
     * @param id 党员管理id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "党员管理id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param parMemberSearchable 党员管理查询条件
     * @param sort 排序条件
     * @return 党员管理 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<ParMemberVO>> list(@ApiParam(value = "党员管理查询条件", required = true) @RequestBody ParMemberSearchable parMemberSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param parMemberSearchable 党员管理查询条件
     * @param pageable 分页条件
     * @return 党员管理 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<ParMemberVO>> page(@ApiParam(value = "党员管理查询条件", required = true) @RequestBody ParMemberSearchable parMemberSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);


    /**
     * 按性别统计
     * @param  districtId 党员组织
     * @return 查询结果
     */
    @ApiOperation(value = "按性别统计", notes = "按性别统计", position = 4)
    @PostMapping("/statisticsSex/{districtId}")
    @Authorization(required = true)
    Result<List<ParMemberChartsVo>> statisticsSex( @PathVariable String districtId);

    /**
     * 按年龄统计
     * @param  districtId 党员组织
     * @return 查询结果
     */
    @ApiOperation(value = "按年龄统计", notes = "按年龄统计", position = 4)
    @PostMapping("/statisticsAge/{districtId}")
    @Authorization(required = true)
    Result<List<ParMemberChartsVo>> statisticsAge(@ApiParam(value = "党员管理id", required = true) @PathVariable String districtId);

    /**
     * 按支部统计
     * @param  districtId 党员组织
     * @return 查询结果
     */
    @ApiOperation(value = "按支部统计", notes = "按支部统计", position = 4)
    @PostMapping("/statisticsBranch/{districtId}")
    @Authorization(required = true)
    Result<List<ParMemberChartsVo>> statisticsBranch(@ApiParam(value = "党员管理id", required = true) @PathVariable String districtId);

}