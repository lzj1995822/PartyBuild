package com.cloudkeeper.leasing.identity.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoListVO implements Serializable {
    /** 长度 */
    @ApiModelProperty(value = "长度", position = 10, required = true)
    private String length;

    /** 名字 */
    @ApiModelProperty(value = "名字", position = 10, required = true)
    private String name;

    /** 视频地址 */
    @ApiModelProperty(value = "视频地址", position = 10, required = true)
    private String playUrl;

    /** 封面 */
    @ApiModelProperty(value = "封面", position = 10, required = true)
    private String posterUrl;

    private String actors;
    private String  assetId;
    private String category;
    private String chaper;
    private String directors;
    private String genre;
    private String  introduction;
    private String region;
    private String year;


}
