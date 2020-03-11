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
 * 村书记模块任务
 * @author asher
 */
@ApiModel(value = "村书记模块任务", description = "村书记模块任务")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cadre_task")
public class CadreTask extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String name;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型")
    private String type;

    /** 得分 */
    @ApiModelProperty(value = "得分")
    private String score;

    /** 截止时间 */
    @ApiModelProperty(value = "截止时间")
    private LocalDate endTime;

    /** 内容 */
    @ApiModelProperty(value = "内容")
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件")
    private String attach;

}