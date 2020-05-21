package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPITownQuotaController;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaSearchable;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaSearchable;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaDTO;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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

    /** 组织 service */
    private final DetectionIndexService detectionIndexService;


    private final CadreTaskService cadreTaskService;

    private final KPIEvaluationService kpiEvaluationService;

    private final KPIAttachmentService kpiAttachmentService;


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


    @GetMapping("/commonWork")
    public Result<List<KpiQuotaVO>> getCommonWorkByQuarter(@Nonnull String taskYear, @Nonnull String quarter, @Nonnull String districtId, @Nonnull String taskId) {
        String quotaId = taskYear + "01";
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
        detachedCriteria.add(Restrictions.like("parentQuotaId", quotaId, MatchMode.START));
        detachedCriteria.add(Restrictions.eq("quotaYear", taskYear));
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        detachedCriteria.addOrder(Order.asc("parentQuotaId"));
        List<KPITownQuota> all = kPITownQuotaService.findAll(detachedCriteria);
        Map<String, List<KPITownQuota>> map = new HashMap<>();

        for (KPITownQuota item : all) {
            if (!map.containsKey(item.getParentQuotaId())) {
                map.put(item.getParentQuotaId(), new ArrayList<>());
            }
            List<KPITownQuota> kpiTownQuotas = map.get(item.getParentQuotaId());
            item.setKpiVillageQuotas(kpiVillageQuotaService.findAllByTownQuotaIdAndQuarter(item.getId(), quarter));
            kpiTownQuotas.add(item);
        }

        // 一级指标实体
        KpiQuota first = kpiQuotaService.findByQuotaId(quotaId);

        // 一级指标VO
        KpiQuotaVO kpiQuotaVO = first.convert(KpiQuotaVO.class);
        KPIAttachment byQuota = kpiAttachmentService.findByQuota(kpiQuotaVO.getQuotaId(), districtId, quarter, taskId);
        if (byQuota != null) {
            kpiQuotaVO.setAttachment(byQuota.convert(KPIAttachmentVO.class));
        }

        // 二级指标实体
        List<KpiQuota> seconds = first.getKpiQuotas();

        if (seconds == null) {
            return Result.of(500, "二级指标内容不全！");
        }
        List<KpiQuotaVO> secondVOS = new ArrayList<>();

        for (KpiQuota item : seconds) {
            KpiQuotaVO convert = item.convert(KpiQuotaVO.class);
            List<KPITownQuota> kpiTownQuotas = map.get(item.getQuotaId());
            if (!CollectionUtils.isEmpty(kpiTownQuotas)) {
                convert.setKpiTownQuotas(KPITownQuota.convert(kpiTownQuotas, KPITownQuotaVO.class));
            }
            secondVOS.add(convert);
        }
        ArrayList<KpiQuotaVO> res = new ArrayList<>();
        kpiQuotaVO.setKpiQuotas(secondVOS);
        res.add(kpiQuotaVO);
        return Result.of(res);
    }

    @Override
    public Result<Object> getAll(@RequestBody KPITownQuotaDTO kpi, String isDepart, String townDistrictId) {
        String d = kpi.getDistrictId();

        KpiQuotaSearchable kpiQuotaSearchable = new KpiQuotaSearchable();
        kpiQuotaSearchable.setQuotaMakeDepartId(d);
        kpiQuotaSearchable.setParentQuotaId(kpi.getParentQuotaId());
        List<KpiQuota> kpiQuotas = kpiQuotaService.findAll(kpiQuotaSearchable,new Sort(Sort.Direction.DESC,"parentQuotaId"));
        List<KpiQuotaVO> quotaVOList = KpiQuota.convert(kpiQuotas,KpiQuotaVO.class);
        for (KpiQuotaVO k : quotaVOList){
            // 查找相关佐证材料
            KPIAttachment byQuota = kpiAttachmentService.findByQuota(k.getQuotaId(), d, kpi.getQuarter(), kpi.getTaskId());
            if (byQuota != null) {
                k.setAttachment(byQuota.convert(KPIAttachmentVO.class));
            }

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
            detachedCriteria.add(Restrictions.eq("parentQuotaId", k.getQuotaId()));
            if ("1".equals(isDepart)) {
                d = "0101";
                if (!StringUtils.isEmpty(townDistrictId)) {
                    d = townDistrictId;
                }
            }
            detachedCriteria.add(Restrictions.eq("districtId",d));
            detachedCriteria.addOrder(Order.desc("createdAt"));
            List<KPITownQuota> kpiTownQuotas = kPITownQuotaService.findAll(detachedCriteria);
            List<KPITownQuotaVO> kpiTownQuotaVOS = new ArrayList<>();
            if (StringUtils.isNotBlank(kpi.getQuarter())){
                for (KPITownQuota item : kpiTownQuotas) {
                    item.setKpiVillageQuotas(kpiVillageQuotaService.findAllByTownQuotaIdAndQuarter(item.getId(), kpi.getQuarter()));
                    KPITownQuotaVO convert = item.convert(KPITownQuotaVO.class);
                    kpiTownQuotaVOS.add(convert);
                }
            }
            kpiTownQuotaVOS = KPITownQuota.convert(kpiTownQuotas, KPITownQuotaVO.class);
            k.setKpiTownQuotas(kpiTownQuotaVOS);
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
                        kpiTownQuota.setQuarter(quarter);
                        KPITownQuota kPITownQuota = kPITownQuotaService.save(kpiTownQuota);//保存二级

                        for (KPIVillageQuotaDTO kpiVillageQuotaDTO : kpiTownQuotaDTO.getKpiVillageQuotas()){
                            kpiVillageQuotaDTO.setParentDistrictId(kPITownQuota.getDistrictId());
                            kpiVillageQuotaDTO.setTownQuotaId(kPITownQuota.getId());
                            kpiVillageQuotaDTO.setParentQuotaId(kPITownQuota.getParentQuotaId());
                            kpiVillageQuotaDTO.setQuarter(quarter);
                            kpiVillageQuotaDTO.setWeight("1");//日常考核权重默认为1
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
        List<DistrictsVo> sysDistricts = sysDistrictService.findAllBySql(DistrictsVo.class,sql);

        //2.获取所有固定考核项
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
        detachedCriteria.add(Restrictions.eq("districtId","-1"));
        detachedCriteria.addOrder(Order.desc("createdAt"));
        List<KPITownQuota> kpiTownQuotas = kPITownQuotaService.findAll(detachedCriteria);
        for (DistrictsVo v : sysDistricts){
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
                List<DistrictsVo> sysDistricts1 = sysDistrictService.findAllBySql(DistrictsVo.class,sql1);
                for (DistrictsVo v2 : sysDistricts1){//默认插入的权重为1
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

    @Override
    public Result<Object> getAllByVillageId(@PathVariable String districtId, @PathVariable String parentQuotaId) {
        String sql = "SELECT v.id , quotaName,v.score FROM KPI_village_Quota v  JOIN KPI_Town_Quota t ON v.townQuotaId = t.id and v.districtId = '"+districtId+"' AND v.parentQuotaId like '"+parentQuotaId+"%'";
        List<VillageQoutaVO> kpiTownQuotaVOS = sysDistrictService.findAllBySql(VillageQoutaVO.class,sql);
        return Result.of(kpiTownQuotaVOS);
    }

    @Override
    public Result<Object> updateStatisticsQoutaScore(@RequestBody List<VillageQoutaVO> kpiVillageQuotaDTOS) {
        for(VillageQoutaVO kpiVillageQuotaDTO : kpiVillageQuotaDTOS){
            KPIVillageQuota kpiVillageQuota = kpiVillageQuotaService.findById(kpiVillageQuotaDTO.getId());
            kpiVillageQuota.setScore(kpiVillageQuotaDTO.getScore());
            kpiVillageQuota.setScoreEnd(kpiVillageQuotaDTO.getScoreEnd());
            kpiVillageQuotaService.save(kpiVillageQuota);
        }
        return Result.ofUpdateSuccess(kpiVillageQuotaDTOS);
    }

    @Override
    public Result<Object> getTownQoutaTree(@RequestBody KPITownQuotaSearchable kPITownQuotaSearchable) {
        if (!StringUtils.isNotBlank(kPITownQuotaSearchable.getQuotaYear())){
            //年份为空获取最近一年的数据
            String sql = "SELECT top 1 * FROM KPI_Quota ORDER BY CAST(quotaYear as INT) DESC";
            KpiQuotaVO kpiQuota = kpiQuotaService.findBySql(KpiQuotaVO.class,sql);
            kPITownQuotaSearchable.setQuotaYear(kpiQuota.getQuotaYear());
        }

//        //获取三级，districtId = -1
//        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
//        detachedCriteria.add(Restrictions.eq("districtId", "-1"));
//        detachedCriteria.add(Restrictions.eq("quotaYear",kPITownQuotaSearchable.getQuotaYear()));
//        List<KPITownQuota> list = kPITownQuotaService.findAll(detachedCriteria);
//        List<KPITownQuotaVO> listVOs = KPITownQuota.convert(list,KPITownQuotaVO.class);
//        Map<String,List<KPITownQuotaVO>> thirdMap = listVOs.stream().collect(Collectors.groupingBy(KPITownQuotaVO::getParentQuotaId));

        //获取二级
        DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(KpiQuota.class);
        detachedCriteria1.add(Restrictions.eq("quotaYear",kPITownQuotaSearchable.getQuotaYear()))
                .add(Restrictions.ne("parentQuotaId","0"))
                .addOrder(Order.asc("quotaId"));
        List<KpiQuota> seconds = kpiQuotaService.findAll(detachedCriteria1);
        List<KpiQuotaVO> secondVOS = KpiQuota.convert(seconds, KpiQuotaVO.class);

        //组装二级，三级
        Map<String,List<KpiQuotaVO>> secondMap = secondVOS.stream().collect(Collectors.toMap(
                key -> key.getParentQuotaId(),
                val -> {
                    List<KpiQuotaVO> valList = new ArrayList();
//                    if (thirdMap.containsKey(val.getQuotaId())){
//                        val.setKpiTownQuotas(thirdMap.get(val.getQuotaId()));
//                    }
                    valList.add(val);
                    return valList;
                },
                (List<KpiQuotaVO> newValueList, List<KpiQuotaVO> oldValueList) -> {
                    oldValueList.addAll(newValueList);
                    return oldValueList;
                }));


        //获取一级
        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(KpiQuota.class);
        detachedCriteria2.add(Restrictions.eq("quotaYear",kPITownQuotaSearchable.getQuotaYear()))
                .add(Restrictions.eq("parentQuotaId","0"))
                .addOrder(Order.asc("quotaId"));
        List<KpiQuota> frists = kpiQuotaService.findAll(detachedCriteria2);
        List<KpiQuotaVO> fristVOS = KpiQuota.convert(frists, KpiQuotaVO.class);

        //组装一级，二级
        for (KpiQuotaVO k : fristVOS){
            if (secondMap.containsKey( k.getQuotaId())){
                k.setKpiQuotas(secondMap.get( k.getQuotaId()));
            }
        }
        return Result.of(fristVOS);
    }

    @PostMapping("/townAndVillageQuota")
    @Transactional
    public Result<KPITownQuotaVO> allOneTownQuotaAndVillageQuota(@RequestBody KPITownQuotaDTO kpiTownQuotaDTO) {

        KpiQuota kpiQuota = kpiQuotaService.findByQuotaId(kpiTownQuotaDTO.getParentQuotaId());
        if (kpiQuota == null) {
            return Result.of(500, "二级指标不能为空");
        }
        String quotaMakeDepartId = kpiQuota.getQuotaMakeDepartId();
        String[] split = quotaMakeDepartId.split(",");
        if (split.length == 1) {
            // 处理单部门制定多组织指标问题
            handleMutipartQuota(kpiTownQuotaDTO);
            return Result.of(kpiTownQuotaDTO.convert(KPITownQuotaVO.class));
        }

        List<KPIVillageQuotaDTO> kpiVillageQuotas = kpiTownQuotaDTO.getKpiVillageQuotas();
        KPITownQuota convert = kpiTownQuotaDTO.convert(KPITownQuota.class);
        convert.setKpiVillageQuotas(null);
        convert = kPITownQuotaService.save(convert);
        kpiVillageQuotaService.deleteAllByTownQuotaId(convert.getId());
        String[] quarters = {"第一季度", "第二季度", "第三季度", "第四季度"};
        List<String> quarterList = Arrays.asList(quarters);

        for (KPIVillageQuotaDTO item : kpiVillageQuotas) {
            item.setId(null);
            item.setTownQuotaId(convert.getId());
            if (kpiQuota.getOnceOrMore().equals("一年多次")) {
                for (String subItem : quarterList) {
                    KPIVillageQuota k = item.convert(KPIVillageQuota.class);
                    k.setQuarter(subItem);
                    kpiVillageQuotaService.save(k);
                }
            } else {
                kpiVillageQuotaService.save(item.convert(KPIVillageQuota.class));
            }
        }
        return Result.of(convert.convert(KPITownQuotaVO.class));
    }

    @DeleteMapping("/townAndVillageQuota/{id}")
    @Transactional
    public Result<KPITownQuotaVO> allOneTownQuotaAndVillageQuota(@PathVariable String id) {
        kpiVillageQuotaService.deleteAllByTownQuotaId(id);
        kPITownQuotaService.deleteById(id);
        return Result.of(200, "删除成功！");
    }

    @GetMapping("/getAllByDistrictId")
    public Result<List<KpiQuotaVO>> getAllByDistrictId(@Nonnull String districtId,@Nonnull String quotaYear) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        detachedCriteria.add(Restrictions.eq("quotaYear", quotaYear));
        List<KPITownQuota> all = kPITownQuotaService.findAll(detachedCriteria);

        Map<String, List<KPITownQuotaVO>> map = new HashMap<>();
        for (KPITownQuota item : all) {
            String parentQuotaId = item.getParentQuotaId();
            List<KPITownQuotaVO> currentList = null;
            if (!map.containsKey(parentQuotaId)) {
                map.put(parentQuotaId, new ArrayList<>());
            }
            currentList = map.get(parentQuotaId);
            currentList.add(item.convert(KPITownQuotaVO.class));
        }

        DetachedCriteria quota = DetachedCriteria.forClass(KpiQuota.class);
        quota.add(Restrictions.eq("quotaYear", quotaYear));
        quota.add(Restrictions.eq("quotaLevel", "1"));
        List<KpiQuota> first = kpiQuotaService.findAll(quota);

        List<KpiQuotaVO> firstRes = new ArrayList<>();
        for (KpiQuota item : first) {
            List<KpiQuota> second = item.getKpiQuotas();
            if (CollectionUtils.isEmpty(second)) {
                continue;
            }
            KpiQuotaVO firstConvert = item.convert(KpiQuotaVO.class);
            List<KpiQuotaVO> secondRes = new ArrayList<>();
            for (KpiQuota subItem : second) {
                KpiQuotaVO secondConvert = subItem.convert(KpiQuotaVO.class);
                secondConvert.setKpiTownQuotas(map.get(subItem.getQuotaId()));
                secondRes.add(secondConvert);
            }
            firstConvert.setKpiQuotas(secondRes);
            firstRes.add(firstConvert);
        }
        return Result.of(firstRes);
    }

    @GetMapping("/scoringBasic")
    @Transactional
    public Result<List<Map>> getScoringBasic(String townQuotaId, String taskId, String quarter) {
        KPITownQuota byId = kPITownQuotaService.findById(townQuotaId);
        if (byId == null) {
            return Result.of(500, "当前三级指标不存在！");
        }
        List<KPIVillageQuota> kpiVillageQuotas;
        if (!StringUtils.isEmpty(quarter)) {
            kpiVillageQuotas = kpiVillageQuotaService.findAllByTownQuotaIdAndQuarter(byId.getId(), quarter);
        } else {
            kpiVillageQuotas = byId.getKpiVillageQuotas();
        }
        List<String> collect = kpiVillageQuotas.stream().map(KPIVillageQuota::getDistrictId).collect(Collectors.toList());

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DetectionIndex.class);
        detachedCriteria.add(Restrictions.in("districtId", collect));
        detachedCriteria.add(Restrictions.eq("taskId", taskId));
        List<DetectionIndex> all = detectionIndexService.findAll(detachedCriteria);
        Map<String, DetectionIndex> detectionIndexMap = all.stream().collect(Collectors.toMap(DetectionIndex::getDistrictId, detectionIndex -> detectionIndex));
        List<Map> res = new LinkedList<>();
        for (KPIVillageQuota item : kpiVillageQuotas) {
            Map<String, Object> map = new HashMap<>();
            if (detectionIndexMap.get(item.getDistrictId()) != null) {
                map = transBean2Map(detectionIndexMap.get(item.getDistrictId()));
            }
            map.putAll(transBean2Map(item));
            List<KPIEvaluation> kpiEvaluations = kpiEvaluationService.findAllByVillageQuotaId(item.getId());
            map.put("kpiEvaluations", kpiEvaluations);
            map.remove("kpiTownQuota");
            res.add(map);
        }
        return Result.of(res);
    }

    public void handleMutipartQuota(KPITownQuotaDTO kpiTownQuotaDTO) {
        if (!StringUtils.isEmpty(kpiTownQuotaDTO.getId())) {
            KPITownQuota byId = kPITownQuotaService.findById(kpiTownQuotaDTO.getId());
            List<KPITownQuota> allByParentQuotaId = kPITownQuotaService.findAllByQuotaName(byId.getQuotaName());
            List<String> collect = allByParentQuotaId.stream().map(KPITownQuota::getId).collect(Collectors.toList());
            kpiVillageQuotaService.deleteAllByTownQuotaIdIn(collect);
            kPITownQuotaService.deleteAllByIdIn(collect);
        }
        List<SysDistrict> allTowns = sysDistrictService.findAllTowns();
        for (SysDistrict item : allTowns) {
            KPITownQuota kpiTownQuota = new KPITownQuota();
            BeanUtils.copyProperties(kpiTownQuotaDTO, kpiTownQuota, "id");
            kpiTownQuota.setDistrictId(item.getDistrictId());
            kpiTownQuota.setDistrictName(item.getDistrictName());
            KPITownQuota save = kPITownQuotaService.save(kpiTownQuota);
            List<SysDistrict> orgChildren = item.getOrgChildren();
            for (SysDistrict subItem : orgChildren) {
                KPIVillageQuota kpiVillageQuota = new KPIVillageQuota();
                kpiVillageQuota.setTownQuotaId(save.getId());
                kpiVillageQuota.setWeight("1");
                kpiVillageQuota.setDistrictId(subItem.getDistrictId());
                kpiVillageQuota.setDistrictName(subItem.getDistrictName());
                kpiVillageQuota.setParentDistrictId(item.getDistrictId());
                kpiVillageQuota.setParentQuotaId(save.getParentQuotaId());
                kpiVillageQuota.setScore("0");
                kpiVillageQuota.setScoreEnd("0");
                kpiVillageQuota.setFormulaScore("0");
                kpiVillageQuota.setQuarter(kpiTownQuotaDTO.getQuarter());
                kpiVillageQuotaService.save(kpiVillageQuota);
            }
        }
    }

    private static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }
}
