package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 村主任信息附件
 * @author yujian
 */
@ApiModel(value = "村主任信息附件", description = "村主任信息附件")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.village_cadres_annex")
public class VillageCadresAnnex extends BaseEntity {

    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;
    @ApiModelProperty(value = "文件路径", position = 10, required = true)
    @Column(length = 60)
    private String filePath;
    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;
}
