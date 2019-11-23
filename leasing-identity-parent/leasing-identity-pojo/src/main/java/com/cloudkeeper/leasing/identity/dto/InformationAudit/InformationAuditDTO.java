package com.cloudkeeper.leasing.identity.dto.InformationAudit;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 村书记审核实体类
 * @author zdw
 */
@ApiModel(value = "村书记审核", description = "村书记审核")
@Getter
@Setter
public class InformationAuditDTO  extends BaseEditDTO  {

    /**通过意见**/
    private String passAdvice;

    /**是否通过**/
    private Integer isPass;

    /**村书记id**/
    private String villageId;

    /**审核人**/
    private  String auditor;

    /**审核意见**/
    private  String auditAdvice;

}
