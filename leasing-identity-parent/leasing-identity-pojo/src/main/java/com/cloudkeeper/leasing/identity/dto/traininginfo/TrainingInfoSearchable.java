package com.cloudkeeper.leasing.identity.dto.traininginfo;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 专职村书记培训情况 查询DTO
 * @author yujian
 */
@ApiModel(value = "专职村书记培训情况 查询DTO", description = "专职村书记培训情况 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainingInfoSearchable extends BaseSearchable {

    /** 培训开始时间 */
    @ApiModelProperty(value = "培训开始时间", position = 10, required = true)
    private LocalDate timeBegin;

    /** 培训结束时间 */
    @ApiModelProperty(value = "培训结束时间", position = 10, required = true)
    private LocalDate timeEnd;

    /** 培训班名称 */
    @ApiModelProperty(value = "培训班名称", position = 10, required = true)
    private String trainingName;

    /** 培训类型 */
    @ApiModelProperty(value = "培训类型", position = 10, required = true)
    private String trainingType;

    /** 培训内容 */
    @ApiModelProperty(value = "培训内容", position = 10, required = true)
    private String content;

    /** 举证材料 */
    @ApiModelProperty(value = "举证材料", position = 10, required = true)
    private String supportDoc;

    /** 培训成果 */
    @ApiModelProperty(value = "培训成果", position = 10, required = true)
    private String trainingResults;

    /** 村主任ID */
    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    private String cadresId;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 10, required = true)
    private String trainingYear;

}