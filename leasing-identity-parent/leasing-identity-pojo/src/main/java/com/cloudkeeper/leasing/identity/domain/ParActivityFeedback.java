package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ParActivityFeedbackVO;
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 移动端执行记录
 * @author lxw
 */
@ApiModel(value = "移动端执行记录", description = "移动端执行记录")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_ActivityFeedback")
public class ParActivityFeedback extends BaseEntity {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    @Column(length = 60)
    private String snId;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 10, required = true)
    @Column(length = 60)
    private String userId;


    /** 描述 */
    @ApiModelProperty(value = "描述", position = 10, required = true)
    @Column(length = 60)
    private String context;

    /** 上传时间 */
    @ApiModelProperty(value = "上传时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime time;

    /** 标记 */
    @ApiModelProperty(value = "标记", position = 10, required = true)
    @Column(length = 60)
    private String flag;

    /** 视频 */
    @ApiModelProperty(value = "附件", position = 10, required = true)
    @JsonIgnore
    @OneToOne(mappedBy = "parActivityFeedback")
    private ParActivityPicture parActivityPicture;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ParActivityFeedbackVO parActivityFeedbackVO = (ParActivityFeedbackVO) convert;
        if(!StringUtils.isEmpty(this.parActivityPicture)){
            parActivityFeedbackVO.setImageUrl(this.parActivityPicture.getImageUrl());
        }

        return (T) parActivityFeedbackVO;
    }


}
