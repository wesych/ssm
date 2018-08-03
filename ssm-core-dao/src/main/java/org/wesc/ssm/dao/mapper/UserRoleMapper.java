package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.UserRole;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface UserRoleMapper extends org.wesc.ssm.dao.generator.base.BaseMapper<UserRole, Integer>, org.wesc.ssm.dao.generator.base.ManagementMapper<UserRole, Integer> {
    UserRole selectById(Integer id);

    java.util.List<UserRole> selectByMap(java.util.Map<String, Object> map);

    java.util.List<UserRole> selectByMap(java.util.Map<String, Object> map, RowBounds rb);

    Integer countByMap(java.util.Map<String, Object> map);

    Integer insertSelective(UserRole obj);

    Integer deleteById(Integer id);

    Integer deleteByIds(java.util.List<Integer> ids);

    Integer deleteByMap(java.util.Map<String, Object> map);

    Integer updateSelectiveById(UserRole obj);

    Integer updateSelectiveByMap(@Param("record") UserRole obj, @Param("map") java.util.Map<String, Object> param);
}