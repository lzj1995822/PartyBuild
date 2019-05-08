package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.SysLoginNoteVO;
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

/**
 * 系统登录日志
 * @author cqh
 */
@ApiModel(value = "系统登录日志", description = "系统登录日志")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.SYS_LoginNote")
public class SysLoginNote extends BaseEntity {

    /** 登录时间 */
    @ApiModelProperty(value = "登录时间", position = 10, required = true)
    @Column(length = 60)
    private String createTime;

    /** 登录名 */
    @ApiModelProperty(value = "登录名", position = 10, required = true)
    @Column(length = 60)
    private String userName;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 10, required = true)
    @Column(length = 60)
    private String userId;

    /** 用户*/
    @ApiModelProperty(value = "用户", position = 24)
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private SysUser user;

    /** 操作 */
    @ApiModelProperty(value = "操作", position = 10, required = true)
    @Column(length = 60)
    private String action;

    /** 地区 */
    @ApiModelProperty(value = "地区", position = 10, required = true)
    @Column(length = 60)
    private String region;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        SysLoginNoteVO sysLoginNoteVO = (SysLoginNoteVO) convert;
        if(!StringUtils.isEmpty(this.user)){
            sysLoginNoteVO.setName(this.user.getName());
        }
        return (T) sysLoginNoteVO;
    }

}