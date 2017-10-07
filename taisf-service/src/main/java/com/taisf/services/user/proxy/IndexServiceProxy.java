package com.taisf.services.user.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.UUIDGenerator;
import com.taisf.services.common.valenum.ApplicationCodeEnum;
import com.taisf.services.common.valenum.EnterpriseStatusEnum;
import com.taisf.services.common.valenum.UserRoleEnum;
import com.taisf.services.common.valenum.UserStatusEnum;
import com.taisf.services.enterprise.entity.EnterpriseAddressEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.supplier.proxy.SupplierProductServiceProxy;
import com.taisf.services.user.api.IndexService;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dto.UserLoginRequest;
import com.taisf.services.user.dto.UserLogoutRequest;
import com.taisf.services.user.dto.UserRegistRequest;
import com.taisf.services.user.entity.LoginTokenEntity;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import com.taisf.services.user.vo.IndexVO;
import com.taisf.services.user.vo.RegistInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>首页接口实现</p>
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
@Component("user.indexServiceProxy")
public class IndexServiceProxy implements IndexService {


    private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceProxy.class);

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;


    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;


    /**
     * 获取首页信息
     * @param userUid
     * @return
     */
    @Override
    public DataTransferObject<IndexVO> getIndex(String userUid) {

        DataTransferObject<IndexVO> dto = new DataTransferObject<>();
        if (Check.NuNObj(userUid)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        return dto;
    }

}
