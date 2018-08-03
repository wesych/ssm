package org.wesc.ssm.dao.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.wesc.ssm.dao.generator.base.BaseEntity;

public class RolePermission extends BaseEntity<Integer> {
    /**
     * id
     */
    private Integer id;

    /**
     * roleId
     */
    private Integer roleId;

    /**
     * permissionId
     */
    private Integer permissionId;

    private static final long serialVersionUID = -4009469549096234887L;

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

    @Override
    public String getIdPropertyName() {
        return "id";
    }

    @Override
    public Integer getIdValue() {
        return id;
    }

    @Override
    public void setIdValue(Integer id) {
        this.id = id;
    }

    public static JSONObject toJSON(RolePermission e) {
        if (e == null) {
            return null;
        }
        JSONObject obj = (JSONObject) JSON.toJSON(e);
        return obj;
    }

    public static List<JSONObject> toJSON(List<RolePermission> list) {
        List<JSONObject> retList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            retList.add(toJSON(list.get(i)));
        }
        return retList;
    }

    @Override
    public JSONObject toJSON() {
        return RolePermission.toJSON(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
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
        RolePermission other = (RolePermission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals(other.getPermissionId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getPermissionId() == null) ? 0 : getPermissionId().hashCode());
        return result;
    }

    public static class Fields {
        public static final String ID = "id";

        public static final String ROLE_ID = "roleId";

        public static final String PERMISSION_ID = "permissionId";
    }

    public static class Query {
        public static final String ID__NE = "ne_id";

        public static final String ID__IN = "list_id";

        public static final String ID__BEGIN = "begin_id";

        public static final String ID__END = "end_id";

        public static final String ROLE_ID__NE = "ne_roleId";

        public static final String ROLE_ID__IN = "list_roleId";

        public static final String ROLE_ID__BEGIN = "begin_roleId";

        public static final String ROLE_ID__END = "end_roleId";

        public static final String PERMISSION_ID__NE = "ne_permissionId";

        public static final String PERMISSION_ID__IN = "list_permissionId";

        public static final String PERMISSION_ID__BEGIN = "begin_permissionId";

        public static final String PERMISSION_ID__END = "end_permissionId";
    }
}