package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.Permission;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface PermissionMapper extends BaseMapper<Permission, Integer>, ManagementMapper<Permission, Integer> {
    Permission selectById(Integer permissionId);

    List<Permission> selectByMap(Map<String, Object> map);

    List<Permission> selectByMap(Map<String, Object> map, RowBounds rb);

    Integer countByMap(Map<String, Object> map);

    Integer insertSelective(Permission obj);

    Integer deleteById(Integer permissionId);

    Integer deleteByIds(List<Integer> ids);

    Integer deleteByMap(Map<String, Object> map);

    Integer updateSelectiveById(Permission obj);

    Integer updateSelectiveByMap(@Param("record") Permission obj, @Param("map") Map<String, Object> param);
}