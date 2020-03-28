package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 村书记拟晋升名单
 * @author asher
 */
@ApiModel(value = "村书记拟晋升名单", description = "村书记拟晋升名单")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Promotion_Cadres")
public class PromotionCadres extends BaseEntity {

    //    书记姓名
    private String cadresName;

    //    职位名称
    private String postName;

    //    目标职级
    private String purposeLevelName;

    //    村id
    private String villageId;

    //    书记id
    private String cadresId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private VillageCadres villageCadres;

    //    镇id
    private String townId;

    private String taskId;

    // 是否破格
    private String isBreakRule;

    // 理由
    private String reason;


    // 晋升状态
    private String status;

}