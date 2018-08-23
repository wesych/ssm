package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.Role;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface RoleMapper extends BaseMapper<Role, Integer>, ManagementMapper<Role, Integer> {
    Role selectById(Integer roleId);

    List<Role> selectByMap(Map<String, Object> map);

    List<Role> selectByMap(Map<String, Object> map, RowBounds rb);

    Integer countByMap(Map<String, Object> map);

    Integer insertSelective(Role obj);

    Integer deleteById(Integer roleId);

    Integer deleteByIds(List<Integer> ids);

    Integer deleteByMap(Map<String, Object> map);

    Integer updateSelectiveById(Role obj);

    Integer updateSelectiveByMap(@Param("record") Role obj, @Param("map") Map<String, Object> param);
}