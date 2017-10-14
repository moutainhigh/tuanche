package com.taisf.services.recharge.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.recharge.dto.ChargeHisRequest;
import com.taisf.services.recharge.dto.ChargeRequest;
import com.taisf.services.recharge.entity.RechargeEntity;

/**
 * <p>充值</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/12.
 * @version 1.0
 * @since 1.0
 */
public interface RechargeService {


    /**
     * 分页查询充值记录
     * @author afi
     * @param chargeHisRequest
     * @return
     */
    DataTransferObject<PagingResult<RechargeEntity>> getRechargeByPage(ChargeHisRequest chargeHisRequest);


    /**
     * 企业充值
     * @author afi
     * @param chargeRequest
     * @return
     */
    DataTransferObject<Void> chargeMoney(ChargeRequest chargeRequest);



}
