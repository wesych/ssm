package org.wesc.ssm.dao.querymapper;

import org.springframework.stereotype.Repository;
import org.wesc.ssm.dao.entity.Permission;
import org.wesc.ssm.dao.entity.Role;

import java.util.List;

/**
 * @author Wesley Cheung
 * @Date Created in 17:48 2017/12/22
 */
@Repository
public interface QueryUserRolesAndPermissionsMapper {

    /**
     * Find all user roles.
     * @param userId
     * @return
     */
    List<Role> findAllRolesByUserId(int userId);

    /**
     * Find all user permissions.
     * @param userId
     * @return
     */
    List<Permission> findAllPermissionsByUserId(int userId);
}
