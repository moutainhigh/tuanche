package com.taisf.services.enterprise.proxy;

import com.jk.framework.base.constant.YesNoEnum;
import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.enterprise.api.EnterpriseService;
import com.taisf.services.enterprise.dto.EnterpriseDayRequest;
import com.taisf.services.enterprise.dto.EnterprisePageRequest;
import com.taisf.services.enterprise.dao.EnterpriseDao;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.entity.EnterpriseDayEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.enterprise.vo.EnterpriseAccountVO;
import com.taisf.services.enterprise.vo.EnterpriseDayVO;
import com.taisf.services.enterprise.vo.EnterpriseDispatchVO;
import com.taisf.services.enterprise.vo.EnterpriseListDay;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;


    /**
     * 改变配送信息
     * @param enterpriseDayRequest
     * @return
     */
    @Override
    public DataTransferObject<Void> changeEnterpriseDay(EnterpriseDayRequest enterpriseDayRequest){
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(enterpriseDayRequest)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNObj(enterpriseDayRequest.getDayTime())) {
            dto.setErrorMsg("请确认时间");
            return dto;
        }

        if (Check.NuNObj(enterpriseDayRequest.getDayType())) {
            dto.setErrorMsg("类型");
            return dto;
        }
        if (Check.NuNStr(enterpriseDayRequest.getEnterpriseCode())) {
            dto.setErrorMsg("企业code为空");
            return dto;
        }
        enterpriseDayRequest.setDayTimeStr(DateUtil.intFormat(enterpriseDayRequest.getDayTime())+"");
        boolean delFlag;
        if (DateUtil.isWorkDay(enterpriseDayRequest.getDayTime())){
            //工作日
            if (enterpriseDayRequest.getDayType() == YesNoEnum.YES.getCode()){
                delFlag = true;
            }else {
                delFlag = false;
            }
        }else {
            //非工作日
            if (enterpriseDayRequest.getDayType() == YesNoEnum.YES.getCode()){
                delFlag = false;
            }else {
                delFlag = true;
            }
        }
        if (delFlag){
            enterpriseManager.delEnterpriseDays(enterpriseDayRequest);
        }else {
            EnterpriseDayEntity has =enterpriseManager.getEnterpriseDays(enterpriseDayRequest);
            if (Check.NuNObj(has)){
                EnterpriseDayEntity record = new EnterpriseDayEntity();
                record.setEnterpriseCode(enterpriseDayRequest.getEnterpriseCode());
                record.setDayTime(enterpriseDayRequest.getDayTimeStr());
                record.setDayType(enterpriseDayRequest.getDayType());
                record.setOpCode(enterpriseDayRequest.getOpCode());
                record.setOpName(enterpriseDayRequest.getOpName());
                enterpriseManager.saveEnterpriseDay(record);
            }
        }
        return dto;
    }

    /**
     * 获取企业配送的信息
     * @param enterpriseCode
     * @return
     */
    @Override
    public DataTransferObject<List<EnterpriseListDay>> getEnterpriseListDay(String enterpriseCode){
        DataTransferObject<List<EnterpriseListDay>> dto = new DataTransferObject<>();
        if (Check.NuNStr(enterpriseCode)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        List<EnterpriseDayEntity> list  = enterpriseManager.dealEnterpriseDays(enterpriseCode);
        if (Check.NuNCollection(list)){
            dto.setErrorMsg("异常的配送信息");
            return dto;
        }
        if (list.size() != 42){
            dto.setErrorMsg("异常的配送信息");
            return dto;
        }
        Integer  now = DateUtil.intFormat(new Date());
        List<EnterpriseListDay> listDayList= new ArrayList<>();
        Map<Integer,EnterpriseListDay>  has = new HashMap<>();
        int key= 0;
        for (int k = 0; k < list.size(); k++) {
            EnterpriseDayEntity ele = list.get(k);
            EnterpriseDayVO vo = new EnterpriseDayVO();
            BeanUtils.copyProperties(ele,vo);
            if (ValueUtil.getintValue(ele.getDayTime()) >= now){
                vo.setChangeFlag(true);
            }
            int last = k%7;
            if (last == 0){
                key ++;
                EnterpriseListDay weekList = new EnterpriseListDay();
                weekList.getList().add(vo);
                has.put(key,weekList);
                listDayList.add(weekList);
            }else {
                EnterpriseListDay weekList = has.get(key);
                weekList.getList().add(vo);
            }
        }
        dto.setData(listDayList);
        return dto;
    }

    /**
     * 获取企业配送的信息
     * @param request
     * @return
     */
    @Override
    public DataTransferObject<PagingResult<EnterpriseDispatchVO>> getEnterpriseDispatchByPage(EnterprisePageRequest request){
        DataTransferObject<PagingResult<EnterpriseDispatchVO>> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)){
            request = new EnterprisePageRequest();
        }
        PagingResult<EnterpriseEntity> pagingResult =enterpriseManager.getEnterpriseByPage(request);
        PagingResult<EnterpriseDispatchVO> paging = new PagingResult(pagingResult.getTotal(),trans2Dispatch(pagingResult.getList()));
        dto.setData(paging);
        return dto;
    }

    /**
     * 转化当前对象信息
     * @param list
     * @return
     */
    private List<EnterpriseDispatchVO>  trans2Dispatch(List<EnterpriseEntity> list){
        List<EnterpriseDispatchVO> volist = new ArrayList<>();
        if (Check.NuNCollection(list)){
            return volist;
        }
        for (EnterpriseEntity entity : list) {
            EnterpriseDispatchVO vo = new EnterpriseDispatchVO();
            BeanUtils.copyProperties(entity,vo);
            fillEnterpriseDispatchVO(vo);
            volist.add(vo);
        }
        return volist;
    }

    /**
     * 填充当前的配送信息
     * @param vo
     */
    private void fillEnterpriseDispatchVO(EnterpriseDispatchVO vo){
        if (Check.NuNObj(vo)){
            return;
        }
        //获取当前的配送信息
        Map<String,EnterpriseDayEntity> map = enterpriseManager.dealEnterpriseMapDays(vo.getEnterpriseCode());
        Date first = DateUtil.getFirstDayOfWeekDay(new Date());
        vo.setDay1(map.get(DateUtil.intFormat(first)+""));
        first = DateUtil.jumpDate(first,1);
        vo.setDay2(map.get(DateUtil.intFormat(first)+""));
        first = DateUtil.jumpDate(first,1);
        vo.setDay3(map.get(DateUtil.intFormat(first)+""));
        first = DateUtil.jumpDate(first,1);
        vo.setDay4(map.get(DateUtil.intFormat(first)+""));
        first = DateUtil.jumpDate(first,1);
        vo.setDay5(map.get(DateUtil.intFormat(first)+""));
        first = DateUtil.jumpDate(first,1);
        vo.setDay6(map.get(DateUtil.intFormat(first)+""));
        first = DateUtil.jumpDate(first,1);
        vo.setDay7(map.get(DateUtil.intFormat(first)+""));
    }


    /**
     * 获取月内时间节点信息
     * @author afi
     * @param enterpriseCode
     */
    public DataTransferObject<List<EnterpriseDayEntity>> dealEnterpriseDays(String enterpriseCode){
        DataTransferObject<List<EnterpriseDayEntity>> dto = new DataTransferObject<>();
        if (Check.NuNStr(enterpriseCode)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        List<EnterpriseDayEntity> list  = enterpriseManager.dealEnterpriseDays(enterpriseCode);
        dto.setData(list);
        return dto;
    }

    /**
     * 获取企业的信息
     * @param request
     * @return
     */
    @Override
    public DataTransferObject<PagingResult<EnterpriseEntity>> getEnterpriseByPage(EnterprisePageRequest request){

        DataTransferObject<PagingResult<EnterpriseEntity>> dto = new DataTransferObject<>();

        if (Check.NuNObj(request)){
            request = new EnterprisePageRequest();
        }
        PagingResult<EnterpriseEntity> pagingResult =enterpriseManager.getEnterpriseByPage(request);
        dto.setData(pagingResult);
        return dto;
    }



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

}
