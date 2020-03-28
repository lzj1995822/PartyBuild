package com.cloudkeeper.leasing.identity.dto.cadretask;

import com.cloudkeeper.leasing.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "晋升名单DTO", description = "晋升名单DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCadresDTO extends BaseDTO {

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

//    镇id
    private String townId;

    // 是否破格
    private String isBreakRule;

    // 理由
    private String reason;

}
