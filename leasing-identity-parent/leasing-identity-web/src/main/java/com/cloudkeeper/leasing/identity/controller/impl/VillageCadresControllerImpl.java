package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VillageCadresController;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
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
 * 村干部管理 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresControllerImpl implements VillageCadresController {

    /** 村干部管理 service */
    private final VillageCadresService villageCadresService;

    @Override
    public Result<VillageCadresVO> findOne(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id) {
        Optional<VillageCadres> villageCadresOptional = villageCadresService.findOptionalById(id);
        return villageCadresOptional.map(villageCadres -> Result.of(villageCadres.convert(VillageCadresVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VillageCadresVO> add(@ApiParam(value = "村干部管理 DTO", required = true) @RequestBody @Validated VillageCadresDTO villageCadresDTO) {
        VillageCadres villageCadres = villageCadresService.save(villageCadresDTO.convert(VillageCadres.class));
        return Result.ofAddSuccess(villageCadres.convert(VillageCadresVO.class));
    }

    @Override
    public Result<VillageCadresVO> update(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id,
        @ApiParam(value = "村干部管理 DTO", required = true) @RequestBody @Validated VillageCadresDTO villageCadresDTO) {
        Optional<VillageCadres> villageCadresOptional = villageCadresService.findOptionalById(id);
        if (!villageCadresOptional.isPresent()) {
            return Result.ofLost();
        }
        VillageCadres villageCadres = villageCadresOptional.get();
        BeanUtils.copyProperties(villageCadresDTO, villageCadres);
        villageCadres = villageCadresService.save(villageCadres);
        return Result.ofUpdateSuccess(villageCadres.convert(VillageCadresVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id) {
        villageCadresService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<VillageCadresVO>> list(@ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<VillageCadres> villageCadresList = villageCadresService.findAll(villageCadresSearchable, sort);
        List<VillageCadresVO> villageCadresVOList = VillageCadres.convert(villageCadresList, VillageCadresVO.class);
        return Result.of(villageCadresVOList);
    }

    @Override
    public Result<Page<VillageCadresVO>> page(@ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VillageCadres.class);
        if (!StringUtils.isEmpty(villageCadresSearchable.getPost())) {
            detachedCriteria.createAlias("cadrePosition","a");
            detachedCriteria.add(Restrictions.eq("a.post", villageCadresSearchable.getPost()));
        }
        Page<VillageCadres> villageCadresPage = villageCadresService.findAll(detachedCriteria, pageable);
        Page<VillageCadresVO> villageCadresVOPage = VillageCadres.convert(villageCadresPage, VillageCadresVO.class);
        return Result.of(villageCadresVOPage);
    }

}