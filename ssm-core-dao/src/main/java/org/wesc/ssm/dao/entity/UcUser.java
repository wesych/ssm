package org.wesc.ssm.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class UcUser implements Serializable {
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

    private static final long serialVersionUID = 1L;

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
        UcUser other = (UcUser) that;
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
}