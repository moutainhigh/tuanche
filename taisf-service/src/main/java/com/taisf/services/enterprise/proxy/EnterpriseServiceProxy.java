package com.taisf.services.enterprise.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.enterprise.api.EnterpriseService;
import com.taisf.services.enterprise.dao.EnterpriseDao;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.dto.EnterprisePageRequest;
import com.taisf.services.enterprise.dto.EnterpriseUpdateRequest;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.enterprise.vo.EnterpriseAccountVO;
import com.taisf.services.enterprise.vo.EnterpriseExtVO;
import com.taisf.services.product.proxy.ProductServiceProxy;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.manager.UserManagerImpl;

/**
 * <p>企业接口实现</p>
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
@Component("enterprise.enterpriseServiceProxy")
public class EnterpriseServiceProxy implements EnterpriseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseServiceProxy.class);

    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;



    /**
     * 获取企业的账户统计信息
     * @param request
     * @return
     */
    @Override
    public DataTransferObject<PagingResult<EnterpriseAccountVO>> getEnterpriseAccountByPage(EnterprisePageRequest request){

        DataTransferObject<PagingResult<EnterpriseAccountVO>> dto = new DataTransferObject<>();

        if (Check.NuNObj(request)){
            request = new EnterprisePageRequest();
        }
        PagingResult<EnterpriseEntity> pagingResult =enterpriseManager.getEnterpriseByPage(request);
        //创建新的分页试图
        PagingResult<EnterpriseAccountVO> page = new PagingResult<>(pagingResult.getTotal(),transBase2Account(pagingResult.getList()));
        dto.setData(page);
        return dto;
    }

    /**
     * 将企业基本对象转化成企业的账户统计信息
     * @author afi
     * @param list
     * @return
     */
    private List<EnterpriseAccountVO> transBase2Account(List<EnterpriseEntity> list){
        List<EnterpriseAccountVO> listAccount = new ArrayList<>();
        if (Check.NuNCollection(list)){
            return listAccount;
        }
        List<String> listPar =new ArrayList<>();
        for (EnterpriseEntity entity : list) {
            listPar.add(entity.getEnterpriseCode());
        }
        //获取企业统计
        Map<String, EnterpriseAccountVO> codeMap = userManager.getEnterpriseAccountMapByList(listPar);

        //获取账户信息
        Map<String, UserAccountEntity> userMap = userManager.getUserAccountMapByList(listPar);
        for (EnterpriseEntity entity : list) {
            String key = entity.getEnterpriseCode();
            EnterpriseAccountVO vo;
            if (codeMap.containsKey(key)){
                vo = codeMap.get(key);
            }else {
                vo = new EnterpriseAccountVO();
                vo.setEnterpriseCode(key);
            }
            vo.setEnterpriseName(entity.getEnterpriseName());

            UserAccountEntity user = null;
            if (userMap.containsKey(key)){
                user = userMap.get(key);
            }
            if (!Check.NuNObj(user)){
                vo.setDrawBalance(user.getDrawBalance());
            }
            listAccount.add(vo);
        }
        return listAccount;
    }

    /**
     * 获取企业基本信息
     *
     * @param enterpriseCode
     * @return
     */
    @Override
    public DataTransferObject<EnterpriseEntity> getEnterpriseByCode(String enterpriseCode) {
        DataTransferObject<EnterpriseEntity> dto = new DataTransferObject<>();

        if (Check.NuNStr(enterpriseCode)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        EnterpriseEntity entity = enterpriseManager.getEnterpriseByCode(enterpriseCode);
        if (entity == null) {
            dto.setErrorMsg("当前企业不存在");
        }
        dto.setData(entity);
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:查询当前销售员工下维护的企业
     **/
    @Override
    public DataTransferObject<PagingResult<EnterpriseEntity>> pageListAndManger(EnterpriseListRequest request) {
        DataTransferObject<PagingResult<EnterpriseEntity>> dto = new DataTransferObject<>();
        if (Check.NuNStr(request.getManger())) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        PagingResult<EnterpriseEntity> pagingResult = enterpriseDao.pageListAndManger(request);
        dto.setData(pagingResult);
        return dto;
    }
    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:根据enterpriseCode修改
     **/
    @Override
    public DataTransferObject<Void> updateEnterprise(EnterpriseEntity enterpriseEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(enterpriseEntity)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        enterpriseDao.updateEnterprise(enterpriseEntity);
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/18
     * @description:查询企业列表
     **/
    @Override
    public DataTransferObject<List<EnterpriseEntity>> findAll() {
        DataTransferObject<List<EnterpriseEntity>> dto = new DataTransferObject<>();
        List<EnterpriseEntity> entityList = enterpriseDao.findAll();
        dto.setData(entityList);
        return dto;
    }

	@Override
	public DataTransferObject<Void> operateEnterprise(EnterpriseUpdateRequest request) {
		DataTransferObject<Void> dto = new DataTransferObject<>();
        try {
            //配置信息
        	EnterpriseConfigEntity configEntity = new EnterpriseConfigEntity();
            BeanUtils.copyProperties(request,configEntity);
           // enterpriseManager.insertProduct(product);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【企业】error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("操作企业信息异常");
            return dto;
        }
        return dto;
	}
	
	/**
	 * 查询企业信息列表
	 */
	@Override
	public DataTransferObject<PagingResult<EnterpriseExtVO>> getEnterpriseExtByPage(EnterpriseListRequest request) {
		DataTransferObject<PagingResult<EnterpriseExtVO>> dto = new DataTransferObject<PagingResult<EnterpriseExtVO>>();
		try {
			PagingResult<EnterpriseExtVO> vos = enterpriseManager.getEnterpriseExtByPage(request);
			dto.setData(vos);
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);            
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
            return dto;
		}		
		return dto;
	}	

}
