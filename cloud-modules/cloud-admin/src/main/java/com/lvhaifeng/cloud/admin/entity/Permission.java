package com.lvhaifeng.cloud.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 权限
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 * @Version: V1.0
 */
@Data
@TableName("permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="permission对象", description="权限")
public class Permission {
    
	/**description*/
    @ApiModelProperty(value = "description")
	private String description;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**name*/
    @ApiModelProperty(value = "name")
	private String name;
	/**url*/
    @ApiModelProperty(value = "url")
	private String url;
}
