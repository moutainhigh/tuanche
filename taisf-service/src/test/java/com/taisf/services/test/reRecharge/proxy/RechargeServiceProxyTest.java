package com.taisf.services.test.reRecharge.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.recharge.api.RechargeService;
import com.taisf.services.recharge.dto.ChargeRequest;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
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
public class RechargeServiceProxyTest extends BaseTest {



    @Resource(name = "recharge.rechargeServiceProxy")
    private RechargeService rechargeService;


    @Test
    public void chargeMoneyTest() {

        ChargeRequest request = new ChargeRequest();
        request.setEnterpriseCode("code");
        request.setMoneyYuan(100);
        request.setOpId("id");
        request.setOpName("name");
        DataTransferObject<Void> chargeMoney = rechargeService.chargeMoney(request);
        System.out.println(JsonEntityTransform.Object2Json(chargeMoney));

    }




}
