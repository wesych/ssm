package org.wesc.ssm.dao.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.wesc.ssm.dao.generator.base.BaseEntity;

public class UserRole extends BaseEntity<Integer> {
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

    private static final long serialVersionUID = -848382393475385822L;

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

    public static JSONObject toJSON(UserRole e) {
        if (e == null) {
            return null;
        }
        JSONObject obj = (JSONObject) JSON.toJSON(e);
        return obj;
    }

    public static List<JSONObject> toJSON(List<UserRole> list) {
        List<JSONObject> retList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            retList.add(toJSON(list.get(i)));
        }
        return retList;
    }

    @Override
    public JSONObject toJSON() {
        return UserRole.toJSON(this);
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

    public static class Fields {
        public static final String ID = "id";

        public static final String USER_ID = "userId";

        public static final String ROLE_ID = "roleId";
    }

    public static class Query {
        public static final String ID__NE = "ne_id";

        public static final String ID__IN = "list_id";

        public static final String ID__BEGIN = "begin_id";

        public static final String ID__END = "end_id";

        public static final String USER_ID__NE = "ne_userId";

        public static final String USER_ID__IN = "list_userId";

        public static final String USER_ID__BEGIN = "begin_userId";

        public static final String USER_ID__END = "end_userId";

        public static final String ROLE_ID__NE = "ne_roleId";

        public static final String ROLE_ID__IN = "list_roleId";

        public static final String ROLE_ID__BEGIN = "begin_roleId";

        public static final String ROLE_ID__END = "end_roleId";
    }
}