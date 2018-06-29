package org.wesc.ssm.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.Permission;
import org.wesc.ssm.dao.entity.PermissionExample;

public interface PermissionMapper {
    long countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExampleWithRowbounds(PermissionExample example, RowBounds rowBounds);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}