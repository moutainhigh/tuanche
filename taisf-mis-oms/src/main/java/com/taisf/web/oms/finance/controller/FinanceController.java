package com.taisf.web.oms.finance.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.enterprise.api.EnterpriseService;
import com.taisf.services.enterprise.dto.EnterprisePageRequest;
import com.taisf.services.enterprise.vo.EnterpriseAccountVO;
import com.taisf.services.recharge.api.RechargeService;
import com.taisf.services.recharge.dto.ChargeHisRequest;
import com.taisf.services.recharge.entity.RechargeEntity;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dto.UserAccountRequest;
import com.taisf.services.user.vo.UserAccountVO;
import com.taisf.web.oms.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>财务相关</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/16.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {


    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    EnterpriseService enterpriseService;


    @Autowired
    UserService userService;




    @RequestMapping("/balanceList")
    public String list(HttpServletRequest request) {
        return "finance/balanceList";
    }


    /**
     * 获取企业余额管理
     * @author afi
     * @param request
     * @param enterprisePageRequest
     * @return
     */
    @RequestMapping("/balanceListPage")
    @ResponseBody
    public PageResult balanceListPage(HttpServletRequest request, EnterprisePageRequest enterprisePageRequest) {

        PageResult pageResult = new PageResult();
        try {


            DataTransferObject<PagingResult<EnterpriseAccountVO>> dto = enterpriseService.getEnterpriseAccountByPage(enterprisePageRequest);
            //查询出当前供餐商下所有菜品信息
            if (!Check.NuNObj(dto.getData())) {
                pageResult.setRows(dto.getData().getList());
                pageResult.setTotal(dto.getData().getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params :{}", JsonEntityTransform.Object2Json(enterprisePageRequest));
            LogUtil.error(LOGGER, "error :{}", e);
            return new PageResult();
        }
        return pageResult;
    }


    /**
     * 获取账户信息
     * @author afi
     * @param request
     * @param userAccountRequest
     * @return
     */
    @RequestMapping("/accountPage")
    @ResponseBody
    public PageResult accountPage(HttpServletRequest request, UserAccountRequest userAccountRequest) {

        PageResult pageResult = new PageResult();
        try {
            DataTransferObject<PagingResult<UserAccountVO>> dto =userService.getUserAccountPage(userAccountRequest);
            //查询出当前供餐商下所有菜品信息
            if (!Check.NuNObj(dto.getData())) {
                pageResult.setRows(dto.getData().getList());
                pageResult.setTotal(dto.getData().getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params :{}", JsonEntityTransform.Object2Json(userAccountRequest));
            LogUtil.error(LOGGER, "error :{}", e);
            return new PageResult();
        }
        return pageResult;
    }




}
