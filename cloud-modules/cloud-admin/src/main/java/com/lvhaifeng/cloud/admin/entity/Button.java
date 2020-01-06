package com.lvhaifeng.cloud.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-04 16:11
 */
@Data
@TableName("button")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="button对象", description="按钮")
public class Button {

	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private java.lang.String crtUser;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private java.lang.String description;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**所属菜单id*/
    @ApiModelProperty(value = "所属菜单id")
	private java.lang.String menuId;
	/**按钮名称*/
    @ApiModelProperty(value = "按钮名称")
	private java.lang.String name;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private java.lang.String updUser;
	/**按钮路径*/
    @ApiModelProperty(value = "按钮路径")
	private java.lang.String url;
}
