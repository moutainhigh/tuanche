package com.taisf.web.oms.everydayTask;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.enterprise.api.EnterpriseService;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.order.manager.OrderManagerImpl;
import com.taisf.web.oms.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        request.setAttribute("entityList",entityList);
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


}
