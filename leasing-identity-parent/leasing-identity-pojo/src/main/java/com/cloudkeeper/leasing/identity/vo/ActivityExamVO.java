package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel(value = "活动考核审核", description = "活动考核审核")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityExamVO {

    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String town;

    /** 镇分数 */
    @ApiModelProperty(value = "镇分数", position = 10, required = true)
    private Integer townExam;

    /** 镇比例 */
    @ApiModelProperty(value = "镇比例", position = 10, required = true)
    private Double townScore;

    @ApiModelProperty(value = "cun", position = 10, required = true)
    private List<ActivityExamVO> children;

    public void setTown(String town) {
        this.town = town;
    }

    public void setTownExam(Integer townExam) {
        this.townExam = townExam;
    }

    public void setTownScore(Double townScore) {
        this.townScore = townScore;
    }

    public void setChildren(List<ActivityExamVO> children) {
        this.children = children;
    }
}
