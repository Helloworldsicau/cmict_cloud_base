package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_area")
@Data
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 片区ID
     */
        @TableId("ID")
      private String id;

      /**
     * 片区名称
     */
      @TableField("AREA_NAME")
    private String areaName;
}
