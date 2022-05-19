package com.cmict.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

import static com.cmict.entity.RegexpConstant.INTEGER_CHECK;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@ApiModel
@Data
public class QueryRequest implements Serializable {
    private static final long serialVersionUID = -4869594085374385813L;

    @ApiModelProperty("每页数量,最大100")
    @Max(value = 100,message = "{query.pageSize}")
    private Integer pageSize = 10000;
    @Min(value = 1,message = "{query.pageNum}")
    @ApiModelProperty("当前页码")
    private Integer pageNum = 1;

    @ApiModelProperty("排序字段")
    private String field;

    @ApiModelProperty("排序(升序：ascending, 降序：descending)")
    private String order;
}
