package com.cmict.server.system.dto;

import com.cmict.annotation.group.usergroup.BatchGroup;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.QueryGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.entity.QueryRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.cmict.entity.RegexpConstant.*;

/**
 * @Author: lichenxin
 * @Date: 2021/5/25
 * 角色DTO
 */
@Data
public class RoleDTO {

    @NotNull(groups = {UpdateGroup.class},message = "{required}")
    private Long roleId;

    @NotBlank(message = "{required}",groups = {BatchGroup.class})
    private String roleIds;

    @NotBlank(message = "{required}",groups = {InsertGroup.class})
    @Size(max = 10, message = "{noMoreThan}")
    @Pattern(regexp =ROLE_NAME_REG ,message = "{role.name.regexp}",groups ={InsertGroup.class, QueryGroup.class})
    private String roleName;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}",groups = {InsertGroup.class})
    @Pattern(regexp =ROLE_REMARK_REG ,message = "{role.remark.regexp}",groups = {InsertGroup.class,UpdateGroup.class})
    private String remark;

    @NotBlank(message = "{required}",groups = {InsertGroup.class})
    private transient String menuIds;

    @Valid
    private QueryRequest queryRequest;
}
