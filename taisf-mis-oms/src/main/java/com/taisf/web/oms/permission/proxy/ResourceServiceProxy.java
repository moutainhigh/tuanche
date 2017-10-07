
package com.taisf.web.oms.permission.proxy;


import com.alibaba.fastjson.JSON;
import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.cache.redis.api.RedisOperations;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.web.oms.permission.api.ResourceService;
import com.taisf.web.oms.permission.dto.EmployeeRequest;
import com.taisf.web.oms.permission.dto.ResourceRequest;
import com.taisf.web.oms.permission.entity.EmployeeEntity;
import com.taisf.web.oms.permission.entity.ResourceEntity;
import com.taisf.web.oms.permission.service.EmployeeServiceImpl;
import com.taisf.web.oms.permission.service.ResourceServiceImpl;
import com.taisf.web.oms.permission.vo.TreeNodeVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台菜单业务代理层
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Component("ups.resourceServiceProxy")
public class ResourceServiceProxy implements ResourceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceProxy.class);

	@Resource(name = "ups.resourceServiceImpl")
	private ResourceServiceImpl resourceServiceImpl;

	@Resource(name="ups.employeeServiceImpl")
	private EmployeeServiceImpl employeeService;
//	@Resource(name = "ups.messageSource")
//	private MessageSource messageSource;
	@Autowired
	private RedisOperations redis;
	/**
	 * 更新菜单信息
	 * 
	 * @param paramJson
	 * @return
	 */
	public String updateMenuByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ResourceEntity menu = JsonEntityTransform.json2Object(paramJson, ResourceEntity.class);
			// 直接更新菜单信息
			resourceServiceImpl.updateMenuByFid(menu);
			List<EmployeeEntity> list = employeeService.findEmployeeByCondition(new EmployeeRequest());
			for (EmployeeEntity Entity : list) {
				if(redis.exists(Entity.getUserId())){
					redis.del(Entity.getUserId());
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto.toJsonString();
	}


	@Override
	public DataTransferObject<PagingResult<ResourceEntity>> searchMenuResList(String paramJson) {
		DataTransferObject<PagingResult<ResourceEntity>> dto = new DataTransferObject<PagingResult<ResourceEntity>>();
		try {
			ResourceRequest menuOperRequest = JsonEntityTransform.json2Object(paramJson, ResourceRequest.class);
			// 条件查询后台用户
			PagingResult<ResourceEntity> pr = resourceServiceImpl.findMenuResList(menuOperRequest);
			dto.setData(pr);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");

		}
		return dto;
	}


	@Override
	public void insertMenuResource(String paramJson) {
		try {
			ResourceEntity menuResourceEntity = JsonEntityTransform.json2Object(paramJson, ResourceEntity.class);
			resourceServiceImpl.saveMenuResouce(menuResourceEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.MenuOperService#
	 * searchAllMenuChildResList()
	 */
	@Override
	public String searchAllMenuChildResList() {
		DataTransferObject<List<ResourceEntity>> dto = new DataTransferObject<List<ResourceEntity>>();
		try {
			List<ResourceEntity> resList = resourceServiceImpl.findAllMenuChildList();
			dto.setData(resList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto.toJsonString();
	}


	@Override
	public String menuTreeVo() {
		DataTransferObject<List<TreeNodeVo>> dto = new DataTransferObject<List<TreeNodeVo>>();
		try {
			List<TreeNodeVo> menuTreeNodeVos = resourceServiceImpl.findMenuTreeNodeVos();
			dto.setData(menuTreeNodeVos);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto.toJsonString();
	}
	/**
	 * 根据用户id查询  用户资源权限
	 * @return
	 */
	public String selectByUserId(String userId){
        String jsonString = redis.get(userId);
	    try{
		if(Check.NuNStr(jsonString)){
			List<ResourceEntity> resourceList = resourceServiceImpl.selectByUserId(userId);
			jsonString = JSON.toJSONString(resourceList);
			redis.setex(userId, 86400, jsonString);
			return jsonString;
		}
        }catch (Exception e){
            LogUtil.error(LOGGER, "【获取用户资源权限错误】param:{}", userId);
            LogUtil.error(LOGGER, "error:{}", e);
        }
		return jsonString;
	}
	/**
	 * @author:zhangzhengguang
	 * @date:2017/7/26
	 * @description:首页加载当前用户拥有的菜单权限
	 **/
	public List<TreeNodeVo> findResourceByCurrentUserId(String currentuserId){
		List<TreeNodeVo> treeNodeVos = null;
		try{
			treeNodeVos = resourceServiceImpl.findResourceByCurrentUserId(currentuserId);
		}catch (Exception e){
			LogUtil.error(LOGGER, "【首页加载当前用户拥有的菜单权限错误】param:{}", currentuserId);
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return treeNodeVos;
	}
}
