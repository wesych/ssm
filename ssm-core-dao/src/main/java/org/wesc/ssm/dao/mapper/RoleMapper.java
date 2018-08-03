package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.Role;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface RoleMapper extends org.wesc.ssm.dao.generator.base.BaseMapper<Role, Integer>, org.wesc.ssm.dao.generator.base.ManagementMapper<Role, Integer> {
    Role selectById(Integer roleId);

    java.util.List<Role> selectByMap(java.util.Map<String, Object> map);

    java.util.List<Role> selectByMap(java.util.Map<String, Object> map, RowBounds rb);

    Integer countByMap(java.util.Map<String, Object> map);

    Integer insertSelective(Role obj);

    Integer deleteById(Integer roleId);

    Integer deleteByIds(java.util.List<Integer> ids);

    Integer deleteByMap(java.util.Map<String, Object> map);

    Integer updateSelectiveById(Role obj);

    Integer updateSelectiveByMap(@Param("record") Role obj, @Param("map") java.util.Map<String, Object> param);
}