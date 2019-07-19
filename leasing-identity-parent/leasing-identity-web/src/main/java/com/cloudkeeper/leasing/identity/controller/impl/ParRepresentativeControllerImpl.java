package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParRepresentativeController;
import com.cloudkeeper.leasing.identity.domain.ParRepresentative;
import com.cloudkeeper.leasing.identity.dto.parrepresentative.ParRepresentativeDTO;
import com.cloudkeeper.leasing.identity.dto.parrepresentative.ParRepresentativeSearchable;
import com.cloudkeeper.leasing.identity.service.ParRepresentativeService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ParRepresentativeVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 党代表 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParRepresentativeControllerImpl implements ParRepresentativeController {

    /** 党代表 service */
    private final ParRepresentativeService parRepresentativeService;

    private final SysLogService sysLogService;

    @Override
    public Result<ParRepresentativeVO> findOne(@ApiParam(value = "党代表id", required = true) @PathVariable String id) {
        Optional<ParRepresentative> parRepresentativeOptional = parRepresentativeService.findOptionalById(id);
        return parRepresentativeOptional.map(parRepresentative -> Result.of(parRepresentative.convert(ParRepresentativeVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParRepresentativeVO> add(@ApiParam(value = "党代表 DTO", required = true) @RequestBody @Validated ParRepresentativeDTO parRepresentativeDTO) {
        ParRepresentative parRepresentative = parRepresentativeService.save(parRepresentativeDTO.convert(ParRepresentative.class));
        String  msg= parRepresentativeService.actionLog("新增","[党代表信息]", parRepresentative.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parRepresentativeService.getTableName(),parRepresentative.getId());
        return Result.ofAddSuccess(parRepresentative.convert(ParRepresentativeVO.class));
    }

    @Override
    public Result<ParRepresentativeVO> update(@ApiParam(value = "党代表id", required = true) @PathVariable String id,
        @ApiParam(value = "党代表 DTO", required = true) @RequestBody @Validated ParRepresentativeDTO parRepresentativeDTO) {
        Optional<ParRepresentative> parRepresentativeOptional = parRepresentativeService.findOptionalById(id);
        if (!parRepresentativeOptional.isPresent()) {
            return Result.ofLost();
        }
        ParRepresentative parRepresentative = parRepresentativeOptional.get();
        BeanUtils.copyProperties(parRepresentativeDTO, parRepresentative);
        parRepresentative = parRepresentativeService.save(parRepresentative);
        String  msg= parRepresentativeService.actionLog("修改","[党代表信息]", parRepresentative.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parRepresentativeService.getTableName(),parRepresentative.getId());
        return Result.ofUpdateSuccess(parRepresentative.convert(ParRepresentativeVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "党代表id", required = true) @PathVariable String id) {
        ParRepresentative parRepresentative = parRepresentativeService.findById(id);
        parRepresentativeService.deleteById(id);
        String  msg= parRepresentativeService.actionLog("删除","[党代表信息]", parRepresentative.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parRepresentativeService.getTableName(),parRepresentative.getId());
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParRepresentativeVO>> list(@ApiParam(value = "党代表查询条件", required = true) @RequestBody ParRepresentativeSearchable parRepresentativeSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParRepresentative> parRepresentativeList = parRepresentativeService.findAll(parRepresentativeSearchable, sort);
        List<ParRepresentativeVO> parRepresentativeVOList = ParRepresentative.convert(parRepresentativeList, ParRepresentativeVO.class);
        return Result.of(parRepresentativeVOList);
    }

    @Override
    public Result<Page<ParRepresentativeVO>> page(@ApiParam(value = "党代表查询条件", required = true) @RequestBody ParRepresentativeSearchable parRepresentativeSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParRepresentative> parRepresentativePage = parRepresentativeService.findAll(parRepresentativeSearchable, pageable);
        Page<ParRepresentativeVO> parRepresentativeVOPage = ParRepresentative.convert(parRepresentativePage, ParRepresentativeVO.class);
        return Result.of(parRepresentativeVOPage);
    }

}