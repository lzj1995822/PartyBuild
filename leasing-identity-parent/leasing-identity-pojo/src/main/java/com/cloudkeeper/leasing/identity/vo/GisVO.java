package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 地图 VO
 * @author cqh
 */
@ApiModel(value = "地图 VO", description = "地图 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GisVO extends BaseVO {

    private String positionName;

    private String positionType;

    private String positionDistrictId;

    private String positionDistrictName;

    private String positionIntroduction;

    private String positionLonLat;

    private Long positionCadresNumber;

    private String positionCadreName;

}
