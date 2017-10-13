package com.taisf.services.base.dao;

import com.taisf.services.base.entity.EmployeeSupplierEntity;
import com.taisf.services.common.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/27.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.employeeSupplierDao")
public class employeeSupplierDao extends BaseDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(employeeSupplierDao.class);

    private String SQLID = "basedata.employeeSupplierDao.";

    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:根据userId查询供餐商code
     **/
    public EmployeeSupplierEntity getByUserId(String userId){
        return mybatisDaoContext.findOneSlave(SQLID+"getByUserId", EmployeeSupplierEntity.class, userId);
    }

}
