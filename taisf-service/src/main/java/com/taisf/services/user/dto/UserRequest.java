package com.taisf.services.user.dto;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>用户的请求参数</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/6.
 * @version 1.0
 * @since 1.0
 */
public class UserRequest extends BaseEntity{
    /**
     * 开始时间
     */
    private Date openTime;
    /**
     * 截止时间
     */
    private Date tillTime;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 状态:1.正常2冻结,3已过期
     */
    private String userStatus;

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getTillTime() {
        return tillTime;
    }

    public void setTillTime(Date tillTime) {
        this.tillTime = tillTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
