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
 * @Description: 路由
 * @Author: haifeng.lv
 * @Date: 2019-12-16 16:34
 */
@Data
@TableName("gateway_route")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="gateway_route对象", description="路由")
public class GatewayRoute {

	/**创建时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.time.LocalDateTime crtTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private java.lang.String crtUser;
	/**是否启用*/
    @ApiModelProperty(value = "是否启用")
	private java.lang.Integer enabled;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**映射路劲*/
    @ApiModelProperty(value = "映射路劲")
	private java.lang.String path;
	/**是否重试*/
    @ApiModelProperty(value = "是否重试")
	private java.lang.Integer retryable;
	/**映射服务*/
    @ApiModelProperty(value = "映射服务")
	private java.lang.String serviceId;
	/**是否忽略前缀*/
    @ApiModelProperty(value = "是否忽略前缀")
	private java.lang.Integer stripPrefix;
	/**最后更新时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
	private java.time.LocalDateTime updTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private java.lang.String updUser;
	/**映射外连接*/
    @ApiModelProperty(value = "映射外连接")
	private java.lang.String url;
}
