package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ExaExamineVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 考核审核
 * @author lxw
 */
@ApiModel(value = "考核审核", description = "考核审核")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.EXA_Examine")
public class ExaExamine extends BaseEntity {

    /** 作者ID */
    @ApiModelProperty(value = "作者ID", position = 10, required = true)
    @Column(length = 60)
    private String authorId;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    @Column(length = 60)
    private Integer score;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    @Column(length = 60)
    private String remark;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime createTime;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 28)
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "organizationId", insertable = false, updatable = false)
    private SysDistrict sysDistrict;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ExaExamineVO exaExamineVO = (ExaExamineVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            exaExamineVO.setOrganizationName(this.sysDistrict.getDistrictName());
        }

        return (T) exaExamineVO;
    }

}
