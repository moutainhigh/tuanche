package com.taisf.services.stock.manager;

import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.MD5Util;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.common.valenum.AccountStatusEnum;
import com.taisf.services.common.valenum.UserTypeEnum;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.vo.EnterpriseAccountVO;
import com.taisf.services.stock.dao.StockProductDao;
import com.taisf.services.stock.entity.StockProductEntity;
import com.taisf.services.ups.dao.EmployeeDao;
import com.taisf.services.user.dao.*;
import com.taisf.services.user.dto.AccountLogRequest;
import com.taisf.services.user.dto.UserAccountRequest;
import com.taisf.services.user.entity.*;
import com.taisf.services.user.vo.UserAccountVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>用户信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/29.
 * @version 1.0
 * @since 1.0
 */
@Service("stock.stockProductManagerImpl")
public class StockProductManagerImpl {


    @Resource(name="stock.stockProductDao")
    private StockProductDao stockProductDao;



    /**
     * 获取当前药品的库存限制情况
     * @param week
     * @param supplierProductType
     * @param orderType
     * @param supplierCode
     * @param productIds
     * @return
     */
    public Map<String,StockProductEntity> getStockLimitMap(Integer week, Integer supplierProductType, Integer orderType, String supplierCode, List productIds) {
        Map<String,StockProductEntity> map = new HashMap<>();
        List<StockProductEntity> stockList =  getStockLimitByList(week,supplierProductType,orderType,supplierCode,productIds);
        if (!Check.NuNCollection(stockList)){
            for (StockProductEntity stockProductEntity : stockList) {
                map.put(ValueUtil.getStrValue(stockProductEntity.getProductCode()),stockProductEntity);
            }
        }
        return map;
    }




    /**
     * 获取当前药品的库存限制情况
     * @param week
     * @param supplierProductType
     * @param orderType
     * @param supplierCode
     * @param productIds
     * @return
     */
    public List<StockProductEntity> getStockLimitByList(Integer week, Integer supplierProductType, Integer orderType, String supplierCode, List productIds) {
        List<StockProductEntity> list = new ArrayList<>();
        if (Check.NuNCollection(productIds)){
            return list;
        }
        if (Check.NuNObjs(week,supplierProductType,orderType,supplierCode)){
            return list;
        }
        return stockProductDao.getStockLimitByList(week,supplierProductType,orderType,supplierCode,productIds);
    }

    /**
     * 保存库存限制信息
     * @author afi
     * @param record
     * @return
     */
    public int saveStockProduct(StockProductEntity record){
        if (Check.NuNObj(record)){
            return 0;
        }
        return stockProductDao.saveStockProduct(record);
    }


}
