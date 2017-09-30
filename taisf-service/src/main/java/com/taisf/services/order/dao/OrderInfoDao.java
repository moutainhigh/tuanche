package com.taisf.services.order.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.vo.OrderInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <p> 订单的试图操作 </p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderInfoDao")
public class OrderInfoDao extends BaseDao{
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderInfoDao.class);

	private String SQLID = "order.orderInfoDao.";


    /**
     * 获取当前订单的信息
     * @author afi
     * @param orderInfoRequest
     * @return
     */
    public PagingResult<OrderInfoVO> getOrderInfoPage(OrderInfoRequest orderInfoRequest){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(orderInfoRequest.getLimit());
		pageBounds.setPage(orderInfoRequest.getPage());

        return mybatisDaoContext.findForPage(SQLID + "getOrderInfo", OrderInfoVO.class, orderInfoRequest,pageBounds);
    }

    



}
