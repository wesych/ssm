package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.Permission;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface PermissionMapper extends org.wesc.ssm.dao.generator.base.BaseMapper<Permission, Integer>, org.wesc.ssm.dao.generator.base.ManagementMapper<Permission, Integer> {
    Permission selectById(Integer permissionId);

    java.util.List<Permission> selectByMap(java.util.Map<String, Object> map);

    java.util.List<Permission> selectByMap(java.util.Map<String, Object> map, RowBounds rb);

    Integer countByMap(java.util.Map<String, Object> map);

    Integer insertSelective(Permission obj);

    Integer deleteById(Integer permissionId);

    Integer deleteByIds(java.util.List<Integer> ids);

    Integer deleteByMap(java.util.Map<String, Object> map);

    Integer updateSelectiveById(Permission obj);

    Integer updateSelectiveByMap(@Param("record") Permission obj, @Param("map") java.util.Map<String, Object> param);
}