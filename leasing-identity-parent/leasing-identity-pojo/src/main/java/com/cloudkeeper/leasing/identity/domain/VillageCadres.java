package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 村干部管理
 * @author cqh
 */
@ApiModel(value = "村干部管理", description = "村干部管理")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "village_cadres")
public class VillageCadres extends BaseEntity {

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10, required = true)
    @Column(length = 60)
    private String sex;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10, required = true)
    @Column(length = 60)
    private String nation;

    /** 健康状况 */
    @ApiModelProperty(value = "健康状况", position = 10, required = true)
    @Column(length = 60)
    private String health;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    @Column(length = 60)
    private LocalDate birth;

    /** 入党时间 */
    @ApiModelProperty(value = "姓入党时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate partyTime;

    /** 工作时间 */
    @ApiModelProperty(value = "工作时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate workTime;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 10, required = true)
    @Column(length = 60)
    private String nativePlace;

    /** 出生地 */
    @ApiModelProperty(value = "出生地", position = 10, required = true)
    @Column(length = 60)
    private String birthPlace;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    @Column(length = 60)
    private String education;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", position = 10, required = true)
    @Column(length = 60)
    private String identityCard;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10, required = true)
    @Column(length = 60)
    private String contact;

    /** 任职经历 */
    @ApiModelProperty(value = "任职经历", position = 10, required = true)
    @Column(length = 60)
    private String postExperience;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "districtId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;

    /** 岗位 */
    @ApiModelProperty(value = "岗位", position = 13)
    @OneToOne(mappedBy = "villageCadres")
    @NotFound(action = NotFoundAction.IGNORE)
    private CadrePosition cadrePosition;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        VillageCadresVO villageCadresVO = (VillageCadresVO) convert;
        if(!StringUtils.isEmpty(this.cadrePosition)){
            villageCadresVO.setPost(this.cadrePosition.getPost());
        }
        if(!StringUtils.isEmpty(this.sysDistrict)){
            villageCadresVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        return (T) villageCadresVO;
    }
}
