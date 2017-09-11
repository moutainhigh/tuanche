package com.taisf.services.base.dao;

import com.taisf.services.base.entity.AreaRegionEntity;
import com.taisf.services.common.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p> 地区主表操作 </p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.areaRegionDao")
public class AreaRegionDao extends BaseDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(AreaRegionDao.class);

    private String SQLID = "basedata.areaRegionDao.";

    
    
    /**
     * 根据praentCode查询子级数据
     * @param entity
     * @return
     */
    public List<AreaRegionEntity> getAreaRegionList(AreaRegionEntity entity){
        return mybatisDaoContext.findAll(SQLID + "queryListSelective", entity);
    }


    /**
     * 获取当前城市信息
     * @param cityName
     * @return
     */
    public AreaRegionEntity getAreaRegionByName(String cityName){
        return mybatisDaoContext.findOne(SQLID + "getAreaRegionByName",AreaRegionEntity.class,cityName);
    }

}
