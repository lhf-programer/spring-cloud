package com.lvhaifeng.cloud.auth.server.modules.client.entity;

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
 * @Description: 认证客户端服务
 * @Author: haifeng.lv
 * @Date:   2019-12-05
 * @Version: V1.0
 */
@Data
@TableName("auth_client_service")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="auth_client_service对象", description="认证客户端服务")
public class AuthClientService {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**服务 id*/
    @ApiModelProperty(value = "服务 id")
	private String serviceId;
	/**客户机 id*/
    @ApiModelProperty(value = "客户机 id")
	private String clientId;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private String description;
	/**创建主机*/
    @ApiModelProperty(value = "创建主机")
	private String crtHost;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
	private Date crtTime;
}
