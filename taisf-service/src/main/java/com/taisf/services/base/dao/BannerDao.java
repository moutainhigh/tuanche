package com.taisf.services.base.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.base.vo.BannerVo;
import com.taisf.services.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BannerDao
 * @Description bannner dao
 * @author rxg
 * @Date 2017年8月25日 上午11:23:34
 * @version 1.0.0
 */
@Repository
public class BannerDao extends BaseDao {

    private String SQLID = "base.bannerDao.";
    
    /**
     * @Description 根据id获取banner
     * @param id
     * @return
     */
    public BannerEntity getBannerById(Integer id){
        return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey",BannerEntity.class,id);
    }



    /**
     * @Description 保存banner
     * @param bannerEntity
     * @return
     */
    public int saveBanner(BannerEntity  bannerEntity){
        if (Check.NuNObj(bannerEntity.getCreateTime())){
        	bannerEntity.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", bannerEntity);
    }
    
    /**
     * @Description 更新banner
     * @param entity
     * @return
     */
    public int updateBanner(BannerEntity entity){
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", entity);
    }
    
    /**
     * @Description 获取banner列表
     * @return
     */
    public List<BannerEntity> getBannerList(BannerReq request){
		return mybatisDaoContext.findAll(SQLID+"getBannerList",request);
	}

    /**
     * @Description 获取banner列表 分页
     * @return
     */
    public PagingResult<BannerEntity> getBannerForPage(BannerReq request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID+"getBannerPage", BannerEntity.class, request, pageBounds);
    }

    /**
     * 上架或者下架 修改状态
     * @param id
     * @param oldStatus
     * @param newStatus
     * @return
     */
    public int changeStatus(Integer id, Integer oldStatus, Integer newStatus){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("oldStatus", oldStatus);
        map.put("newStatus", newStatus);

        return mybatisDaoContext.update(SQLID + "changeStatus", map);
    }

    /**
     * @Description 根据id和状态获取banner
     * @param id
     * @return
     */
    public BannerEntity getBannerByIdAndStatus(Integer id, Integer oldStatus){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("oldStatus", oldStatus);

        return mybatisDaoContext.findOne(SQLID + "getBannerByIdAndStatus",BannerEntity.class,map);
    }



	public List<BannerVo> listBanner(BannerReq request) {
		return mybatisDaoContext.findAll(SQLID+"listBanner",request);
	}

}