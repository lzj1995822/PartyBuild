package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPITownQuotaController;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaSearchable;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaSearchable;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaDTO;
import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
import com.cloudkeeper.leasing.identity.service.KPIVillageQuotaService;
import com.cloudkeeper.leasing.identity.service.KpiQuotaService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.vo.KPITownQuotaVO;
import com.cloudkeeper.leasing.identity.vo.KPIVillageQuotaVO;
import com.cloudkeeper.leasing.identity.vo.KpiQuotaVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 镇考核指标 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPITownQuotaControllerImpl implements KPITownQuotaController {

    /** 镇考核指标 service */
    private final KPITownQuotaService kPITownQuotaService;
    /** 村主任考核指标 service */
    private final KpiQuotaService kpiQuotaService;

    private final KPIVillageQuotaService kpiVillageQuotaService;

    /** 组织 service */
    private final SysDistrictService sysDistrictService;
    @Override
    public Result<KPITownQuotaVO> findOne(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id) {
        Optional<KPITownQuota> kPITownQuotaOptional = kPITownQuotaService.findOptionalById(id);
        return kPITownQuotaOptional.map(kPITownQuota -> Result.of(kPITownQuota.convert(KPITownQuotaVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KPITownQuotaVO> add(@ApiParam(value = "镇考核指标 DTO", required = true) @RequestBody @Validated KPITownQuotaDTO kPITownQuotaDTO) {
        KPITownQuota kPITownQuota = kPITownQuotaService.save(kPITownQuotaDTO.convert(KPITownQuota.class));
        return Result.ofAddSuccess(kPITownQuota.convert(KPITownQuotaVO.class));
    }

    @Override
    public Result<KPITownQuotaVO> update(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id,
        @ApiParam(value = "镇考核指标 DTO", required = true) @RequestBody @Validated KPITownQuotaDTO kPITownQuotaDTO) {
        Optional<KPITownQuota> kPITownQuotaOptional = kPITownQuotaService.findOptionalById(id);
        if (!kPITownQuotaOptional.isPresent()) {
            return Result.ofLost();
        }
        KPITownQuota kPITownQuota = kPITownQuotaOptional.get();
        BeanUtils.copyProperties(kPITownQuotaDTO, kPITownQuota);
        kPITownQuota = kPITownQuotaService.save(kPITownQuota);
        return Result.ofUpdateSuccess(kPITownQuota.convert(KPITownQuotaVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id) {
        kPITownQuotaService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KPITownQuotaVO>> list(@ApiParam(value = "镇考核指标查询条件", required = true) @RequestBody KPITownQuotaSearchable kPITownQuotaSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPITownQuota> kPITownQuotaList = kPITownQuotaService.findAll(kPITownQuotaSearchable, sort);
        List<KPITownQuotaVO> kPITownQuotaVOList = KPITownQuota.convert(kPITownQuotaList, KPITownQuotaVO.class);
        return Result.of(kPITownQuotaVOList);
    }

    @Override
    public Result<Page<KPITownQuotaVO>> page(@ApiParam(value = "镇考核指标查询条件", required = true) @RequestBody KPITownQuotaSearchable kPITownQuotaSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPITownQuota> kPITownQuotaPage = kPITownQuotaService.findAll(kPITownQuotaSearchable, pageable);
        Page<KPITownQuotaVO> kPITownQuotaVOPage = KPITownQuota.convert(kPITownQuotaPage, KPITownQuotaVO.class);
        return Result.of(kPITownQuotaVOPage);
    }

    @Override
    public Result<Object> getAll(@PathVariable String districtId,@PathVariable String parentQuotaId) {
        KpiQuotaSearchable kpiQuotaSearchable = new KpiQuotaSearchable();
        kpiQuotaSearchable.setParentQuotaId(parentQuotaId);
        String d = districtId;
        List<KpiQuota> kpiQuotas = kpiQuotaService.findAll(kpiQuotaSearchable,new Sort(Sort.Direction.DESC,"parentQuotaId"));
        List<KpiQuotaVO> quotaVOList = KpiQuota.convert(kpiQuotas,KpiQuotaVO.class);
        for (KpiQuotaVO k : quotaVOList){
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
            detachedCriteria.add(Restrictions.eq("parentQuotaId", k.getQuotaId()));
            detachedCriteria.add(Restrictions.eq("districtId",d));
            detachedCriteria.addOrder(Order.desc("createdAt"));
            List<KPITownQuota> kpiTownQuotas = kPITownQuotaService.findAll(detachedCriteria);
            List<KPITownQuotaVO> kpiTownQuotaVOS = KPITownQuota.convert(kpiTownQuotas,KPITownQuotaVO.class);
            k.setKpiTownQuotas(kpiTownQuotaVOS);
        }

        List<KPITownQuotaVO> kpiTownQuotaVOS ;
        if (CollectionUtils.isEmpty(quotaVOList.get(0).getKpiTownQuotas())){//此处为了前端初始化考核指标中村数据
            //二级为空，需要初始化一个二级和三级
            kpiTownQuotaVOS = new ArrayList<>();//二级
            KPITownQuotaVO vo = new KPITownQuotaVO();
            String sql = "SELECT districtId,districtName FROM SYS_District WHERE attachTo ="+districtId+" order by districtId desc";
            List<KPIVillageQuotaVO> kpiVillageQuotaVOS1 = sysDistrictService.findAllBySql(KPIVillageQuotaVO.class,sql);
            vo.setKpiVillageQuotas(kpiVillageQuotaVOS1);
            kpiTownQuotaVOS.add(vo);
            KpiQuotaVO k = quotaVOList.get(0);
            k.setKpiTownQuotas(kpiTownQuotaVOS);
            quotaVOList.set(0,k);
        }else if (CollectionUtils.isEmpty(quotaVOList.get(0).getKpiTownQuotas().get(0).getKpiVillageQuotas())){
            //三级为空，需要初始化一个三级
            kpiTownQuotaVOS = quotaVOList.get(0).getKpiTownQuotas();//二级
            KPITownQuotaVO vo = new KPITownQuotaVO();
            String sql = "SELECT districtId,districtName FROM SYS_District WHERE attachTo ="+districtId+" order by districtId desc";
            List<KPIVillageQuotaVO> kpiVillageQuotaVOS1 = sysDistrictService.findAllBySql(KPIVillageQuotaVO.class,sql);
            vo.setKpiVillageQuotas(kpiVillageQuotaVOS1);
            kpiTownQuotaVOS.set(0,vo);
            KpiQuotaVO k = quotaVOList.get(0);
            k.setKpiTownQuotas(kpiTownQuotaVOS);
            quotaVOList.set(0,k);
        }
        return Result.of(quotaVOList);
    }

    @Override
    @Transactional
    public Result<Object> addAll(@RequestBody List<KpiQuotaDTO> kpiQuotas) {
        //需要先删除
        List<String> quarters = Arrays.asList("第一季度", "第二季度", "第三季度","第四季度");
        for (KpiQuotaDTO kpiQuota : kpiQuotas){
            for (KPITownQuotaDTO kpiTownQuotaDTO : kpiQuota.getKpiTownQuotas()){

                kPITownQuotaService.deleteAllByDistrictIdAndParentQuotaId(kpiTownQuotaDTO.getDistrictId(),kpiTownQuotaDTO.getParentQuotaId());//删除二级
                kpiVillageQuotaService.deleteAllByParentDistrictIdAndParentQuotaId(kpiTownQuotaDTO.getDistrictId(),kpiTownQuotaDTO.getParentQuotaId());//删除三级

                if ("01".equals(kpiQuota.getParentQuotaId())){//日常需要增加四个季度数据
                    for(String quarter : quarters){
                        kpiTownQuotaDTO.setParentQuotaId(kpiQuota.getQuotaId());
                        kpiTownQuotaDTO.setParentQuotaName(kpiQuota.getQuotaName());
                        KPITownQuota kpiTownQuota = kpiTownQuotaDTO.convert(KPITownQuota.class);
                        kpiTownQuota.setKpiVillageQuotas(null);
                        KPITownQuota kPITownQuota = kPITownQuotaService.save(kpiTownQuota);//保存二级

                        for (KPIVillageQuotaDTO kpiVillageQuotaDTO : kpiTownQuotaDTO.getKpiVillageQuotas()){
                            kpiVillageQuotaDTO.setParentDistrictId(kPITownQuota.getDistrictId());
                            kpiVillageQuotaDTO.setTownQuotaId(kPITownQuota.getId());
                            kpiVillageQuotaDTO.setParentQuotaId(kPITownQuota.getParentQuotaId());
                            kpiVillageQuotaDTO.setQuarter(quarter);
                            KPIVillageQuota kpiVillageQuota = kpiVillageQuotaDTO.convert(KPIVillageQuota.class);
                            kpiVillageQuotaService.save(kpiVillageQuota);//保存三级
                        }
                    }
                }else {
                    kpiTownQuotaDTO.setParentQuotaId(kpiQuota.getQuotaId());
                    kpiTownQuotaDTO.setParentQuotaName(kpiQuota.getQuotaName());
                    KPITownQuota kpiTownQuota = kpiTownQuotaDTO.convert(KPITownQuota.class);
                    kpiTownQuota.setKpiVillageQuotas(null);
                    KPITownQuota kPITownQuota = kPITownQuotaService.save(kpiTownQuota);//保存二级

                    for (KPIVillageQuotaDTO kpiVillageQuotaDTO : kpiTownQuotaDTO.getKpiVillageQuotas()){
                        kpiVillageQuotaDTO.setParentDistrictId(kPITownQuota.getDistrictId());
                        kpiVillageQuotaDTO.setTownQuotaId(kPITownQuota.getId());
                        kpiVillageQuotaDTO.setParentQuotaId(kPITownQuota.getParentQuotaId());
                        KPIVillageQuota kpiVillageQuota = kpiVillageQuotaDTO.convert(KPIVillageQuota.class);
                        kpiVillageQuotaService.save(kpiVillageQuota);//保存三级
                    }
                }
            }
        }
        return Result.ofAddSuccess(true);
    }

    @Override
    @Transactional
    public Result<Boolean> initAll() {
        //删除除districtId为-1的所有数据，请手动删除。
        String delete1 = "delete from KPI_Town_Quota where districtId != '-1'";
        String delete2 = "delete from KPI_village_Quota where districtId != '-1'";

        //下面为初始化03,04,05的数据
        //1.获取所有镇
        String sql = "SELECT districtId,districtName FROM SYS_District WHERE districtType = 'Party' and districtLevel = '2' order by districtId desc";
        List<SysDistrict> sysDistricts = sysDistrictService.findAllBySql(SysDistrict.class,sql);

        //2.获取所有固定考核项
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
        detachedCriteria.add(Restrictions.eq("districtId","-1"));
        detachedCriteria.addOrder(Order.desc("createdAt"));
        List<KPITownQuota> kpiTownQuotas = kPITownQuotaService.findAll(detachedCriteria);
        for (SysDistrict v : sysDistricts){
            for(KPITownQuota kpiTownQuota : kpiTownQuotas){
                KPITownQuota k = new KPITownQuota();
                k.setDistrictName(v.getDistrictName());
                k.setDistrictId(v.getDistrictId());
                k.setQuotaName(kpiTownQuota.getQuotaName());
                k.setParentQuotaId(kpiTownQuota.getParentQuotaId());
                k.setParentQuotaName(kpiTownQuota.getParentQuotaName());
                k.setScore(kpiTownQuota.getScore());
                KPITownQuota kPITownQuota = kPITownQuotaService.save(k);//保存二级

                //3.获取镇下所有村
                String sql1 = "SELECT districtId,districtName FROM SYS_District WHERE attachTo ="+v.getDistrictId()+" order by districtId desc";
                List<SysDistrict> sysDistricts1 = sysDistrictService.findAllBySql(SysDistrict.class,sql1);
                for (SysDistrict v2 : sysDistricts1){//默认插入的权重为1
                    KPIVillageQuota kpiVillageQuota = new KPIVillageQuota();
                    kpiVillageQuota.setWeight("1");
                    kpiVillageQuota.setDistrictId(v2.getDistrictId());
                    kpiVillageQuota.setDistrictName(v2.getDistrictName());
                    kpiVillageQuota.setParentDistrictId(kPITownQuota.getDistrictId());
                    kpiVillageQuota.setTownQuotaId(kPITownQuota.getId());
                    kpiVillageQuota.setParentQuotaId(kPITownQuota.getParentQuotaId());
                    kpiVillageQuotaService.save(kpiVillageQuota);//保存三级
                }
            }
        }
        return Result.ofAddSuccess(true);
    }
}
