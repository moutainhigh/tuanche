package com.taisf.services.refund.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.refund.dto.RefundRequest;
import com.taisf.services.refund.entity.RefundEntity;
import com.taisf.services.refund.vo.RefundVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author:zhangzhengguang
 * @date:2017/12/21
 * @description:
 **/
@Repository("refund.refundDao")
public class RefundDao extends BaseDao {

    private String SQLID = "refund.refundDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RefundDao.class);
    
    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:分页条件查询
     **/
    public PagingResult<RefundVo> refundPageList(RefundRequest request){
        PageBounds page = new PageBounds();
        page.setPage(request.getPage());
        page.setLimit(request.getLimit());
        return mybatisDaoContext.findForPage(SQLID+"refundPageList", RefundVo.class,request,page);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据id查询详情
     **/
    public RefundEntity findRefundById(Integer id){
       return  mybatisDaoContext.findOne(SQLID+"selectByPrimaryKey",RefundEntity.class,id);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据id修改
     **/
    public int updateRefund(RefundEntity refundEntity){
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", refundEntity);
    }

}
