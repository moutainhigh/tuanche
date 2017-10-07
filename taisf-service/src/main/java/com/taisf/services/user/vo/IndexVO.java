package com.taisf.services.user.vo;

import com.jk.framework.base.entity.BaseEntity;
import com.taisf.services.user.entity.UserEntity;

/**
 * <p>首页的信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/7.
 * @version 1.0
 * @since 1.0
 */
public class IndexVO extends BaseEntity{

    /**
     * 用户信息
     */
    private UserEntity userEntity;


    /**
     * 余额
     */
    private Integer drawBalance;

    /**
     * 显示名
     */
    private String timeMsg;

    /**
     * 时间倒计时
     */
    private String timeLast;


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getDrawBalance() {
        return drawBalance;
    }

    public void setDrawBalance(Integer drawBalance) {
        this.drawBalance = drawBalance;
    }

    public String getTimeMsg() {
        return timeMsg;
    }

    public void setTimeMsg(String timeMsg) {
        this.timeMsg = timeMsg;
    }

    public String getTimeLast() {
        return timeLast;
    }

    public void setTimeLast(String timeLast) {
        this.timeLast = timeLast;
    }
}
