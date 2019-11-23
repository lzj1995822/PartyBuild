package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Boolean submit(VillageCadres villageCadres);

    Boolean  virify(@PathVariable("id") String id, @PathVariable("code") String code, @RequestBody InformationAuditDTO informationAuditDTO2);
}