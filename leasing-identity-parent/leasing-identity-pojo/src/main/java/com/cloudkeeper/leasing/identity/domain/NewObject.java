package com.cloudkeeper.leasing.identity.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 发布新对象
 * @author xw
 */
@ApiModel(value = "发布新对象", description = "发布新对象")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewObject {
    private boolean countryside;

    private boolean office;
}
