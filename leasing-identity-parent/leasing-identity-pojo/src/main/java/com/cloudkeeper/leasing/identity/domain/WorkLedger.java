package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.WorkLedgerVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 工作台账
 * @author cqh
 */
@ApiModel(value = "工作台账", description = "工作台账")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Work_Ledger")
public class WorkLedger extends BaseEntity {

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    @Column(length = 60)
    private String title;

    /** 工作时间 */
    @ApiModelProperty(value = "工作时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime workTime;

    /** 台账类型 */
    @ApiModelProperty(value = "台账类型", position = 10, required = true)
    @Column(length = 60)
    private String ledgerType;

    /** 工作地址 */
    @ApiModelProperty(value = "工作地址", position = 10, required = true)
    @Column(length = 60)
    private String workAddress;

    /** 具体内容 */
    @ApiModelProperty(value = "具体内容", position = 10, required = true)
    @Column(length = 60)
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10, required = true)
    @Column(length = 60)
    private String enclosure;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 22, required = true)
    @Column(length = 36)
    private String districtId;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "districtId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;
    @Nonnull
    @Override

    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        WorkLedgerVO workLedgerVO = (WorkLedgerVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            workLedgerVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        return (T) workLedgerVO;
    }

}