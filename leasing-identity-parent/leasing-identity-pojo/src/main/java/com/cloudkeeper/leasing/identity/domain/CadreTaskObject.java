package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import com.cloudkeeper.leasing.identity.vo.SysDistrictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * 村书记模块发布任务对象记录
 * @author asher
 */
@ApiModel(value = "村书记模块发布任务对象记录", description = "村书记模块发布任务对象记录")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cadres_task_object")
public class CadreTaskObject extends BaseEntity {

    /** 任务 */
    @ApiModelProperty(value = "任务", position = 10, required = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId", insertable = false, updatable = false)
    private CadreTask cadreTask;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String taskId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 10, required = true)
    private String taskName;

    /** 任务对象Id */
    @ApiModelProperty(value = "任务对象Id", position = 10, required = true)
    private String objectId;

    /** 父級名 */
    @ApiModelProperty(value = "父級名", position = 10, required = true)
    private String townName;

    /** 任务对象名称 */
    @ApiModelProperty(value = "任务对象Id", position = 10, required = true)
    private String objectName;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 名称 */
    @ApiModelProperty(value = "对象类型", position = 10, required = true)
    private String objectType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    private String note;

    /** 当前对象进度 */
    @ApiModelProperty(value = "当前对象进度")
    private String currentPercent;

    /** 成果相关文件 */
    @ApiModelProperty(value = "成果相关文件")
    private String resultFiles;

    /** 个人申请 */
    @ApiModelProperty(value = "个人申请")
    private String applyFiles;

    /** 考察审查文件 */
    @ApiModelProperty(value = "考察审查文件")
    private String reviewFiles;

    /** 获奖证书 */
    @ApiModelProperty(value = "获奖证书")
    private String rewardFiles;

    /** 最新审核人 */
    @ApiModelProperty(value = "最新审核人")
    private String lastestAuditor;

    /** 最新审核意见 */
    @ApiModelProperty(value = "最新审核意见")
    private String lastestAdvice;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        CadreTaskObjectVO cadreTaskObjectVO = (CadreTaskObjectVO) convert;
        if (!StringUtils.isEmpty(cadreTask)) {
            cadreTaskObjectVO.setTaskType(cadreTask.getType());
            cadreTaskObjectVO.setEndTime(cadreTask.getEndTime());
        }
        return (T) cadreTaskObjectVO;
    }
}