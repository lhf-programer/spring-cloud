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
 * @Description: 授权客户端
 * @Author: haifeng.lv
 * @Date: 2019-12-16 16:30
 */
@Data
@TableName("auth_client")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="auth_client对象", description="授权客户端")
public class AuthClient {

	/**客户端 id*/
    @ApiModelProperty(value = "客户端 id")
	private java.lang.String code;
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
	/**客户端 名称*/
    @ApiModelProperty(value = "客户端 名称")
	private java.lang.String name;
	/**客户端 密钥*/
    @ApiModelProperty(value = "客户端 密钥")
	private java.lang.String secret;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private java.lang.String updUser;
}
