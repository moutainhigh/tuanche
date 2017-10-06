package com.taisf.services.enterprise.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.enterprise.entity.EnterpriseEntity;

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
     * 获取企业基本信息
     * @param enterpriseCode
     * @return
     */
    DataTransferObject<EnterpriseEntity> getEnterpriseByCode(String enterpriseCode);
}
