package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "当月活动VO", description = "当月活动 VO")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrentMonthActivityVO {

    /** 活动 */
    @ApiModelProperty(value = "活动id", position = 10, required = true)
    private String activityId;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    private String title;

    /** 标题 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String context;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private  Integer score;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 下蜀 */
    @ApiModelProperty(value = "下蜀", position = 10, required = true)
    private Double xiaShuPercent;

    /** 宝华 */
    @ApiModelProperty(value = "宝华", position = 10, required = true)
    private Double baoHuaPercent;

    /** 茅山 */
    @ApiModelProperty(value = "茅山", position = 10, required = true)
    private Double maoShanPercent;

    /** 后白 */
    @ApiModelProperty(value = "后白", position = 10, required = true)
    private Double houBaiPercent;

    /** 白兔 */
    @ApiModelProperty(value = "白兔", position = 10, required = true)
    private Double baiTuPercent;

    /** 茅山风景区 */
    @ApiModelProperty(value = "茅山风景区", position = 10, required = true)
    private Double maoShanFengJingPercent;

    /** 边城 */
    @ApiModelProperty(value = "边城", position = 10, required = true)
    private Double bianChengPercent;

    /** 郭庄 */
    @ApiModelProperty(value = "郭庄", position = 10, required = true)
    private Double guoZhuangPercent;

    /** 华阳 */
    @ApiModelProperty(value = "华阳", position = 10, required = true)
    private Double huaYangPercent;

    /** 开发区 */
    @ApiModelProperty(value = "开发区", position = 10, required = true)
    private Double kaiFaPercent;

    /** 天王 */
    @ApiModelProperty(value = "天王", position = 10, required = true)
    private Double tianWangPercent;

    /** 村执行状态 */
    @ApiModelProperty(value = "村执行状态", position = 10, required = true)
    private List<CurrentObjectVo> cunList = new ArrayList<>();

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getXiaShuPercent() {
        return xiaShuPercent;
    }

    public void setXiaShuPercent(Double xiaShuPercent) {
        this.xiaShuPercent = xiaShuPercent;
    }

    public Double getBaoHuaPercent() {
        return baoHuaPercent;
    }

    public void setBaoHuaPercent(Double baoHuaPercent) {
        this.baoHuaPercent = baoHuaPercent;
    }

    public Double getMaoShanPercent() {
        return maoShanPercent;
    }

    public void setMaoShanPercent(Double maoShanPercent) {
        this.maoShanPercent = maoShanPercent;
    }

    public Double getHouBaiPercent() {
        return houBaiPercent;
    }

    public void setHouBaiPercent(Double houBaiPercent) {
        this.houBaiPercent = houBaiPercent;
    }

    public Double getBaiTuPercent() {
        return baiTuPercent;
    }

    public void setBaiTuPercent(Double baiTuPercent) {
        this.baiTuPercent = baiTuPercent;
    }

    public Double getMaoShanFengJingPercent() {
        return maoShanFengJingPercent;
    }

    public void setMaoShanFengJingPercent(Double maoShanFengJingPercent) {
        this.maoShanFengJingPercent = maoShanFengJingPercent;
    }

    public Double getBianChengPercent() {
        return bianChengPercent;
    }

    public void setBianChengPercent(Double bianChengPercent) {
        this.bianChengPercent = bianChengPercent;
    }

    public Double getGuoZhuangPercent() {
        return guoZhuangPercent;
    }

    public void setGuoZhuangPercent(Double guoZhuangPercent) {
        this.guoZhuangPercent = guoZhuangPercent;
    }

    public Double getHuaYangPercent() {
        return huaYangPercent;
    }

    public void setHuaYangPercent(Double huaYangPercent) {
        this.huaYangPercent = huaYangPercent;
    }

    public Double getKaiFaPercent() {
        return kaiFaPercent;
    }

    public void setKaiFaPercent(Double kaiFaPercent) {
        this.kaiFaPercent = kaiFaPercent;
    }

    public Double getTianWangPercent() {
        return tianWangPercent;
    }

    public void setTianWangPercent(Double tianWangPercent) {
        this.tianWangPercent = tianWangPercent;
    }

    public List<CurrentObjectVo> getCunList() {
        return cunList;
    }

    public void setCunList(List<CurrentObjectVo> cunList) {
        this.cunList = cunList;
    }
}
