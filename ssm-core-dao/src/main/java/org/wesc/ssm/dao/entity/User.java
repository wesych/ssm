package org.wesc.ssm.dao.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.wesc.ssm.dao.generator.base.BaseEntity;

public class User extends BaseEntity<Integer> {
    /**
     * userId
     */
    private Integer userId;

    /**
     * account
     */
    private String account;

    /**
     * nickname
     */
    private String nickname;

    /**
     * mobile
     */
    private String mobile;

    /**
     * password
     */
    private String password;

    /**
     * email
     */
    private String email;

    /**
     * gender
     */
    private Boolean gender;

    /**
     * city
     */
    private String city;

    /**
     * birth
     */
    private Date birth;

    /**
     * joinTime
     */
    private Date joinTime;

    /**
     * locked
     */
    private Boolean locked;

    private static final long serialVersionUID = 3730140540624156256L;

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
     * {@link #account}
     */
    public String getAccount() {
        return account;
    }

    /**
     * {@link #account}
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * {@link #nickname}
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * {@link #nickname}
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * {@link #mobile}
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * {@link #mobile}
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * {@link #password}
     */
    public String getPassword() {
        return password;
    }

    /**
     * {@link #password}
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * {@link #email}
     */
    public String getEmail() {
        return email;
    }

    /**
     * {@link #email}
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * {@link #gender}
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * {@link #gender}
     */
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    /**
     * {@link #city}
     */
    public String getCity() {
        return city;
    }

    /**
     * {@link #city}
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * {@link #birth}
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * {@link #birth}
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * {@link #joinTime}
     */
    public Date getJoinTime() {
        return joinTime;
    }

    /**
     * {@link #joinTime}
     */
    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * {@link #locked}
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * {@link #locked}
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String getIdPropertyName() {
        return "userId";
    }

    @Override
    public Integer getIdValue() {
        return userId;
    }

    @Override
    public void setIdValue(Integer id) {
        this.userId = id;
    }

    public static JSONObject toJSON(User e) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (e == null) {
            return null;
        }
        JSONObject obj = (JSONObject) JSON.toJSON(e);
        if (e.getBirth() != null) {
            obj.put("birthStr", fmt.format(e.getBirth()));
        }
        if (e.getJoinTime() != null) {
            obj.put("joinTimeStr", fmt.format(e.getJoinTime()));
        }
        return obj;
    }

    public static List<JSONObject> toJSON(List<User> list) {
        List<JSONObject> retList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            retList.add(toJSON(list.get(i)));
        }
        return retList;
    }

    @Override
    public JSONObject toJSON() {
        return User.toJSON(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", account=").append(account);
        sb.append(", nickname=").append(nickname);
        sb.append(", mobile=").append(mobile);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", gender=").append(gender);
        sb.append(", city=").append(city);
        sb.append(", birth=").append(birth);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", locked=").append(locked);
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
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getBirth() == null ? other.getBirth() == null : this.getBirth().equals(other.getBirth()))
            && (this.getJoinTime() == null ? other.getJoinTime() == null : this.getJoinTime().equals(other.getJoinTime()))
            && (this.getLocked() == null ? other.getLocked() == null : this.getLocked().equals(other.getLocked()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getBirth() == null) ? 0 : getBirth().hashCode());
        result = prime * result + ((getJoinTime() == null) ? 0 : getJoinTime().hashCode());
        result = prime * result + ((getLocked() == null) ? 0 : getLocked().hashCode());
        return result;
    }

    public static class Fields {
        public static final String USER_ID = "userId";

        public static final String ACCOUNT = "account";

        public static final String NICKNAME = "nickname";

        public static final String MOBILE = "mobile";

        public static final String PASSWORD = "password";

        public static final String EMAIL = "email";

        public static final String GENDER = "gender";

        public static final String CITY = "city";

        public static final String BIRTH = "birth";

        public static final String JOIN_TIME = "joinTime";

        public static final String LOCKED = "locked";
    }

    public static class Query {
        public static final String USER_ID__NE = "ne_userId";

        public static final String USER_ID__IN = "list_userId";

        public static final String USER_ID__BEGIN = "begin_userId";

        public static final String USER_ID__END = "end_userId";

        public static final String ACCOUNT__NE = "ne_account";

        public static final String ACCOUNT__LIKE = "like_account";

        public static final String ACCOUNT__IN = "list_account";

        public static final String ACCOUNT__BEGIN = "begin_account";

        public static final String ACCOUNT__END = "end_account";

        public static final String NICKNAME__NE = "ne_nickname";

        public static final String NICKNAME__LIKE = "like_nickname";

        public static final String NICKNAME__IN = "list_nickname";

        public static final String NICKNAME__BEGIN = "begin_nickname";

        public static final String NICKNAME__END = "end_nickname";

        public static final String MOBILE__NE = "ne_mobile";

        public static final String MOBILE__LIKE = "like_mobile";

        public static final String MOBILE__IN = "list_mobile";

        public static final String MOBILE__BEGIN = "begin_mobile";

        public static final String MOBILE__END = "end_mobile";

        public static final String PASSWORD__NE = "ne_password";

        public static final String PASSWORD__LIKE = "like_password";

        public static final String PASSWORD__IN = "list_password";

        public static final String PASSWORD__BEGIN = "begin_password";

        public static final String PASSWORD__END = "end_password";

        public static final String EMAIL__NE = "ne_email";

        public static final String EMAIL__LIKE = "like_email";

        public static final String EMAIL__IN = "list_email";

        public static final String EMAIL__BEGIN = "begin_email";

        public static final String EMAIL__END = "end_email";

        public static final String GENDER__NE = "ne_gender";

        public static final String GENDER__IN = "list_gender";

        public static final String GENDER__BEGIN = "begin_gender";

        public static final String GENDER__END = "end_gender";

        public static final String CITY__NE = "ne_city";

        public static final String CITY__LIKE = "like_city";

        public static final String CITY__IN = "list_city";

        public static final String CITY__BEGIN = "begin_city";

        public static final String CITY__END = "end_city";

        public static final String BIRTH__NE = "ne_birth";

        public static final String BIRTH__IN = "list_birth";

        public static final String BIRTH__BEGIN = "begin_birth";

        public static final String BIRTH__END = "end_birth";

        public static final String JOIN_TIME__NE = "ne_joinTime";

        public static final String JOIN_TIME__IN = "list_joinTime";

        public static final String JOIN_TIME__BEGIN = "begin_joinTime";

        public static final String JOIN_TIME__END = "end_joinTime";

        public static final String LOCKED__NE = "ne_locked";

        public static final String LOCKED__IN = "list_locked";

        public static final String LOCKED__BEGIN = "begin_locked";

        public static final String LOCKED__END = "end_locked";
    }
}