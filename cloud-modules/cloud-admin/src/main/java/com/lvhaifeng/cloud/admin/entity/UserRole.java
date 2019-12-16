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
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2019-12-16 16:46
 */
@Data
@TableName("user_role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="user_role对象", description="用户角色")
public class UserRole {

	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private java.lang.String crtUser;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**角色id*/
    @ApiModelProperty(value = "角色id")
	private java.lang.String roleId;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private java.lang.String updUser;
	/**用户id*/
    @ApiModelProperty(value = "用户id")
	private java.lang.String userId;
}
