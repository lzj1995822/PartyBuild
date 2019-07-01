package com.cloudkeeper.leasing.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TVIndexVO {

    private List<ParActivityVO> currentMonth = new ArrayList<>();


    private List<ParActivityVO> nextMonth = new ArrayList<>();

}
