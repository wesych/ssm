package org.wesc.ssm.dao.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.wesc.ssm.dao.generator.base.BaseEntity;

public class Permission extends BaseEntity<Integer> {
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

    private static final long serialVersionUID = 2802106662658875869L;

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
    public String getIdPropertyName() {
        return "permissionId";
    }

    @Override
    public Integer getIdValue() {
        return permissionId;
    }

    @Override
    public void setIdValue(Integer id) {
        this.permissionId = id;
    }

    public static JSONObject toJSON(Permission e) {
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

    public static List<JSONObject> toJSON(List<Permission> list) {
        List<JSONObject> retList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            retList.add(toJSON(list.get(i)));
        }
        return retList;
    }

    @Override
    public JSONObject toJSON() {
        return Permission.toJSON(this);
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

    public static class Fields {
        public static final String PERMISSION_ID = "permissionId";

        public static final String PERMISSION_NAME = "permissionName";

        public static final String CREATE_TIME = "createTime";
    }

    public static class Query {
        public static final String PERMISSION_ID__NE = "ne_permissionId";

        public static final String PERMISSION_ID__IN = "list_permissionId";

        public static final String PERMISSION_ID__BEGIN = "begin_permissionId";

        public static final String PERMISSION_ID__END = "end_permissionId";

        public static final String PERMISSION_NAME__NE = "ne_permissionName";

        public static final String PERMISSION_NAME__LIKE = "like_permissionName";

        public static final String PERMISSION_NAME__IN = "list_permissionName";

        public static final String PERMISSION_NAME__BEGIN = "begin_permissionName";

        public static final String PERMISSION_NAME__END = "end_permissionName";

        public static final String CREATE_TIME__NE = "ne_createTime";

        public static final String CREATE_TIME__IN = "list_createTime";

        public static final String CREATE_TIME__BEGIN = "begin_createTime";

        public static final String CREATE_TIME__END = "end_createTime";
    }
}