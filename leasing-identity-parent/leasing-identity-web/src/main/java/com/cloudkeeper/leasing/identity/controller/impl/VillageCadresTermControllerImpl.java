package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VillageCadresTermController;
import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import com.cloudkeeper.leasing.identity.dto.villagecadresterm.VillageCadresTermDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadresterm.VillageCadresTermSearchable;
import com.cloudkeeper.leasing.identity.service.VillageCadresTermService;
import com.cloudkeeper.leasing.identity.vo.VillageCadresTermVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
 * 村主任任期信息 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresTermControllerImpl implements VillageCadresTermController {

    /** 村主任任期信息 service */
    private final VillageCadresTermService villageCadresTermService;

    @Override
    public Result<VillageCadresTermVO> findOne(@ApiParam(value = "村主任任期信息id", required = true) @PathVariable String id) {
        Optional<VillageCadresTerm> villageCadresTermOptional = villageCadresTermService.findOptionalById(id);
        return villageCadresTermOptional.map(villageCadresTerm -> Result.of(villageCadresTerm.convert(VillageCadresTermVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VillageCadresTermVO> add(@ApiParam(value = "村主任任期信息 DTO", required = true) @RequestBody @Validated VillageCadresTermDTO villageCadresTermDTO) {
        VillageCadresTerm villageCadresTerm = villageCadresTermService.save(villageCadresTermDTO.convert(VillageCadresTerm.class));
        return Result.ofAddSuccess(villageCadresTerm.convert(VillageCadresTermVO.class));
    }

    @Override
    public Result<VillageCadresTermVO> update(@ApiParam(value = "村主任任期信息id", required = true) @PathVariable String id,
        @ApiParam(value = "村主任任期信息 DTO", required = true) @RequestBody @Validated VillageCadresTermDTO villageCadresTermDTO) {
        Optional<VillageCadresTerm> villageCadresTermOptional = villageCadresTermService.findOptionalById(id);
        if (!villageCadresTermOptional.isPresent()) {
            return Result.ofLost();
        }
        VillageCadresTerm villageCadresTerm = villageCadresTermOptional.get();
        BeanUtils.copyProperties(villageCadresTermDTO, villageCadresTerm);
        villageCadresTerm = villageCadresTermService.save(villageCadresTerm);
        return Result.ofUpdateSuccess(villageCadresTerm.convert(VillageCadresTermVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村主任任期信息id", required = true) @PathVariable String id) {
        villageCadresTermService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<VillageCadresTermVO>> list(@ApiParam(value = "村主任任期信息查询条件", required = true) @RequestBody VillageCadresTermSearchable villageCadresTermSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<VillageCadresTerm> villageCadresTermList = villageCadresTermService.findAll(villageCadresTermSearchable, sort);
        List<VillageCadresTermVO> villageCadresTermVOList = VillageCadresTerm.convert(villageCadresTermList, VillageCadresTermVO.class);
        return Result.of(villageCadresTermVOList);
    }

    @Override
    public Result<Page<VillageCadresTermVO>> page(@ApiParam(value = "村主任任期信息查询条件", required = true) @RequestBody VillageCadresTermSearchable villageCadresTermSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VillageCadresTerm.class);
        if (!StringUtils.isEmpty(villageCadresTermSearchable.getHasRetire()) && "-1".equals(villageCadresTermSearchable.getHasRetire())){
            detachedCriteria.add(Restrictions.isNotNull("departureTime"));
        }
        if (!StringUtils.isEmpty(villageCadresTermSearchable.getDistrictId())){
            detachedCriteria.add(Restrictions.ilike("districtId",villageCadresTermSearchable.getDistrictId(), MatchMode.START));
        }
        if (!StringUtils.isEmpty(villageCadresTermSearchable.getCadresType())){
            detachedCriteria.add(Restrictions.ilike("cadresType",villageCadresTermSearchable.getCadresType(), MatchMode.START));
        }
        Integer total = villageCadresTermService.getTotalCount(detachedCriteria);
        pageable.getSort().forEach(item -> {
            if (item.isAscending()) {
                detachedCriteria.addOrder(Order.asc(item.getProperty()));
            } else {
                detachedCriteria.addOrder(Order.desc(item.getProperty()));
            }
        });
        Page<VillageCadresTerm> villageCadresTermPage = villageCadresTermService.findAll(detachedCriteria, pageable,total);
        Page<VillageCadresTermVO> villageCadresTermVOPage = VillageCadresTerm.convert(villageCadresTermPage, VillageCadresTermVO.class);
        return Result.of(villageCadresTermVOPage);
    }

}
