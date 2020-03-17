package com.cloudkeeper.leasing.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishRatioVO {

    private Integer unfinish;

    private Integer finish;

    private Integer total;
}
