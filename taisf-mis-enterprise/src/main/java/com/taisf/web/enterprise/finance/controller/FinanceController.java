package com.taisf.web.enterprise.finance.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.common.valenum.EnterpriseStatusEnum;
import com.taisf.services.enterprise.api.EnterpriseService;
import com.taisf.services.enterprise.dto.EnterprisePageRequest;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.vo.EnterpriseAccountVO;
import com.taisf.services.recharge.api.RechargeService;
import com.taisf.services.recharge.dto.BalanceMoneyAvgRequest;
import com.taisf.services.recharge.dto.BalanceMoneyOneRequest;
import com.taisf.services.recharge.dto.ChargeRequest;
import com.taisf.services.recharge.vo.EnterpriseStatsNumber;
import com.taisf.services.ups.entity.EmployeeEntity;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dto.UserAccountRequest;
import com.taisf.services.user.vo.UserAccountVO;
import com.taisf.web.enterprise.common.constant.LoginConstant;
import com.taisf.web.enterprise.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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




    @RequestMapping("/enterpriseRechargeList")
    public String enterpriseRechargeList(HttpServletRequest request) {
        return "finance/enterpriseRechargeList";
    }


    /**
     * 企业列表
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("enterprisePageListPage")
    @ResponseBody
    public PageResult enterprisePageListPage(HttpServletRequest request, EnterprisePageRequest req) {
        PageResult pageResult = new PageResult();
        try {
            if (Check.NuNObj(req)){
                req = new EnterprisePageRequest();
            }
            HttpSession session = request.getSession();
            EmployeeEntity emp = (EmployeeEntity)session.getAttribute(LoginConstant.SESSION_KEY);
            req.setSupplierCode(emp.getEmpBiz());

            req.setEnterpriseStatus(EnterpriseStatusEnum.SUCCESS.getCode());
            DataTransferObject<PagingResult<EnterpriseEntity>> dto = enterpriseService.getEnterpriseByPage(req);
            if (dto.checkSuccess()){
                PagingResult<EnterpriseEntity> pagingResult = dto.getData();
                if(Check.NuNObj(pagingResult)){
                    return pageResult;
                }
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(req));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }



    @RequestMapping("/balanceList")
    public String balanceList(HttpServletRequest request) {
        return "finance/balanceList";
    }


    /**
     * 获取企业的统计信息
     * @author afi
     * @param request
     * @param enterpriseCode
     * @return
     */
    @RequestMapping("/enterpriseStats")
    @ResponseBody
    public DataTransferObject balanceListPage(HttpServletRequest request, String enterpriseCode) {
        DataTransferObject<EnterpriseStatsNumber> dto = new DataTransferObject<>();
        try {
            return rechargeService.getEnterpriseStatsNumber(enterpriseCode);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params :{}", JsonEntityTransform.Object2Json(enterpriseCode));
            LogUtil.error(LOGGER, "error :{}", e);
            dto.setErrorMsg("处理异常");
        }
        return dto;
    }




    /**
     * 企业充值
     * @author afi
     * @param request
     * @param chargeRequest
     * @return
     */
    @RequestMapping("/chargeMoney")
    @ResponseBody
    public DataTransferObject chargeMoney(HttpServletRequest request, ChargeRequest chargeRequest) {
        DataTransferObject<EnterpriseStatsNumber> dto = new DataTransferObject<>();
        try {
            EmployeeEntity loginUser = (EmployeeEntity) request.getSession().getAttribute(LoginConstant.SESSION_KEY);
            if(loginUser!=null&&loginUser.getUserId()!=null){
                chargeRequest.setOpId(loginUser.getUserId());
                chargeRequest.setOpName(loginUser.getEmpName());
            }else {
                dto.setErrorMsg("获取操作人失败");
            }
            return rechargeService.chargeMoney(chargeRequest);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params :{}", JsonEntityTransform.Object2Json(chargeRequest));
            LogUtil.error(LOGGER, "error :{}", e);
            dto.setErrorMsg("处理异常");
        }
        return dto;
    }



    /**
     * 平均分配金额
     * @author afi
     * @param request
     * @param balanceMoneyAvgRequest
     * @return
     */
    @RequestMapping("/balanceMoneyAvg")
    @ResponseBody
    public DataTransferObject balanceMoneyAvg(HttpServletRequest request, BalanceMoneyAvgRequest balanceMoneyAvgRequest) {
        DataTransferObject<EnterpriseStatsNumber> dto = new DataTransferObject<>();
        try {
            return rechargeService.balanceMoneyAvg(balanceMoneyAvgRequest);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params :{}", JsonEntityTransform.Object2Json(balanceMoneyAvgRequest));
            LogUtil.error(LOGGER, "error :{}", e);
            dto.setErrorMsg("处理异常");
        }
        return dto;
    }


    /**
     * 个人充值
     * @author afi
     * @param request
     * @param balanceMoneyOneRequest
     * @return
     */
    @RequestMapping("/balanceMoneyOne")
    @ResponseBody
    public DataTransferObject balanceMoneyOne(HttpServletRequest request, BalanceMoneyOneRequest balanceMoneyOneRequest) {
        DataTransferObject<EnterpriseStatsNumber> dto = new DataTransferObject<>();
        try {
            return rechargeService.balanceMoneyOne(balanceMoneyOneRequest);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params :{}", JsonEntityTransform.Object2Json(balanceMoneyOneRequest));
            LogUtil.error(LOGGER, "error :{}", e);
            dto.setErrorMsg("处理异常");
        }
        return dto;
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
            HttpSession session = request.getSession();
            EmployeeEntity emp = (EmployeeEntity)session.getAttribute(LoginConstant.SESSION_KEY);
            enterprisePageRequest.setSupplierCode(emp.getEmpBiz());

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
                if(dto.getData().getList().size() == 0){
                    dto.getData().getList().add(new UserAccountVO());
                    dto.getData().setTotal(1);
                }
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
