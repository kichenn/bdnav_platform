package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**收费部门和设备的展示对象
 * @author WanMing
 * @date 2019/7/16 10:15
 */
@Data
public class ChargeDeptAndDeviceVo {

    /*******************收费部门的属性*************************/
    /**
     * 收费部门主键
     */
    @ApiModelProperty("收费部门主键")
    private Long deptId;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    private String chargeDeptName;

    /**
     * 收费部门类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类
     */
    @ApiModelProperty("收费部门类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类")
    private Byte chargeDeptType;


    /*******************POS机属性*************************/

    /**
     * 设备主键
     */
    @ApiModelProperty("设备主键")
    private Long id;

    /**
     * 设备编码
     */
    @ApiModelProperty("设备编码")
    private String deviceId;

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String deviceName;





}
