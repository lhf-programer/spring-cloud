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
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Data
@TableName("role_resource")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="role_resource对象", description="角色资源")
public class RoleResource {
    
	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String crtUser;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private String description;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**资源id*/
    @ApiModelProperty(value = "资源id")
	private String resourceId;
	/**角色id*/
    @ApiModelProperty(value = "角色id")
	private String roleId;
	/**资源类型 0 菜单 1 按钮*/
    @ApiModelProperty(value = "资源类型 0 菜单 1 按钮")
	private Integer type;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private String updUser;
}
