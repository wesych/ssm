package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.RolePermission;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface RolePermissionMapper extends BaseMapper<RolePermission, Integer>, ManagementMapper<RolePermission, Integer> {
    RolePermission selectById(Integer id);

    List<RolePermission> selectByMap(Map<String, Object> map);

    List<RolePermission> selectByMap(Map<String, Object> map, RowBounds rb);

    Integer countByMap(Map<String, Object> map);

    Integer insertSelective(RolePermission obj);

    Integer deleteById(Integer id);

    Integer deleteByIds(List<Integer> ids);

    Integer deleteByMap(Map<String, Object> map);

    Integer updateSelectiveById(RolePermission obj);

    Integer updateSelectiveByMap(@Param("record") RolePermission obj, @Param("map") Map<String, Object> param);
}