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
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 */
@Data
@TableName("role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="role对象", description="角色")
public class Role {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**name*/
    @ApiModelProperty(value = "name")
	private String name;
}
