package org.wesc.ssm.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable {
    /**
     * permissionId
     */
    private Integer permissionId;

    /**
     * permissionName
     */
    private String permissionName;

    /**
     * createTime
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * {@link #permissionId}
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * {@link #permissionId}
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * {@link #permissionName}
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * {@link #permissionName}
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * {@link #createTime}
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * {@link #createTime}
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permissionId=").append(permissionId);
        sb.append(", permissionName=").append(permissionName);
        sb.append(", createTime=").append(createTime);
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
        Permission other = (Permission) that;
        return (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals(other.getPermissionId()))
            && (this.getPermissionName() == null ? other.getPermissionName() == null : this.getPermissionName().equals(other.getPermissionName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPermissionId() == null) ? 0 : getPermissionId().hashCode());
        result = prime * result + ((getPermissionName() == null) ? 0 : getPermissionName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}