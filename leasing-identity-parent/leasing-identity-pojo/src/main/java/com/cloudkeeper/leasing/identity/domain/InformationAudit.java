package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 村书记审核实体类
 * @author zdw
 */
@ApiModel(value = "村书记审核", description = "村书记审核")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Information_Audit")
public class InformationAudit extends BaseEntity {


    /**通过意见**/
    private String passAdvice;

    /**是否通过**/
    private String status;

    /**村书记id**/
    private String villageId;

    /**审核人**/
    private  String auditor;

    /**审核意见**/
    private  String auditAdvice;
}
