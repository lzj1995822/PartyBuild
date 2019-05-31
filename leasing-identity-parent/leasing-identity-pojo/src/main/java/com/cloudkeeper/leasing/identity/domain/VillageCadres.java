package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
    private LocalDateTime birth;

    /** 入党时间 */
    @ApiModelProperty(value = "姓入党时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime partyTime;

    /** 工作时间 */
    @ApiModelProperty(value = "工作时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime workTime;

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


}