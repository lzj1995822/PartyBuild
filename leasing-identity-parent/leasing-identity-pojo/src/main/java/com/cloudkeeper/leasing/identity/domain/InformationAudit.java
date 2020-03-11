package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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


    @ManyToOne
    @JoinColumn(name = "villageId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private VillageCadres villageCadres;

    /**通过意见**/
    private String passAdvice;

    /**是否通过**/
    private String status;

    /**村书记id**/
    private String villageId;

    /**审核人**/
    private String auditor;

    /**审核意见**/
    private String auditAdvice;

    /**流程类型（村书记基本信息，考核，职级评定）**/
    private String processType;

    /**村书记任务Id**/
    private String taskId;
}
