
package com.taisf.web.oms.permission.api;


import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.web.oms.permission.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeService {
	/**
	 * 根据科室id查询人员信息
	 * @param departmentId
	 * @return
	 */
	DataTransferObject<List<EmployeeEntity>> listEmployeeByDepartId(String departmentId);


	/**
	 * 通过医生姓名查询医生列表
	 * @author afi
	 * @param name
	 * @return
	 */
	DataTransferObject<List<EmployeeEntity>> findEmployeeByName(String name);

	/**
	 * 获取所有用户列表
	 * @author zyf
	 * @return
	 */
	DataTransferObject<List<EmployeeEntity>> findAllEmployee();

	/**
	 * 查询医生列表
	 * @author zyf
	 * @return DataTransferObject
	 */
	DataTransferObject<List<EmployeeEntity>> findAllDoctor();
}

