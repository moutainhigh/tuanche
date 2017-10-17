package com.taisf.services.order.dto;

import com.jk.framework.base.page.PageRequest;

/**
 * <p>订单列表查询</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/25.
 * @version 1.0
 * @since 1.0
 */
public class OrderInfoRequest extends PageRequest {

    private static final long serialVersionUID = 301231231201446703L;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 骑士id
     */
    private String knightUid;


    /**
     * 开始时间
     */
    private String openTime;
    /**
     * 截止时间
     */
    private String tillTime;

    public String getKnightUid() {
        return knightUid;
    }

    public void setKnightUid(String knightUid) {
        this.knightUid = knightUid;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getTillTime() {
        return tillTime;
    }

    public void setTillTime(String tillTime) {
        this.tillTime = tillTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

}
