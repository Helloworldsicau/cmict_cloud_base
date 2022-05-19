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
 * @since 2021-03-22
 */
@TableName("t_event_user")
@Data
public class EventUser implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 事件编号
     */
      @TableField("CASE_NUMBER")
    private String caseNumber;

      /**
     * 用户ID
     */
      @TableField("USER_ID")
    private Long userId;

      /**
     * 0 未查看  1查看
     */
      @TableField("LOOK")
    private Boolean look;

      /**
     * 0 未关注  1 已关注
     */
      @TableField("ATTENTION")
    private Boolean attention;
}
