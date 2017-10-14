package com.taisf.services.enterprise.dto;

import com.jk.framework.base.page.PageRequest;

/**
 * <p>企业编号</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/14.
 * @version 1.0
 * @since 1.0
 */
public class EnterprisePageRequest extends PageRequest {

    /**
     * 企业编号
     */
    private String enterpriseCode;

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }
}
