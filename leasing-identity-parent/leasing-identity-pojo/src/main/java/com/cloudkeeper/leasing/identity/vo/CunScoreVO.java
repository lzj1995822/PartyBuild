package com.cloudkeeper.leasing.identity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel(value = "云图年度审核村排名 VO", description = "云图年度审核村排名VO")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CunScoreVO {

    /** 村名 */
    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String cun;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer exam;

    public String getCun() {
        return cun;
    }

    public void setCun(String cun) {
        this.cun = cun;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }
}
