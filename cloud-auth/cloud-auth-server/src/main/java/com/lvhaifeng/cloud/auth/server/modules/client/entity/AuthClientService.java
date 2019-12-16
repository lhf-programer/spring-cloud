package com.lvhaifeng.cloud.auth.server.modules.client.entity;

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
 * @Description: 认证客户端服务
 * @Author: haifeng.lv
 * @Date: 2019-12-16 16:33
 */
@Data
@TableName("auth_client_service")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="auth_client_service对象", description="认证客户端服务")
public class AuthClientService {

	/**客户机 id*/
    @ApiModelProperty(value = "客户机 id")
	private java.lang.String clientId;
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
	/**服务 id*/
    @ApiModelProperty(value = "服务 id")
	private java.lang.String serviceId;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private java.lang.String updUser;
}
