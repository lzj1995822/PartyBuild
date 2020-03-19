package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VillageCadresController;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import com.cloudkeeper.leasing.identity.service.VillageCadresTermService;
import com.cloudkeeper.leasing.identity.vo.CadresExamineVO;
import com.cloudkeeper.leasing.identity.vo.SecretaryNumberVO;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
    /** 村干部管理 service */
    private final VillageCadresTermService villageCadresTermService;
    private final SysLogService sysLogService;

    private final CadrePositionService cadrePositionService;

    @Override
    public Result<VillageCadresVO> findOne(@ApiParam(value = "村干部管理id", required = true) @PathVariable String id) {
        Optional<VillageCadres> villageCadresOptional = villageCadresService.findOptionalById(id);
        return villageCadresOptional.map(villageCadres -> Result.of(villageCadres.convert(VillageCadresVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VillageCadresVO> departure(@ApiParam(value = "村干部管理id", required = true)@PathVariable String id) {
        VillageCadres villageCadres = villageCadresService.findById(id);
        String msg;
        if (villageCadres == null){
            return Result.ofNotFound();
        }
        villageCadres.setHasRetire("1");
        villageCadresService.save(villageCadres);
        msg = villageCadresService.actionLog("移除","[村干部信息]", villageCadres.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,villageCadresService.getTableName(),villageCadres.getId());
        VillageCadresTerm villageCadresTerm = villageCadresTermService.findByCadresId(id);
        if (villageCadresTerm != null){
            villageCadresTerm.setDepartureTime(LocalDate.now());
            villageCadresTermService.deleteAllByCadresId(id);
            villageCadresTermService.save(villageCadresTerm);
        }
        CadrePosition cadrePosition = cadrePositionService.findByDistrictIdAndPost(villageCadres.getDistrictId(), "SECRETARY");
        cadrePosition.setCadreId("");
        cadrePositionService.save(cadrePosition);
        return Result.ofUpdateSuccess(villageCadres.convert(VillageCadresVO.class));
    }

    @Override
    public Result<List<CadresExamineVO>> getExamines() {

        return Result.of(villageCadresService.getExamines());
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

        //添加村干部任期信息----开始
        VillageCadresTerm villageCadresTerm = new VillageCadresTerm();
        villageCadresTerm.setCadresId(villageCadres.getId());
        villageCadresTerm.setCadresName(villageCadres.getName());
        villageCadresTerm.setAppointmentTime(LocalDate.now());
        villageCadresTerm.setDistrictId(villageCadres.getDistrictId());
        villageCadresTerm.setDistrictName(villageCadres.getDistrictName());
        villageCadresTerm.setCadresType(villageCadres.getCadresType());
        villageCadresTermService.save(villageCadresTerm);
        //添加村干部任期信息----结束

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
        VillageCadres villageCadres = villageCadresService.save(villageCadresDTO);
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
        System.out.println(new Date().getTime());
        List<VillageCadres> villageCadresList = villageCadresService.findAll(villageCadresSearchable, sort);
        List<VillageCadresVO> villageCadresVOList = VillageCadres.convert(villageCadresList, VillageCadresVO.class);
        System.out.println(new Date().getTime());
        return Result.of(villageCadresVOList);
    }

    @Override
    public Result<Page<VillageCadresVO>> page(@ApiParam(value = "村干部管理查询条件", required = true) @RequestBody VillageCadresSearchable villageCadresSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        DetachedCriteria detachedCriteria = setParam(villageCadresSearchable);
        Integer total = villageCadresService.getTotalCount(detachedCriteria);
        pageable.getSort().forEach(item -> {
            if (item.isAscending()) {
                detachedCriteria.addOrder(Order.asc(item.getProperty()));
            } else {
                detachedCriteria.addOrder(Order.desc(item.getProperty()));
            }
        });
        Page<VillageCadres> villageCadresPage = villageCadresService.findAll(detachedCriteria, pageable, total);
        Page<VillageCadresVO> villageCadresVOPage = VillageCadres.pageConvert(villageCadresPage, VillageCadresVO.class);
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

    @Override
    public Result<SecretaryNumberVO> countNumber() {
        SecretaryNumberVO secretaryNumberVO = villageCadresService.countNumber();
        return Result.of(secretaryNumberVO);
    }

    @Override
    public Result<SecretaryNumberVO> countNumber(HttpServletRequest request, HttpServletResponse response, @RequestBody VillageCadresSearchable villageCadresSearchable) {
        DetachedCriteria detachedCriteria = setParam(villageCadresSearchable);
        List<VillageCadres> list = villageCadresService.findAll(detachedCriteria);
        List<VillageCadresVO> villageCadresVOS = VillageCadres.pageConvert(list, VillageCadresVO.class);
        return null;
    }

    @Override
    public Result<Boolean> init() {
        villageCadresService.initCadres();
        return Result.of(true);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    private Date getCurrYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    private Date getCurrYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
    private DetachedCriteria setParam(VillageCadresSearchable villageCadresSearchable){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VillageCadres.class);
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        if (!StringUtils.isEmpty(villageCadresSearchable.getPost())) {
            detachedCriteria.createAlias("cadrePosition","a");
            detachedCriteria.add(Restrictions.eq("a.post", villageCadresSearchable.getPost()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getName())){
            //通过姓名模糊查询
            detachedCriteria.add(Restrictions.ilike("name", villageCadresSearchable.getName(), MatchMode.ANYWHERE));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getCadresType())){
            //通过姓名模糊查询
            detachedCriteria.add(Restrictions.eq("cadresType", villageCadresSearchable.getCadresType()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getDistrictId())){
            //通过组织模糊查询
            detachedCriteria.add(Restrictions.ilike("districtId", villageCadresSearchable.getDistrictId(), MatchMode.START));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getQuasiAssessmentRank())){
            //通过员额村书记等级
            detachedCriteria.add(Restrictions.ilike("quasiAssessmentRank", villageCadresSearchable.getQuasiAssessmentRank(), MatchMode.ANYWHERE));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getEducation())){
            //通过学历
            detachedCriteria.add(Restrictions.eq("education",villageCadresSearchable.getEducation()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getSex())){
            //通过性别
            detachedCriteria.add(Restrictions.eq("sex",villageCadresSearchable.getSex()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getPersonnelType())){
            //通过人员类型
            detachedCriteria.add(Restrictions.eq("personnelType",villageCadresSearchable.getPersonnelType()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getHasRetire())){
            //通过是否离退休
            detachedCriteria.add(Restrictions.eq("hasRetire",villageCadresSearchable.getHasRetire()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getAge())){
            //通过年龄
            int start = 0;
            int end = 0;
            if (villageCadresSearchable.getAge().equals("-1")){
                start = year - 200;
                end = year - 50;
            }else if (villageCadresSearchable.getAge().equals("35")){
                start = year - 35;
                end = year;
            }else if (villageCadresSearchable.getAge().equals("50")){
                start = year - 50;
                end = year - 35;
            }
            Date s = getCurrYearFirst(start);
            Date e = getCurrYearLast(end);
            LocalDate ls = s.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate le = e.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            detachedCriteria.add(Restrictions.between("birth",ls,le));
        }
        if (!StringUtils.isEmpty(villageCadresSearchable.getOnDutyTime())){
            //任职年限
            int end = Integer.valueOf(villageCadresSearchable.getOnDutyTime());
            int start = end - 5;
            if (end == -1){
                end = 100;
                start = 20;
            }
            String send = String.valueOf(end);
            String sstart = String.valueOf(start);
            detachedCriteria.add(Restrictions.between("onDutyTime",sstart,send));
        }
        if (!StringUtils.isEmpty(villageCadresSearchable.getRank())){
            //职级等次
            detachedCriteria.add(Restrictions.eq("rank",villageCadresSearchable.getRank()));
        }
        if(!StringUtils.isEmpty(villageCadresSearchable.getEnterPartyTime())){
            //通过党龄
            int start = 0;
            int end = 0;
            if (villageCadresSearchable.getEnterPartyTime().equals("-1")){
                start = year - 200;
                end = year - 20;
            }else{
                start = year - Integer.valueOf(villageCadresSearchable.getEnterPartyTime());
                end = year - Integer.valueOf(villageCadresSearchable.getEnterPartyTime()) + 4;
            }
            Date s = getCurrYearFirst(start);
            Date e = getCurrYearLast(end);
            LocalDate ls = s.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate le = e.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            detachedCriteria.add(Restrictions.between("partyTime",ls,le));
        }
        return detachedCriteria;
    }
}
