package com.lvhaifeng.cloud.auth.server.modules.client.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 授权客户端
 * @Author: haifeng.lv
 * @Date:   2019-12-05
 * @Version: V1.0
 */
@Data
@TableName("auth_client")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="auth_client对象", description="授权客户端")
public class AuthClient {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**客户端 id*/
    @ApiModelProperty(value = "客户端 id")
	private String code;
	/**客户端 密钥*/
    @ApiModelProperty(value = "客户端 密钥")
	private String secret;
	/**客户端 名称*/
    @ApiModelProperty(value = "客户端 名称")
	private String name;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private String description;
	/**创建用户名称*/
    @ApiModelProperty(value = "创建用户名称")
	private String crtUserName;
	/**创建主机*/
    @ApiModelProperty(value = "创建主机")
	private String crtHost;
	/**更新主机*/
    @ApiModelProperty(value = "更新主机")
	private String updHost;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
	private Date crtTime;
	/**最后更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后更新时间")
	private Date updTime;
}
