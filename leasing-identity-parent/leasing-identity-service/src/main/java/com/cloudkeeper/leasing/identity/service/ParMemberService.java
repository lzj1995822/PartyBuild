package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParMember;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.ParMemberChartsVo;

import java.util.List;

/**
 * 党员管理 service
 * @author cqh
 */
public interface ParMemberService extends BaseService<ParMember> {

    Integer countAll(String districtId);

    //通过性别统计
    List<ParMemberChartsVo> statisticsSex(String districtId);

    //通过年龄统计
    List<ParMemberChartsVo> statisticsAge(String districtId);

    //通过村支部统计
    List<ParMemberChartsVo> statisticsBranch(String districtId);



}