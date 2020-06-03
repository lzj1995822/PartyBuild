package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 村主任任期信息 service
 * @author yujian
 */
public interface VillageCadresTermService extends BaseService<VillageCadresTerm> {

    void deleteAllByCadresId(String cadresId);

    VillageCadresTerm findByCadresId(String cadresId);

    VillageCadresTerm findByCadresIdAndTermType(String cadresId, String termType);

}
