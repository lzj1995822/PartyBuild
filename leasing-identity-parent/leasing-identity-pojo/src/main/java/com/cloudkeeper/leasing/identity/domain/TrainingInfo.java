package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 专职村书记培训情况
 * @author yujian
 */
@ApiModel(value = "专职村书记培训情况", description = "专职村书记培训情况")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.training_info")
public class TrainingInfo extends BaseEntity {

    /** 培训开始时间 */
    @ApiModelProperty(value = "培训开始时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate timeBegin;

    @ApiModelProperty(value = "培训结束时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate timeEnd;
    @ApiModelProperty(value = "培训班名称", position = 10, required = true)
    @Column(length = 60)
    private String trainingName;

    @ApiModelProperty(value = "培训类型", position = 10, required = true)
    @Column(length = 60)
    private String trainingType;

    @ApiModelProperty(value = "培训内容", position = 10, required = true)
    @Column(length = 60)
    private String content;

    @ApiModelProperty(value = "举证材料", position = 10, required = true)
    @Column(length = 60)
    private String supportDoc;

    @ApiModelProperty(value = "培训成果", position = 10, required = true)
    @Column(length = 60)
    private String trainingResults;

    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;

    @ApiModelProperty(value = "年度", position = 10, required = true)
    @Column(length = 60)
    private String trainingYear;

    @ApiModelProperty(value = "培训年月", position = 10, required = true)
    @Column(length = 60)
    private String trainingDuration;

    @ApiModelProperty(value = "培训天数", position = 10, required = true)
    @Column(length = 60)
    private String trainingDays;

    @ApiModelProperty(value = "等级", position = 10, required = true)
    @Column(length = 60)
    private String trainingGrade;
}
