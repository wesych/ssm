package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.UserRole;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface UserRoleMapper extends BaseMapper<UserRole, Integer>, ManagementMapper<UserRole, Integer> {
    UserRole selectById(Integer id);

    List<UserRole> selectByMap(Map<String, Object> map);

    List<UserRole> selectByMap(Map<String, Object> map, RowBounds rb);

    Integer countByMap(Map<String, Object> map);

    Integer insertSelective(UserRole obj);

    Integer deleteById(Integer id);

    Integer deleteByIds(List<Integer> ids);

    Integer deleteByMap(Map<String, Object> map);

    Integer updateSelectiveById(UserRole obj);

    Integer updateSelectiveByMap(@Param("record") UserRole obj, @Param("map") Map<String, Object> param);
}