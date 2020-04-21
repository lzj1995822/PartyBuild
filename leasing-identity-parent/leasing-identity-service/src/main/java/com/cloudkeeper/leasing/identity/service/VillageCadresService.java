package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.vo.CadresExamineVO;
import com.cloudkeeper.leasing.identity.vo.CadresGroupByLevelVO;
import com.cloudkeeper.leasing.identity.vo.SecretaryNumberVO;

import java.util.List;

/**
 * 村干部管理 service
 * @author cqh
 */
public interface VillageCadresService extends BaseService<VillageCadres> {

    Long countAllByDistrictId(String districtId);

    /**
     * 初始化岗位
     */
    void initPost();

    /**
     * 重写村干部新增方法
     * @param villageCadresDTO
     * @return
     */
    VillageCadres save(VillageCadresDTO villageCadresDTO);

    VillageCadres saveBaseInfo(VillageCadresDTO villageCadresDTO);

    Boolean submit(VillageCadres villageCadres);

    Boolean  virify(String id, String code, InformationAuditDTO informationAuditDTO2);

    //根据等级统计村书记的数量
    SecretaryNumberVO countNumber();

    //获取带审核数据
    List<CadresExamineVO> getExamines(String cadresType, String districtId1);

    void updateIsEdit(String cadresId);

    RatingStandard generatePostLevel(VillageCadres villageCadres);

    List<VillageCadres> findAllByParentDistrictId(String objectId);

    void initCadres();


    List<CadresGroupByLevelVO> getCadresGroupByLevel(String districtId);

    Boolean secretarySubmit(VillageCadres villageCadres);

    public Boolean secretaryReview(String id, String code, InformationAuditDTO informationAuditDTO);
}
