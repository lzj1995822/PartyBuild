package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.AcceptInformationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 接收公告
 * @author cqh
 */
@ApiModel(value = "接收公告", description = "接收公告")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INF_Push")
public class AcceptInformation extends BaseEntity {

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    @Column(length = 60)
    private String title;

    /** 发布人 */
    @ApiModelProperty(value = "发布人", position = 10, required = true)
    @Column(length = 60)
    private String authorId;

    /** 接收组织 */
    @ApiModelProperty(value = "接收组织", position = 10, required = true)
    @Column(length = 60)
    private String objs;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime creatTime;

    /** 是否接收 */
    @ApiModelProperty(value = "是否接收", position = 10, required = true)
    @Column(length = 60)
    private String status;

    /** 公告id */
    @ApiModelProperty(value = "公告id", position = 10, required = true)
    @Column(length = 60)
    private String informationId;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "authorId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;

    /** 公告 */
    @ApiModelProperty(value = "公告", position = 24)
    @ManyToOne
    @JoinColumn(name = "informationId", insertable = false, updatable = false)
    private Information information;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        AcceptInformationVO acceptInformationVO = (AcceptInformationVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            acceptInformationVO.setName(this.sysDistrict.getDistrictName());
        }
        if(!StringUtils.isEmpty((this.information))){
            acceptInformationVO.setDescription(this.information.getDescription());
        }
        return (T) acceptInformationVO;
    }




}