package com.taisf.services.enterprise.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.enterprise.dto.EnterprisePageRequest;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.vo.OrderInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
@Repository("enterprise.enterpriseDao")
public class EnterpriseDao extends BaseDao {

    private String SQLID = "enterprise.enterpriseDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(EnterpriseDao.class);


    /**
     * 获取当前的企业进本信息
     * @author afi
     * @param enterpriseCode
     * @return
     */
    public EnterpriseEntity getEnterpriseByCode(String enterpriseCode){
        return mybatisDaoContext.findOne(SQLID+"getEnterpriseByCode", EnterpriseEntity.class, enterpriseCode);
    }



    /**
     * 获取企业列表信息
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<EnterpriseEntity> getEnterpriseByPage(EnterprisePageRequest request){
        PageBounds pageBounds=new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());

        return mybatisDaoContext.findForPage(SQLID + "getEnterpriseByPage", EnterpriseEntity.class, request,pageBounds);
    }


    /**
     * 增加企业
     * @author afi
     * @param record
     * @return
     */
    public int saveEnterprise(EnterpriseEntity record){

        return mybatisDaoContext.save(SQLID + "saveEnterprise", record);
    }
    /**
     * 修改企业信息
     * @author afi
     * @param record
     * @return
     */
    public int updateEnterprise(EnterpriseEntity record){
        return mybatisDaoContext.update(SQLID + "updateEnterprise", record);
    }

}
