package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.HonourInfoVO;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * 表彰情况
 * @author asher
 */
@ApiModel(value = "表彰情况", description = "表彰情况")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Honour_Info")
public class HonourInfo extends BaseEntity {

    /** 干部id */
    @ApiModelProperty(value = "干部id", position = 5)
    private String cadresId;

    /** 干部id */
    @ApiModelProperty(value = "干部id", position = 5)
    @OneToOne
    @JoinColumn(name = "cadresId", insertable = false, updatable = false)
    private VillageCadres villageCadres;

    /** 获取时间 */
    @ApiModelProperty(value = "获取时间", position = 1)
    private LocalDate achieveTime;

    /** 表彰内容 */
    @ApiModelProperty(value = "表彰内容", position = 10)
    private String content;

    /** 表彰描述（字段备用） */
    @ApiModelProperty(value = "表彰描述（字段备用）", position = 15)
    private String des;

    /** 佐证材料 */
    @ApiModelProperty(value = "佐证材料", position = 20)
    private String supportDoc;

    /** 是否可以编辑 */
    @ApiModelProperty(value = "是否可以编辑", position = 25)
    private String isEdit;

    /** 表彰类型 */
    @ApiModelProperty(value = "表彰类型", position = 25)
    private String honourType;

    /** 2020-03-12新增字段 **/
    /** 奖或惩 */
    @ApiModelProperty(value = "奖或惩", position = 25)
    private String rewardsType;

    /** 表彰种类 */
    @ApiModelProperty(value = "表彰种类", position = 25)
    private String honourDescription;

    @Nonnull
    @Override
    public <T> T pageConvert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        HonourInfoVO honourInfoVO = (HonourInfoVO) convert;
        if (!StringUtils.isEmpty(this.villageCadres)){
            honourInfoVO.setCadresName(this.villageCadres.getName());
        }
        return (T) honourInfoVO;
    }

}
