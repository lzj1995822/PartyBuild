package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.dto.villagecadresterm.VillageCadresTermDTO;
import com.cloudkeeper.leasing.identity.vo.CadresExamineVO;
import com.cloudkeeper.leasing.identity.vo.SecretaryNumberVO;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 村干部管理 controller
 * @author cqh
 */
@Api(value = "村干部管理", tags = "村干部管理")
@RequestMapping("/villageCadres")
public interface VillageCadresController {

/**
 * 查询
 * @param id 村干部管理id
 * @return 村干部管理 VO
 */
@ApiOperation(value = "查询", notes = "查询", position = 1)
@GetMapping("/{id}id")
    Result<VillageCadresVO> findOne(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id);


    /**
     * 查询
     * @param id 村干部管理id
     * @return 村干部管理 VO
     */
    @ApiOperation(value = "离任", notes = "离任", position = 1)
    @PostMapping("/departure")
    Result<VillageCadresVO> departure(@ApiParam(value = "村干部管理id", required = true) @RequestBody VillageCadresTermDTO villageCadresTermDTO);

    @ApiOperation(value = "获取带审核数据", notes = "离任", position = 1)
    @GetMapping("/getExamines")
    Result<List<CadresExamineVO>> getExamines(String cadresType);
/**
 * 新增
 * @param villageCadresDTO 村干部管理 DTO
 * @return 村干部管理 VO
 */
@ApiOperation(value = "新增", notes = "新增", position = 2)
@PostMapping("/")
    Result<VillageCadresVO> add(@ApiParam(value = "村干部管理 DTO", required = true) @RequestBody @Validated VillageCadresDTO villageCadresDTO);

    @ApiOperation(value = "新增基本信息", notes = "新增基本信息", position = 2)
    @PostMapping("/addBaseInfo")
    Result<VillageCadresVO> addBaseInfo(@ApiParam(value = "村干部管理 DTO", required = true) @RequestBody @Validated VillageCadresDTO villageCadresDTO);

/**
 * 更新
 * @param id 村干部管理id
 * @param villageCadresDTO 村干部管理 DTO
 * @return 村干部管理 VO
 */
@ApiOperation(value = "更新", notes = "更新", position = 3)
@PutMapping("/{id}id")
    Result<VillageCadresVO> update(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id,
@ApiParam(value = "村干部管理 DTO", required = true) @RequestBody @Validated VillageCadresDTO villageCadresDTO);

/**
 * 删除
 * @param id 村干部管理id
 * @return 删除结果
 */
@ApiOperation(value = "删除", notes = "删除", position = 4)
@DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id);

/**
 * 列表查询
 * @param villageCadresSearchable 村干部管理查询条件
 * @param sort 排序条件
 * @return 村干部管理 VO 集合
 */
@ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
@PostMapping("/list")
    Result<List<VillageCadresVO>> list(@ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable,
@ApiParam(value = "排序条件", required = true) Sort sort);

/**
 * 分页查询
 * @param villageCadresSearchable 村干部管理查询条件
 * @param pageable 分页条件
 * @return 村干部管理 VO 分页
 */
@ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
@PostMapping("/page")
    Result<Page<VillageCadresVO>> page(@ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable,
@ApiParam(value = "分页参数", required = true) Pageable pageable);

/**
 * 村干部数量
 * @param
 * @return 村干部管理 VO 分页
 */
@ApiOperation(value = "村干部数量", notes = "村干部数量", position = 6)
@PostMapping("/countall")
    Result<Long> countALl(@ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable);


/**
 * 村干部状态提交结果
 * @param
 * @return 提交结果
 */
@ApiOperation(value = "村干部状态提交结果", notes = "村干部状态提交结果", position = 6)
@PutMapping("/submit/{id}")
    Result<Boolean> submit(@ApiParam(value = "村干部状态提交结果", required = true) @PathVariable("id")String id);




/**
 * 村干部状态审核
 * @param
 * @return 审核
 */
@ApiOperation(value = "村干部审核", notes = "村干部审核", position = 6)
@PutMapping("/verify/{id}/{code}")
    Result<Boolean> verify(@ApiParam(value = "村干部审核", required = true) @PathVariable("id") String id, @PathVariable("code") String code,
@RequestBody InformationAuditDTO informationAuditDTO2);


/**
 * 初始化岗位
 * @return
 */
@GetMapping("/initPost")
    Result<String> initPost();

/**
 * 村书记各等级数量
 * @param
 * @return 村书记各等级数量
 */
@ApiOperation(value = "村书记各等级数量", notes = "村书记各等级数量", position = 6)
@PostMapping("/secretaryNumber")
    Result<SecretaryNumberVO> countNumber();


    @ApiOperation(value = "导出excel", notes = "导出excel", position = 6)
    @PostMapping("/exportExl")
    Result<SecretaryNumberVO> countNumber(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable);

    @ApiOperation(value = "init", notes = "init", position = 6)
    @PostMapping("/init")
    Result<Boolean> init();


}
