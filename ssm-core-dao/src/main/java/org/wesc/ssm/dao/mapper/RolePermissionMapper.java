package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.RolePermission;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface RolePermissionMapper extends org.wesc.ssm.dao.generator.base.BaseMapper<RolePermission, Integer>, org.wesc.ssm.dao.generator.base.ManagementMapper<RolePermission, Integer> {
    RolePermission selectById(Integer id);

    java.util.List<RolePermission> selectByMap(java.util.Map<String, Object> map);

    java.util.List<RolePermission> selectByMap(java.util.Map<String, Object> map, RowBounds rb);

    Integer countByMap(java.util.Map<String, Object> map);

    Integer insertSelective(RolePermission obj);

    Integer deleteById(Integer id);

    Integer deleteByIds(java.util.List<Integer> ids);

    Integer deleteByMap(java.util.Map<String, Object> map);

    Integer updateSelectiveById(RolePermission obj);

    Integer updateSelectiveByMap(@Param("record") RolePermission obj, @Param("map") java.util.Map<String, Object> param);
}