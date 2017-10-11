package com.taisf.services.user.vo;

import com.jk.framework.base.entity.BaseEntity;

/**
 * <p>基本属性</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/11.
 * @version 1.0
 * @since 1.0
 */
public class IndexUserVO  extends BaseEntity {

    private static final long serialVersionUID = 301231231201446703L;


    /**
     * 用户名称
     */
    private String userName = "";

    /**
     * 企业编码
     */
    private String enterpriseCode = "";

    /**
     * 企业名称
     */
    private String enterpriseName = "";


    /**
     * 手机号
     */
    private String userPhone = "";


    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户角色
     */
    private Integer userRole;


    /**
     * 餐属性
     */
    private Integer productSource;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getProductSource() {
        return productSource;
    }

    public void setProductSource(Integer productSource) {
        this.productSource = productSource;
    }
}