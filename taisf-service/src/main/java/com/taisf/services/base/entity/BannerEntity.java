package com.taisf.services.base.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

public class BannerEntity extends BaseEntity{
    /**
     * @Field @serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 位置 1 首页 2 商城 3 食物库 4 专家咨询
     */
    private String position;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 截止时间
     */
    private Date endTime;
    /**
     * 排序
     */
    private Integer rank;
    /**
     * 标的物URL
     */
    private String url;
    /**
     * 素材图片url
     */
    private String imageUrl;
    /**
     * 状态 1 正常 2 下架
     */
    private Integer state;
    /**
     * 平台 1 iOS 2 Android 3 微信
     */
    private Integer platform;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    private  String appCode;
    /**
     * 跳转类型 1 原生 2 h5
     */
    private Integer jumpType;

    /**
     *应用名称
     */
    private  String appName;

    /**
     * 位置名称
     */
    private  String positionName;

    public String getAppCode() {
        return appCode;
    }
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
    public Integer getId() {
        return id;
    }
    public Integer getJumpType() {
        return jumpType;
    }
    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public Integer getPlatform() {
        return platform;
    }
    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}