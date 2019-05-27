package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.InformationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 消息通知
 * @author cqh
 */
@ApiModel(value = "消息通知", description = "消息通知")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INF_information")
public class Information extends BaseEntity {

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    @Column(length = 60)
    private String title;

    /** 标题 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    @Column(length = 60)
    private String description;

    /** 发布时间 */
    @ApiModelProperty(value = "发布时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime releaseTime;

    /** 发布对象*/
    @ApiModelProperty(value = "发布对象", position = 10, required = true)
    @Column(length = 60)
    private String districtID;

    /** 发布人  */
    @ApiModelProperty(value = "发布人", position = 24)
    @ManyToOne
    @JoinColumn(name = "createdBy", insertable = false, updatable = false)
    private SysUser sysUser;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        InformationVO informationVO = (InformationVO) convert;
        if(!StringUtils.isEmpty(this.sysUser)){
            informationVO.setName(this.sysUser.getName());
        }
        return (T) informationVO;
    }


}