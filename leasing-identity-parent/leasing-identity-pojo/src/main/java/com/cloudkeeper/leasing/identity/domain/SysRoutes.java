package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 类属性配置
 * @author asher
 */
@ApiModel(value = "类属性配置", description = "类属性配置")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_routes")
public class SysRoutes extends BaseEntity implements Comparable{

    /** 父路由id */
    @ApiModelProperty(value = "父路由id", position = 1, required = true)
    @Column(length = 36)
    private String parentId;

    /** 类id */
    @ApiModelProperty(value = "类id", position = 3, required = true)
    @Column(length = 36)
    private String classId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysClass sysClass;

    /** 路由名称 */
    @ApiModelProperty(value = "路由名", position = 5, required = true)
    @Column(length = 36)
    private String name;

    /** 路由访问url地址 */
    @ApiModelProperty(value = "路由访问url地址", position = 7, required = true)
    @Column(length = 200)
    private String path;

    /** 组件名 */
    @ApiModelProperty(value = "组件名", position = 9, required = true)
    @Column(length = 50)
    private String componentName;

    /** 路由描述 */
    @ApiModelProperty(value = "路由描述", position = 11)
    @Column(length = 200)
    private String des;

    /** 路由等级 */
    @ApiModelProperty(value = "路由等级", position = 11)
    private Integer level;

    /** 是否可视 */
    @ApiModelProperty(value = "是否可视", position = 12)
    private Integer visible;

    /** 路由meta */
    @ApiModelProperty(value = "路由meta", position = 13)
    @OneToOne(mappedBy = "sysRoutes")
    @Fetch(FetchMode.JOIN)
    private SysRoutesMeta meta;

    /** 子路由*/
    @ApiModelProperty(value = "子路由", position = 13)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    @Fetch(FetchMode.JOIN)
    private List<SysRoutes> children;

    @Override
    public int compareTo(Object roleMenu) {
        LocalDateTime left=((SysRoutes)roleMenu).getCreatedAt();
        /* 正序排列 */
        return this.getCreatedAt().compareTo(left);
    }
}
