package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 村主任信息附件 service
 * @author yujian
 */
public interface VillageCadresAnnexService extends BaseService<VillageCadresAnnex> {

    VillageCadresAnnex save(VillageCadresAnnex villageCadresAnnex);
}
