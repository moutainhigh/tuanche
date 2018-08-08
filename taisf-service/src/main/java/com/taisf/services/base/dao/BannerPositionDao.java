package com.taisf.services.base.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.base.dto.BannerPositionRequest;
import com.taisf.services.base.entity.BannerPositionEntity;
import com.taisf.services.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class BannerPositionDao extends BaseDao {

    private String SQLID = "base.bannerPositionDao.";

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:根据ID查询
     **/
    public BannerPositionEntity getBannerPositionById(Integer id){
        return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey",BannerPositionEntity.class,id);
    }



    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:保存
     **/
    public int saveBannerPosition(BannerPositionEntity  entity){
        if (Check.NuNObj(entity.getCreateTime())){
            entity.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", entity);
    }
    
    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:更新
     **/
    public int updateBannerPosition(BannerPositionEntity entity){
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", entity);
    }
    

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:查询所有
     **/
    public List<BannerPositionEntity> getBannerPositionList(BannerPositionRequest request){
		return mybatisDaoContext.findAll(SQLID+"getBannerPositionList",request);
	}

    /**
     * @Description 获取banner列表 分页
     * @return
     */
    public PagingResult<BannerPositionEntity> getBannerPositionForPage(BannerPositionRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID+"getBannerPositionPage", BannerPositionEntity.class, request, pageBounds);
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:根据appCode查询
     **/
    public BannerPositionEntity getByAppCodeAndPositionCode(String appCode,String positionCode){
        Map<String, Object> param = new HashMap<>(2);
        param.put("appCode",appCode);
        param.put("positionCode",positionCode);
        return mybatisDaoContext.findOne(SQLID + "getByAppCodeAndPositionCode",BannerPositionEntity.class,param);
    }



}