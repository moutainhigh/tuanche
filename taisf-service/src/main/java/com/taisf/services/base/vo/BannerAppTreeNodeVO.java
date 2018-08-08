package com.taisf.services.base.vo;

import com.jk.framework.base.entity.BaseEntity;

public class BannerAppTreeNodeVO extends BaseEntity{

    private String id;

    /**
     * 应用名称
     */
    private String text;

    private Integer leafLevel;
    /**
     * AppCode :属于哪个App的banner
     */
    private String appCode;

    //private List<BannerPositionEntity> positionList;


    public Integer getLeafLevel() {
        return leafLevel;
    }

    public void setLeafLevel(Integer leafLevel) {
        this.leafLevel = leafLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}