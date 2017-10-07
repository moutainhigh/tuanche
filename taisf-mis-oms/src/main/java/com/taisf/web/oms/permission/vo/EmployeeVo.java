package com.taisf.web.oms.permission.vo;


import java.util.ArrayList;
import java.util.List;

import com.taisf.web.oms.permission.entity.EmployeeEntity;

/**
 * 
 * <p>请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class EmployeeVo extends EmployeeEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4625548985466290939L;
	
	
	/**
	 * 用户角色
	 */
	private List<String> roleFidList=new ArrayList<String>();
	
	/**
	 * 用户角色
	 */
	private List<EmployeeRoleVo> roles=new ArrayList<EmployeeRoleVo>();



	public List<String> getRoleFidList() {
		return roleFidList;
	}


	public void setRoleFidList(List<String> roleFidList) {
		this.roleFidList = roleFidList;
	}


	public List<EmployeeRoleVo> getRoles() {
		return roles;
	}


	public void setRoles(List<EmployeeRoleVo> roles) {
		this.roles = roles;
	}

	
}
