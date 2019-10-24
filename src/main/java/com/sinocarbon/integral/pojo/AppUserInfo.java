package com.sinocarbon.integral.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 小程序用户信息
 * </p>
 *
 * @author 葛明明123
 * @since 2019-01-02
 */
@TableName("app_user_info")
public class AppUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 用户昵称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 用户头像
     */
    @TableField("user_img")
    private String userImg;
    /**
     * 用户性别
     */
    @TableField("user_gender")
    private Integer userGender;
    /**
     * 用户手机
     */
    @TableField("user_tel")
    private String userTel;
    
    
    /**
     * 昵称
     */
    @TableField("user_nickname")
    private String userNickname;
    
    
    /**
     * 签名
     */
    @TableField("user_signature")
    private String userSignature;
    
    
    /**
     * 注册码
     */
    @TableField("registration_code")
    private String registrationCode;
    
    
    /**
     * 用户账户状态，0未激活，1已激活，2已删除
     */
    @TableField("user_state")
    private  Integer userState;
    
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonSerialize(as = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class, as = LocalDateTime.class)
    
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    
      

    public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
  
    
    

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}

	@Override
	public String toString() {
		return "AppUserInfo [userId=" + userId + ", userName=" + userName + ", userImg=" + userImg + ", userGender="
				+ userGender + ", userTel=" + userTel + ", userNickname=" + userNickname + ", userSignature="
				+ userSignature + ", registrationCode=" + registrationCode + ", userState=" + userState
				+ ", updateTime=" + updateTime + "]";
	}



	


}
