package com.taisf.services.user.dto;

import com.jk.framework.base.page.PageRequest;

/**
 * <p>账户的记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/21.
 * @version 1.0
 * @since 1.0
 */
public class AccountLogRequest extends PageRequest {

    private static final long serialVersionUID = 5645623423545403L;

    /**
     * 当前用户的uid
     */
    private  String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
