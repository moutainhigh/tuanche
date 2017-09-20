package com.taisf.services.enterprise.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.enterprise.entity.EnterpriseDayEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>企业基本信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/13.
 * @version 1.0
 * @since 1.0
 */
@Repository("enterprise.enterpriseDayDao")
public class EnterpriseDayDao extends BaseDao {

    private String SQLID = "enterprise.enterpriseDayDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(EnterpriseDayDao.class);


    /**
     * 获取当前的企业配送时间
     * @author afi
     * @param enterpriseCode
     * @return
     */
    public List<EnterpriseDayEntity> getEnterpriseDaysByCode(String enterpriseCode){
        return mybatisDaoContext.findAll(SQLID+"getEnterpriseDaysByCode", EnterpriseDayEntity.class, enterpriseCode);
    }

    /**
     * 增加企业
     * @author afi
     * @param record
     * @return
     */
    public int saveEnterpriseDay(EnterpriseDayEntity record){

        return mybatisDaoContext.save(SQLID + "saveEnterpriseDay", record);
    }
    /**
     * 修改企业信息
     * @author afi
     * @param record
     * @return
     */
    public int updateEnterpriseDay(EnterpriseDayEntity record){
        return mybatisDaoContext.update(SQLID + "updateEnterpriseDay", record);
    }

}
