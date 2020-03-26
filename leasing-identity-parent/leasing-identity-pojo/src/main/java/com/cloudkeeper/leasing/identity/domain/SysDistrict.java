package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.SysDistrictVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
/**
 * 组织
 * @author lxw
 */
@ApiModel(value = "组织", description = "组织")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.SYS_District")
@Where(clause = "isDelete=0")
public class SysDistrict extends BaseEntity {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 组织名字 */
    @ApiModelProperty(value = "组织名字", position = 10, required = true)
    @Column(length = 60)
    private String districtName;

    /** 审核组织id */
    @ApiModelProperty(value = "attachTo", position = 10, required = true)
    @Column(length = 60)
    private String attachTo;

    /** 上级id */
    @ApiModelProperty(value = "orgParent", position = 10, required = true)
    @Column(length = 60)
    private String orgParent;


    /** 组织等级 */
    @ApiModelProperty(value = "组织等级", position = 10, required = true)
    @Column(length = 60)
    private Integer districtLevel;


    /** 下属组织 */
    @ApiModelProperty(value = "subDistrictNum", position = 10, required = true)
    @Column(length = 60)
    private String subDistrictNum;

    /** 描述 */
    @ApiModelProperty(value = "description", position = 10, required = true)
    @Column(length = 60)
    private String description;

    /** 分数 */
    @ApiModelProperty(value = "score", position = 10, required = true)
    @Column(length = 60)
    private Integer score;

    /** 是否可用 */
    @ApiModelProperty(value = "enable", position = 10, required = true)
    @Column(length = 60)
    private Integer enable;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String districtType;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    @Column(length = 60)
    private String location;

    @ApiModelProperty(value = "阵地信息", position = 10, required = true)
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "districtId", referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<PositionInformation> positionInformation;

    /**
     * 审核下属子组织
     */
    @ApiModelProperty(value = "审核下属子组织", position = 10, required = true)
    @OneToMany(mappedBy = "sysDistrict", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("districtId asc")
    @Fetch(value = FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.TRUE)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<SysDistrict> children;

    /**
     * 组织架构子组织
     */
    @ApiModelProperty(value = "组织架构子组织", position = 10, required = true)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("districtId asc")
    @Fetch(value = FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.TRUE)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<SysDistrict> orgChildren;


    /** 审核组织 */
    @ApiModelProperty(value = "审核组织", position = 24)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachTo",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private SysDistrict sysDistrict;

    /** 组织 */
    @ApiModelProperty(value = "上级组织", position = 24)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgParent",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private SysDistrict parent;

    @ApiModelProperty(value = "摄像头信息", position = 10, required = true)
    @OneToMany(mappedBy = "sysDistrict", fetch = FetchType.LAZY)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ParCamera> parCamera;

    @ApiModelProperty(value = "是否属于机关党支部", position = 10, required = true)
    private String isOfficeBranch;

    @ApiModelProperty(value = "是否属于离退休党支部", position = 10, required = true)
    private String isRetiredBranch;

    @ApiModelProperty(value = "任务对象编码", position = 10, required = true)
    private String objectTypeCode;

    private String parentName;

    private String orgParentName;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        SysDistrictVO sysDistrictVO = (SysDistrictVO) convert;
        if(!StringUtils.isEmpty(this.positionInformation)){
            sysDistrictVO.setPositionInformation(this.positionInformation);
        }
        if(!StringUtils.isEmpty(this.orgChildren) && this.orgChildren.size() > 0){
            List<SysDistrictVO> convert1 = SysDistrict.convert(this.orgChildren, SysDistrictVO.class);
            sysDistrictVO.setOrgChildren(new HashSet<>(convert1));
        } else {
            sysDistrictVO.setOrgChildren(null);
        }
        return (T) sysDistrictVO;
    }

    @Nonnull
    @Override
    public <T> T pageConvert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        SysDistrictVO sysDistrictVO = (SysDistrictVO) convert;
        return (T) sysDistrictVO;
    }


}
