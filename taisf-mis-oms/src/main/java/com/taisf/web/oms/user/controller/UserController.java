package com.taisf.web.oms.user.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.user.dto.UserRequest;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.web.oms.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("user/")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:销售管理列表
     **/
    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        return "user/userList";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:销售管理列表数据
     **/
    @RequestMapping("pageList")
    @ResponseBody
    public PageResult pageList(HttpServletRequest request, UserRequest userRequest) {
        DataTransferObject<PagingResult<UserEntity>> dto = new DataTransferObject<>();
        PageResult pageResult = new PageResult();
        try {
            //todo 分页条件查询
            UserEntity entity = new UserEntity();
            entity.setUserUid("123");
            entity.setCreateTime(new Date());
            entity.setUserName("小明");
            entity.setUserPhone("110");
            entity.setUserStatus(1);
            List<UserEntity> list = new ArrayList<>();
            list.add(entity);
            pageResult.setRows(list);
            pageResult.setTotal(1L);
            System.out.println("分页条件查询");
        } catch (Exception e) {
            LogUtil.info(LOGGER,"params:{}", JsonEntityTransform.Object2Json(userRequest));
            LogUtil.error(LOGGER,"error:{}",e);
            return  new PageResult();
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:新增销售员工
     **/
    @RequestMapping("addUser")
    @ResponseBody
    public DataTransferObject<Void> addUser(HttpServletRequest request, UserEntity userEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(userEntity)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            //todo 执行保存
            System.out.println(userEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统错误");
            return dto;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:编辑销售员工
     **/
    @RequestMapping("toedit")
    @ResponseBody
    public DataTransferObject<UserEntity> toedit(HttpServletRequest request, String userUid) {
        DataTransferObject<UserEntity> dto = new DataTransferObject<>();
        if (Check.NuNObj(userUid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            //todo 根据userUid查询用户信息
            System.out.println(userUid);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName("小明");
            userEntity.setUserPhone("151200957278");
            userEntity.setUserUid("110");
            dto.setData(userEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统错误");
            return dto;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:编辑保存销售员工
     **/
    @RequestMapping("editUser")
    @ResponseBody
    public DataTransferObject<Void> editUser(HttpServletRequest request, UserEntity userEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(userEntity)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            //todo 执行修改

            System.out.println(userEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统错误");
            return dto;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:离职
     **/
    @RequestMapping("dimission")
    @ResponseBody
    public DataTransferObject<Void> dimission(HttpServletRequest request, String userUid) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(userUid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            //todo
            //1.根据userUid查询是否有维护信息  如果有直接return ,提示信息: 请先将该员工下维护信息做变更后方可做离职变更
            //2.如果无维护信息 修改状态
            //3.离职后账号自动解除
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统错误");
            return dto;
        }
        return dto;
    }
}
