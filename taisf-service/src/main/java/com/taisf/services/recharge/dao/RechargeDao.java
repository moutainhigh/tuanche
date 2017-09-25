package com.taisf.services.recharge.dao;

import com.jk.framework.base.utils.Check;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.recharge.entity.RechargeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * <p>充值信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/20.
 * @version 1.0
 * @since 1.0
 */
@Repository("recharge.rechargeDao")
public class RechargeDao extends BaseDao {

    private String SQLID = "recharge.rechargeDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RechargeDao.class);

    /**
     * 获取充值信息
     * @author afi
     * @param rechargeSn
     * @return
     */
    public RechargeEntity getRechargeEntityBySn(String rechargeSn){
        return mybatisDaoContext.findOne(SQLID+"getRechargeEntityBySn", RechargeEntity.class, rechargeSn);
    }

    /**
     * 新增充值信息
     * @author afi
     * @param record
     * @return
     */
    public int saveRecharge(RechargeEntity record){
        if (Check.NuNObj(record.getCreateTime())){
            record.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "saveRecharge", record);
    }
    /**
     * 修改充值信息
     * @author afi
     * @param record
     * @return
     */
    public int updateRecharge(RechargeEntity record){
        return mybatisDaoContext.update(SQLID + "updateRecharge", record);
    }
}
