package com.cmict.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmict.entity.system.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 查询用户区域
     *
     * @param userId
     * @return
     */
    List<Area> selectListByUserId(@Param("userId") String userId);
}
