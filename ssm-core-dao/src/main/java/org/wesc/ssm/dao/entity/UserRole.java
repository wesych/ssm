package org.wesc.ssm.dao.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * userId
     */
    private Integer userId;

    /**
     * roleId
     */
    private Integer roleId;

    private static final long serialVersionUID = 1L;

    /**
     * {@link #id}
     */
    public Integer getId() {
        return id;
    }

    /**
     * {@link #id}
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * {@link #userId}
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * {@link #userId}
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * {@link #roleId}
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * {@link #roleId}
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserRole other = (UserRole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        return result;
    }
}