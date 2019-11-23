package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    Boolean submit(VillageCadres villageCadres);

    Boolean  virify(String id, String code, InformationAuditDTO informationAuditDTO2);
}