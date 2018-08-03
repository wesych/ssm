package org.wesc.ssm.dao.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.wesc.ssm.dao.generator.base.BaseEntity;

public class Role extends BaseEntity<Integer> {
    /**
     * roleId
     */
    private Integer roleId;

    /**
     * roleName
     */
    private String roleName;

    /**
     * createTime
     */
    private Date createTime;

    private static final long serialVersionUID = -8982340372655168475L;

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
     * {@link #roleName}
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * {@link #roleName}
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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
    public String getIdPropertyName() {
        return "roleId";
    }

    @Override
    public Integer getIdValue() {
        return roleId;
    }

    @Override
    public void setIdValue(Integer id) {
        this.roleId = id;
    }

    public static JSONObject toJSON(Role e) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (e == null) {
            return null;
        }
        JSONObject obj = (JSONObject) JSON.toJSON(e);
        if (e.getCreateTime() != null) {
            obj.put("createTimeStr", fmt.format(e.getCreateTime()));
        }
        return obj;
    }

    public static List<JSONObject> toJSON(List<Role> list) {
        List<JSONObject> retList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            retList.add(toJSON(list.get(i)));
        }
        return retList;
    }

    @Override
    public JSONObject toJSON() {
        return Role.toJSON(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", createTime=").append(createTime);
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
        Role other = (Role) that;
        return (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    public static class Fields {
        public static final String ROLE_ID = "roleId";

        public static final String ROLE_NAME = "roleName";

        public static final String CREATE_TIME = "createTime";
    }

    public static class Query {
        public static final String ROLE_ID__NE = "ne_roleId";

        public static final String ROLE_ID__IN = "list_roleId";

        public static final String ROLE_ID__BEGIN = "begin_roleId";

        public static final String ROLE_ID__END = "end_roleId";

        public static final String ROLE_NAME__NE = "ne_roleName";

        public static final String ROLE_NAME__LIKE = "like_roleName";

        public static final String ROLE_NAME__IN = "list_roleName";

        public static final String ROLE_NAME__BEGIN = "begin_roleName";

        public static final String ROLE_NAME__END = "end_roleName";

        public static final String CREATE_TIME__NE = "ne_createTime";

        public static final String CREATE_TIME__IN = "list_createTime";

        public static final String CREATE_TIME__BEGIN = "begin_createTime";

        public static final String CREATE_TIME__END = "end_createTime";
    }
}