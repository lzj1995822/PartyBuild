package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 接收公告 VO
 * @author cqh
 */
@ApiModel(value = "接收公告 VO", description = "接收公告 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CentralConsoleVo extends BaseVO {

    //村干部数量
    private Long villageCadresNumber;

    //村书记数量
    private Integer villageSecretaryNumber;

    //活动执行次数
    private Integer activityPerformNumber;

    //活动完成率
    private BigDecimal activityCompleteRate;

    //下属阵地数量
    private Integer positionNumber;

    //所属组织数量
    private Integer organizationNumber;

    //党员数量
    private Integer parMemberNumber;





}
