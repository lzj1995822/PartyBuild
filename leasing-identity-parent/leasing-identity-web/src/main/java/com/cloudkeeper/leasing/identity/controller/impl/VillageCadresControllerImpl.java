package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VillageCadresController;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.*;
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

    private final SysLogService sysLogService;

    @Override
    public Result<VillageCadresVO> findOne(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id) {
        Optional<VillageCadres> villageCadresOptional = villageCadresService.findOptionalById(id);
        return villageCadresOptional.map(villageCadres -> Result.of(villageCadres.convert(VillageCadresVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VillageCadresVO> add(@ApiParam(value = "村干部管理 DTO", required = true) @RequestBody @Validated VillageCadresDTO villageCadresDTO) {
        VillageCadres villageCadres = villageCadresService.save(villageCadresDTO);
        String msg;
        if (villageCadres == null) {
            msg = villageCadresService.actionLog("新增村干部失败，职位不存在","[村干部信息]", villageCadres.getName());
            sysLogService.pushLog(this.getClass().getName(),msg,villageCadresService.getTableName(),villageCadres.getId());
            return Result.ofNotFound();
        }
        msg = villageCadresService.actionLog("新增","[村干部信息]", villageCadres.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,villageCadresService.getTableName(),villageCadres.getId());
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
        String  msg= villageCadresService.actionLog("修改","[村干部信息]", villageCadres.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,villageCadresService.getTableName(),villageCadres.getId());
        return Result.ofUpdateSuccess(villageCadres.convert(VillageCadresVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id) {
        VillageCadres villageCadres = villageCadresService.findById(id);
        villageCadresService.deleteById(id);
        String  msg= villageCadresService.actionLog("删除","[村干部信息]", villageCadres.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,villageCadresService.getTableName(),villageCadres.getId());
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
        if(!StringUtils.isEmpty(villageCadresSearchable.getName())){
            //通过姓名模糊查询
            detachedCriteria.add(Restrictions.ilike("name", villageCadresSearchable.getName(), MatchMode.ANYWHERE));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getDistrictId())){
            //通过组织模糊查询
            detachedCriteria.add(Restrictions.ilike("districtId", villageCadresSearchable.getDistrictId(), MatchMode.START));
        }
        Integer total = villageCadresService.getTotalCount(detachedCriteria);
        pageable.getSort().forEach(item -> {
            if (item.isAscending()) {
                detachedCriteria.addOrder(Order.asc(item.getProperty()));
            } else {
                detachedCriteria.addOrder(Order.desc(item.getProperty()));
            }
        });
        Page<VillageCadres> villageCadresPage = villageCadresService.findAll(detachedCriteria, pageable, total);
        Page<VillageCadresVO> villageCadresVOPage = VillageCadres.convert(villageCadresPage, VillageCadresVO.class);
        return Result.of(villageCadresVOPage);
    }

    @Override
    public Result<Long> countALl(@RequestBody VillageCadresSearchable villageCadresSearchable) {
        Long aLong = villageCadresService.countAllByDistrictId(villageCadresSearchable.getDistrictId());
        return Result.of(aLong);
    }

    @Override
    public Result<Boolean> submit( @PathVariable String id) {
        Optional<VillageCadres> optionalById = villageCadresService.findOptionalById(id);

        if(optionalById.isPresent()){
            return  Result.of(villageCadresService.submit(optionalById.get()));
        }
        return Result.ofNotFound();
    }

    @Override
    public Result<Boolean> verify(@PathVariable("id") String id, @PathVariable("code") String code, @RequestBody InformationAuditDTO informationAuditDTO2) {
        return  Result.of(villageCadresService.virify(id,code,informationAuditDTO2));
    }

    @Override
    public Result<String> initPost() {
        try {
            villageCadresService.initPost();
        } catch (Exception e) {
            System.out.println(e);
            return Result.of("false");
        }

        return Result.of("true");
    }


}