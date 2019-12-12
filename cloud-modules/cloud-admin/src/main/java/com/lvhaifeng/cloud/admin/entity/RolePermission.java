package com.lvhaifeng.cloud.admin.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 角色权限
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 */
@Data
@TableName("role_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="role_permission对象", description="角色权限")
public class RolePermission {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**permissionId*/
    @ApiModelProperty(value = "permissionId")
	private String permissionId;
	/**roleId*/
    @ApiModelProperty(value = "roleId")
	private String roleId;
}
