package com.taisf.web.oms.everydaytask;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.common.valenum.OrdersStatusEnum;
import com.taisf.services.enterprise.api.EnterpriseService;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.entity.OrderEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.order.manager.OrderManagerImpl;
import com.taisf.services.order.vo.OrderListVo;
import com.taisf.web.oms.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhangzhengguang
 * @description:
 **/
@Controller
@RequestMapping("everydayTask/")
public class EverydayTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EverydayTaskController.class);

    @Autowired
    private OrderManagerImpl orderManagerImpl;

    @Autowired
    private EnterpriseService enterpriseService;


    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:菜品统计列表
     **/
    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        List<EnterpriseEntity> entityList = enterpriseService.findAll().getData();
        request.setAttribute("entityList", entityList);
        return "everydayTask/everydayTaskList";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:菜品统计列表数据
     **/
    @RequestMapping("pageList")
    @ResponseBody
    public PageResult pageList(HttpServletRequest request, EnterpriseListRequest enterpriseListRequest) {
        PageResult pageResult = new PageResult();
        try {
            PagingResult<OrderProductEntity> pagingResult = orderManagerImpl.getEverydayTaskPgeList(enterpriseListRequest);
            if (!Check.NuNObj(pagingResult)) {
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(enterpriseListRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:企业订单配送
     **/
    @RequestMapping("orderDistributionList")
    public String orderDistributionList(HttpServletRequest request) {
        List<EnterpriseEntity> entityList = enterpriseService.findAll().getData();
        request.setAttribute("entityList", entityList);
        return "everydayTask/orderDistributionList";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:企业订单配送
     **/
    @RequestMapping("finOrderDistributionList")
    @ResponseBody
    public PageResult finOrderDistributionList(HttpServletRequest request, EnterpriseListRequest enterpriseListRequest) {
        PageResult pageResult = new PageResult();
        try {
            PagingResult<OrderListVo> pagingResult = orderManagerImpl.finOrderDistributionList(enterpriseListRequest);
            if (!Check.NuNObj(pagingResult)) {
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(enterpriseListRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:配送当前企业下所有订单
     **/
    @RequestMapping("distribution")
    @ResponseBody
    public DataTransferObject<Void> distribution(HttpServletRequest request, OrderEntity orderEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if(Check.NuNObjs(orderEntity,orderEntity.getEnterpriseCode())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        try {
            orderEntity.setOrderStatus(OrdersStatusEnum.SEND.getCode());
            orderManagerImpl.updateByEnterpriseCode(orderEntity);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(orderEntity));
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
            return dto;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/19
     * @description:修改订单状态根据企业编号
     **/
    @RequestMapping("findListByEnterpriseCode")
    @ResponseBody
    public PageResult findListByEnterpriseCode(HttpServletRequest request, OrderInfoRequest orderInfoRequest) {
        PageResult pageResult = new PageResult();
        try {
            PagingResult<OrderEntity> pagingResult = orderManagerImpl.findListByEnterpriseCode(orderInfoRequest);
            if (!Check.NuNObj(pagingResult)) {
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }else{
                List<OrderEntity> list = new ArrayList<>();
                list.add(new OrderEntity());
                pageResult.setRows(list);
                pageResult.setTotal(1L);
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(orderInfoRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }
}
