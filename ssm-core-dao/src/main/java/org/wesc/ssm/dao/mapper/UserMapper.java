package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface UserMapper extends org.wesc.ssm.dao.generator.base.BaseMapper<User, Integer>, org.wesc.ssm.dao.generator.base.ManagementMapper<User, Integer> {
    User selectById(Integer userId);

    java.util.List<User> selectByMap(java.util.Map<String, Object> map);

    java.util.List<User> selectByMap(java.util.Map<String, Object> map, RowBounds rb);

    Integer countByMap(java.util.Map<String, Object> map);

    Integer insertSelective(User obj);

    Integer deleteById(Integer userId);

    Integer deleteByIds(java.util.List<Integer> ids);

    Integer deleteByMap(java.util.Map<String, Object> map);

    Integer updateSelectiveById(User obj);

    Integer updateSelectiveByMap(@Param("record") User obj, @Param("map") java.util.Map<String, Object> param);
}