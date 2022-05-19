package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
@TableName("t_dept")
@Data
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 部门ID
     */
        @TableId(value = "DEPT_ID", type = IdType.AUTO)
      private Long deptId;

      /**
     * 上级部门ID
     */
      @TableField("PARENT_ID")
    private Long parentId;

      /**
     * 部门名称
     */
      @TableField("DEPT_NAME")
    private String deptName;

      /**
     * 排序
     */
      @TableField("ORDER_NUM")
    private Double orderNum;

      /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
      private LocalDateTime createTime;

      /**
     * 修改时间
     */
      @TableField("MODIFY_TIME")
    private LocalDateTime modifyTime;
}
