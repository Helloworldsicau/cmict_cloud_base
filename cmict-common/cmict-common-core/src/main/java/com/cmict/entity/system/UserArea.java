package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
@TableName("t_user_area")
@Data
public class UserArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("USER_ID")
    private Long userId;

    @TableField("AREA_ID")
    private String areaId;
}
