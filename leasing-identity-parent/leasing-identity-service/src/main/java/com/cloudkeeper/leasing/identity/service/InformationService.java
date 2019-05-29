package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.Information;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.information.InformationDTO;
import com.cloudkeeper.leasing.identity.vo.InformationVO;

/**
 * 消息通知 service
 * @author cqh
 */
public interface InformationService extends BaseService<Information> {

    InformationVO save(InformationDTO informationDTO);

}