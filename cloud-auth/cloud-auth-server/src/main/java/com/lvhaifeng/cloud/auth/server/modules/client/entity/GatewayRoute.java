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
 * @Description: 路由
 * @Author: haifeng.lv
 * @Date:   2019-12-05
 */
@Data
@TableName("gateway_route")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="gateway_route对象", description="路由")
public class GatewayRoute {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**映射路劲*/
    @ApiModelProperty(value = "映射路劲")
	private String path;
	/**映射服务*/
    @ApiModelProperty(value = "映射服务")
	private String serviceId;
	/**映射外连接*/
    @ApiModelProperty(value = "映射外连接")
	private String url;
	/**是否重试*/
    @ApiModelProperty(value = "是否重试")
	private Boolean retryable;
	/**是否启用*/
    @ApiModelProperty(value = "是否启用")
	private Boolean enabled;
	/**是否忽略前缀*/
    @ApiModelProperty(value = "是否忽略前缀")
	private Boolean stripPrefix;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String crtUserName;
	/**创建人ID*/
    @ApiModelProperty(value = "创建人ID")
	private String crtUserId;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
	private Date crtTime;
	/**最后更新人*/
    @ApiModelProperty(value = "最后更新人")
	private String updUserName;
	/**最后更新人ID*/
    @ApiModelProperty(value = "最后更新人ID")
	private String updUserId;
	/**最后更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后更新时间")
	private Date updTime;
}
