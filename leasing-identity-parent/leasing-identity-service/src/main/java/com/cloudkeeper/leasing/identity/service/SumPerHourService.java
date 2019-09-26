package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 每小时人流量 service
 * @author asher
 */
public interface SumPerHourService extends BaseService<SumPerHour> {

    void calPeopleStream();

}