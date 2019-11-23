package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;

public interface InformationAuditService extends BaseService<InformationAudit> {

    /**
     * 根据 informationAuditId 判断是否通过
     * @param informationAuditId
     * @return 0不通过 1通过
     */
    Integer isPass(String informationAuditId);


    /**
     * 根据 informationAuditId 判
     * @param  passAdvice
     * @return 通过意见
     */

    String passAdvice(String passAdvice);


    /**
     * 根据 informationAuditId
     * @param villageId
     * @return 村书记Id
     */
    Integer villageId(Integer villageId);


}
