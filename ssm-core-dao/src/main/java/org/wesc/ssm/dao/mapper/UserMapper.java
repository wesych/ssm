package org.wesc.ssm.dao.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.generator.base.BaseMapper;
import org.wesc.ssm.dao.generator.base.ManagementMapper;

public interface UserMapper extends BaseMapper<User, Integer>, ManagementMapper<User, Integer> {
    User selectById(Integer userId);

    List<User> selectByMap(Map<String, Object> map);

    List<User> selectByMap(Map<String, Object> map, RowBounds rb);

    Integer countByMap(Map<String, Object> map);

    Integer insertSelective(User obj);

    Integer deleteById(Integer userId);

    Integer deleteByIds(List<Integer> ids);

    Integer deleteByMap(Map<String, Object> map);

    Integer updateSelectiveById(User obj);

    Integer updateSelectiveByMap(@Param("record") User obj, @Param("map") Map<String, Object> param);
}