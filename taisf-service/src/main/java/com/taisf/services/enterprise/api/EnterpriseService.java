package com.taisf.services.enterprise.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.enterprise.dto.EnterpriseDayRequest;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.dto.EnterprisePageRequest;
import com.taisf.services.enterprise.dto.EnterpriseUpdateRequest;
import com.taisf.services.enterprise.entity.EnterpriseDayEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.entity.EnterpriseModel;
import com.taisf.services.enterprise.vo.EnterpriseAccountVO;
import com.taisf.services.enterprise.vo.EnterpriseDispatchVO;
import com.taisf.services.enterprise.vo.EnterpriseExtVO;
import com.taisf.services.enterprise.vo.EnterpriseListDay;

import java.util.List;

/**
 * <p>企业接口信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/6.
 * @version 1.0
 * @since 1.0
 */
public interface EnterpriseService {


    /**
     * 改变配送信息
     * @param enterpriseDayRequest
     * @return
     */
    DataTransferObject<Void> changeEnterpriseDay(EnterpriseDayRequest enterpriseDayRequest);


    /**
     * 获取企业配送的信息
     * @param enterpriseCode
     * @return
     */
    DataTransferObject<List<EnterpriseListDay>> getEnterpriseListDay(String enterpriseCode);



    /**
     * 获取企业配送的信息
     * @param request
     * @return
     */
    DataTransferObject<PagingResult<EnterpriseDispatchVO>> getEnterpriseDispatchByPage(EnterprisePageRequest request);

    /**
     * 获取月内时间节点信息
     * @author afi
     * @param enterpriseCode
     */
    DataTransferObject<List<EnterpriseDayEntity>> dealEnterpriseDays(String enterpriseCode);


    /**
     * 获取企业的信息
     * @param request
     * @return
     */
    DataTransferObject<PagingResult<EnterpriseEntity>> getEnterpriseByPage(EnterprisePageRequest request);

    /**
     * 获取企业的账户统计信息
     * @param request
     * @return
     */
    DataTransferObject<PagingResult<EnterpriseAccountVO>> getEnterpriseAccountByPage(EnterprisePageRequest request);


    /**
     * 获取企业基本信息
     * @param enterpriseCode
     * @return
     */
    DataTransferObject<EnterpriseEntity> getEnterpriseByCode(String enterpriseCode);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:查询当前销售员工下维护的企业
     **/
    DataTransferObject<PagingResult<EnterpriseEntity>> pageListAndManger(EnterpriseListRequest request);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:根据enterpriseCode修改
     **/
    DataTransferObject<Void> updateEnterprise(EnterpriseEntity enterpriseEntity);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:查询企业列表
     **/
    DataTransferObject<List<EnterpriseEntity>> findAll();

    /**
     * 操作（新增、修改）企业信息
     * @param request
     * @return
     */
    DataTransferObject<Void> operateEnterprise(EnterpriseUpdateRequest request);
    
    DataTransferObject<PagingResult<EnterpriseExtVO>> getEnterpriseExtByPage(EnterpriseListRequest request);
    
    DataTransferObject<EnterpriseModel> getEnterpriseModelById(Integer id);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/31
     * @description:查询所有企业列表
     **/
    DataTransferObject<List<EnterpriseEntity>> findAllEnterprise();
}
