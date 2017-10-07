package com.taisf.services.user.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.user.vo.IndexVO;

/**
 * <p>获取首页信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/7.
 * @version 1.0
 * @since 1.0
 */
public interface IndexService {

    /**
     * 获取首页信息
     * @param userUid
     * @return
     */
    DataTransferObject<IndexVO> getIndex(String userUid);
}
