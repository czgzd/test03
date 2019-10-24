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
 * 账户
 * </p>
 *
 * @author 葛明明123
 * @since 2019-09-10
 */
@TableName("user_account_info")
public class UserAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private long userId;
    /**
     * 总积分
     */
    private Integer integral;
    /**
     * 豆余额:等价与RMB
     */
    private Double bean;
    /**
     * 豆合计:等价与RMB
     */
    @TableField("total_bean")
    private Double totalBean;
    /**
     * 减排量
     */
    @TableField("emission_reduction")
    private Double emissionReduction;
    /**
     * 上线天数
     */
    @TableField("on_line_days")
    private Integer onLineDays;
    /**
     * 步行数
     */
    @TableField("walk_step")
    private Integer walkStep;
    /**
     * 骑行公里数
     */
    @TableField("bike_kilometre")
    private Double bikeKilometre;
    /**
     * 签到积分
     */
    @TableField("sign_integral")
    private Integer signIntegral;
    /**
     * 签到次数
     */
    @TableField("sign_number")
    private Integer signNumber;
    /**
     * 登录积分
     */
    @TableField("login_integral")
    private Integer loginIntegral;
    /**
     * 步行积分
     */
    @TableField("walk_integral")
    private Integer walkIntegral;
    /**
     * 骑行积分
     */
    @TableField("bike_integral")
    private Integer bikeIntegral;
    /**
     * 兑换次数
     */
    @TableField("exchanges_number")
    private Integer exchangesNumber;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;
    /**
     * 租户id/企业码/注册码
     */
    @TableField("registration_code")
    private String registrationCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Double getBean() {
        return bean;
    }

    public void setBean(Double bean) {
        this.bean = bean;
    }

    public Double getTotalBean() {
        return totalBean;
    }

    public void setTotalBean(Double totalBean) {
        this.totalBean = totalBean;
    }

    public Double getEmissionReduction() {
        return emissionReduction;
    }

    public void setEmissionReduction(Double emissionReduction) {
        this.emissionReduction = emissionReduction;
    }

    public Integer getOnLineDays() {
        return onLineDays;
    }

    public void setOnLineDays(Integer onLineDays) {
        this.onLineDays = onLineDays;
    }

    public Integer getWalkStep() {
        return walkStep;
    }

    public void setWalkStep(Integer walkStep) {
        this.walkStep = walkStep;
    }

    public Double getBikeKilometre() {
        return bikeKilometre;
    }

    public void setBikeKilometre(Double bikeKilometre) {
        this.bikeKilometre = bikeKilometre;
    }

    public Integer getSignIntegral() {
        return signIntegral;
    }

    public void setSignIntegral(Integer signIntegral) {
        this.signIntegral = signIntegral;
    }

    public Integer getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(Integer signNumber) {
        this.signNumber = signNumber;
    }

    public Integer getLoginIntegral() {
        return loginIntegral;
    }

    public void setLoginIntegral(Integer loginIntegral) {
        this.loginIntegral = loginIntegral;
    }

    public Integer getWalkIntegral() {
        return walkIntegral;
    }

    public void setWalkIntegral(Integer walkIntegral) {
        this.walkIntegral = walkIntegral;
    }

    public Integer getBikeIntegral() {
        return bikeIntegral;
    }

    public void setBikeIntegral(Integer bikeIntegral) {
        this.bikeIntegral = bikeIntegral;
    }

    public Integer getExchangesNumber() {
        return exchangesNumber;
    }

    public void setExchangesNumber(Integer exchangesNumber) {
        this.exchangesNumber = exchangesNumber;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public String toString() {
        return "UserAccountInfo{" +
        ", id=" + id +
        ", userId=" + userId +
        ", integral=" + integral +
        ", bean=" + bean +
        ", totalBean=" + totalBean +
        ", emissionReduction=" + emissionReduction +
        ", onLineDays=" + onLineDays +
        ", walkStep=" + walkStep +
        ", bikeKilometre=" + bikeKilometre +
        ", signIntegral=" + signIntegral +
        ", signNumber=" + signNumber +
        ", loginIntegral=" + loginIntegral +
        ", walkIntegral=" + walkIntegral +
        ", bikeIntegral=" + bikeIntegral +
        ", exchangesNumber=" + exchangesNumber +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        ", registrationCode=" + registrationCode +
        "}";
    }
}
