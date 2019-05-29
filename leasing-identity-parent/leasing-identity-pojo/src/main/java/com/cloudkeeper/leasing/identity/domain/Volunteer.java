package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.cglib.core.Local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 志愿者
 * @author asher
 */
@ApiModel(value = "志愿者", description = "志愿者")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Team_Volunteer")
public class Volunteer extends BaseEntity {

    /** 姓名 */
    @ApiModelProperty(value = "党员表id", position = 10, required = true)
    private String partyMemberId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10)
    private Integer sex;

    /** 出生年月 */
    @ApiModelProperty(value = "出生年月", position = 10)
    private LocalDate birthday;

    /** 入党时间 */
    @ApiModelProperty(value = "入党时间", position = 10)
    private LocalDate joinDate;

    /** 服务目录 */
    @ApiModelProperty(value = "服务目录", position = 10)
    private String category;

    /** 志愿者承诺 */
    @ApiModelProperty(value = "志愿者承诺", position = 10)
    private String promise;

    /** 其他服务目录 */
    @ApiModelProperty(value = "其他服务目录", position = 10)
    private String otherCategory;
}