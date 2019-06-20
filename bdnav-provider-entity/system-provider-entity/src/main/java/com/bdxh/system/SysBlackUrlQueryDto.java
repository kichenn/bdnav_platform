package com.bdxh.system;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/6/20 12:13
 */
@Data
public class SysBlackUrlQueryDto extends Query {

    /**
     * 网站名称
     */
    @ApiModelProperty("网站名称")
    private String name;

    /**
     * 网站域名或者ip
     */
    @ApiModelProperty("网站域名或者ip")
    private String ip;

    /**
     * 网站来源 1 北斗 2 金山 3 360 4 百度
     */
    @ApiModelProperty("网站来源 1 北斗 2 金山 3 360 4 百度")
    private Byte origin;


}
