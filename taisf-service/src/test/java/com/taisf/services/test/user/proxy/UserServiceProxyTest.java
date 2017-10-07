package com.taisf.services.test.user.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.test.common.BaseTest;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dao.UserAccountDao;
import com.taisf.services.user.dto.UserLoginRequest;
import com.taisf.services.user.dto.UserLogoutRequest;
import com.taisf.services.user.dto.UserRegistRequest;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.vo.RegistInfoVO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>账户的dao测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/21.
 * @version 1.0
 * @since 1.0
 */
public class UserServiceProxyTest extends BaseTest {

    @Resource(name = "user.userServiceProxy")
    private UserService userService;


    @Test
    public void registTest() {

        UserRegistRequest userRegistRequest = new UserRegistRequest();

        Header header = new Header();
        header.setApplicationCode("user");
        header.setDeviceUuid("uid");
        header.setVersionCode("versionCode");
        userRegistRequest.setHeader(header);
        userRegistRequest.setUserPhone("123");
        userRegistRequest.setPwd("123");
        DataTransferObject<RegistInfoVO>  dto = userService.regist(userRegistRequest);

        System.out.println(JsonEntityTransform.Object2Json(dto));
    }


    @Test
    public void loginTest() {

        UserLoginRequest userRegistRequest = new UserLoginRequest();

        Header header = new Header();
        header.setApplicationCode("user");
        header.setDeviceUuid("uid");
        header.setVersionCode("versionCode");
        userRegistRequest.setHeader(header);
        userRegistRequest.setUserPhone("123");
        userRegistRequest.setPwd("123");
        DataTransferObject<String>  dto = userService.login(userRegistRequest);

        System.out.println(JsonEntityTransform.Object2Json(dto));
    }


    @Test
    public void logoutTest() {

        UserLogoutRequest userRegistRequest = new UserLogoutRequest();

        Header header = new Header();
        header.setApplicationCode("user");
        header.setDeviceUuid("uid");
        header.setVersionCode("versionCode");
        userRegistRequest.setHeader(header);
        userRegistRequest.setToken("ff8080815ef6181a015ef6181a970001");
        DataTransferObject<Void>  dto = userService.logout(userRegistRequest);

        System.out.println(JsonEntityTransform.Object2Json(dto));
    }

}
