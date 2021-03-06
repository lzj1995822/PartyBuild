package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CadrePositionController;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.cadreposition.CadrePositionDTO;
import com.cloudkeeper.leasing.identity.dto.cadreposition.CadrePositionSearchable;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.CadrePositionVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 岗位管理 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadrePositionControllerImpl implements CadrePositionController {

    /** 岗位管理 service */
    private final CadrePositionService cadrePositionService;

    private final SysDistrictService sysDistrictService;

    private final SysLogService sysLogService;

    @Override
    public Result<CadrePositionVO> findOne(@ApiParam(value = "岗位管理id", required = true) @PathVariable String id) {
        Optional<CadrePosition> cadrePositionOptional = cadrePositionService.findOptionalById(id);
        return cadrePositionOptional.map(cadrePosition -> Result.of(cadrePosition.convert(CadrePositionVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<CadrePositionVO> add(@ApiParam(value = "岗位管理 DTO", required = true) @RequestBody @Validated CadrePositionDTO cadrePositionDTO) {
        CadrePosition cadrePosition = cadrePositionService.save(cadrePositionDTO.convert(CadrePosition.class));
        String  msg= cadrePositionService.actionLog("新增","[岗位信息]", cadrePosition.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,cadrePositionService.getTableName(),cadrePosition.getId());
        return Result.ofAddSuccess(cadrePosition.convert(CadrePositionVO.class));
    }

    @Override
    public Result<CadrePositionVO> update(@ApiParam(value = "岗位管理id", required = true) @PathVariable String id,
        @ApiParam(value = "岗位管理 DTO", required = true) @RequestBody @Validated CadrePositionDTO cadrePositionDTO) {
        Optional<CadrePosition> cadrePositionOptional = cadrePositionService.findOptionalById(id);
        if (!cadrePositionOptional.isPresent()) {
            return Result.ofLost();
        }
        CadrePosition cadrePosition = cadrePositionOptional.get();
        BeanUtils.copyProperties(cadrePositionDTO, cadrePosition);
        cadrePosition = cadrePositionService.save(cadrePosition);
        String  msg= cadrePositionService.actionLog("修改","[岗位信息]", cadrePosition.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,cadrePositionService.getTableName(),cadrePosition.getId());
        return Result.ofUpdateSuccess(cadrePosition.convert(CadrePositionVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "岗位管理id", required = true) @PathVariable String id) {
        CadrePosition cadrePosition = cadrePositionService.findById(id);
        cadrePositionService.deleteById(id);
        String  msg= cadrePositionService.actionLog("删除","[岗位信息]", cadrePosition.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,cadrePositionService.getTableName(),cadrePosition.getId());
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<CadrePositionVO>> list(@ApiParam(value = "岗位管理查询条件", required = true) @RequestBody CadrePositionSearchable cadrePositionSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<CadrePosition> cadrePositionList = cadrePositionService.findAll(cadrePositionSearchable, sort);
        List<CadrePositionVO> cadrePositionVOList = CadrePosition.convert(cadrePositionList, CadrePositionVO.class);
        return Result.of(cadrePositionVOList);
    }

    @Override
    public Result<Page<CadrePositionVO>> page(@ApiParam(value = "岗位管理查询条件", required = true) @RequestBody CadrePositionSearchable cadrePositionSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<CadrePosition> cadrePositionPage = cadrePositionService.findAll(cadrePositionSearchable, pageable);
        Page<CadrePositionVO> cadrePositionVOPage = CadrePosition.convert(cadrePositionPage, CadrePositionVO.class);
        return Result.of(cadrePositionVOPage);
    }

    @Override
    public Result<Boolean> init() {
        List<SysDistrict> party = sysDistrictService.findAllByDistrictLevelAndDistrictType(3, "Party");
        for (SysDistrict sysDistrict: party) {
            CadrePosition cadrePosition = new CadrePosition();
            cadrePosition.setDistrictId(sysDistrict.getDistrictId());
            cadrePosition.setPost("SECRETARY");
            cadrePosition.setParentDistrictId(sysDistrict.getOrgParent());
            cadrePosition.setName(sysDistrict.getDistrictName() + "书记");
            cadrePositionService.save(cadrePosition);
        }
        return Result.of(true);
    }

    @Override
    public Result<Page<CadrePositionVO>> isExist( @RequestBody CadrePositionSearchable cadrePositionSearchable, Pageable pageable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadrePosition.class);
        if (!StringUtils.isEmpty(cadrePositionSearchable.getDistrictId())) {
            detachedCriteria.add(Restrictions.and(Restrictions.like("districtId", cadrePositionSearchable.getDistrictId(), MatchMode.START)));
        }
        if (!StringUtils.isEmpty(cadrePositionSearchable.getPost())) {
            detachedCriteria.add(Restrictions.and(Restrictions.like("post", cadrePositionSearchable.getPost(), MatchMode.START)));
        }
        if (!StringUtils.isEmpty(cadrePositionSearchable.getIsExist())) {
            if (cadrePositionSearchable.getIsExist().equals("1")) {
                detachedCriteria.add(Restrictions.or(Restrictions.ne("cadreId", null), Property.forName("cadreId").isNotNull()));
            } else {
                detachedCriteria.add(Restrictions.or(Restrictions.eq("cadreId", null), Property.forName("cadreId").isNull()));
            }
        }
        Page<CadrePosition> cadrePositionPage = cadrePositionService.findAll(detachedCriteria, pageable);
        Page<CadrePositionVO> cadrePositionVOPage = CadrePosition.convert(cadrePositionPage, CadrePositionVO.class);
        return Result.of(cadrePositionVOPage);
    }

}