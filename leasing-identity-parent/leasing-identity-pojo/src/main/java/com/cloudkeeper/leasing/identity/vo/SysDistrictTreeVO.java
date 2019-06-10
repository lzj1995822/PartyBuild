package com.cloudkeeper.leasing.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 组织树返回vo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysDistrictTreeVO implements Serializable {

    /**
     * 实体类对应的DistrictId
     */
    private String id;

    private String label;

    private Boolean leaf = false;

    private Set<SysDistrictTreeVO> children = new HashSet<>();
}
