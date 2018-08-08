package com.taisf.services.base.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.base.dto.BannerAppRequest;
import com.taisf.services.base.entity.BannerAppEntity;
import com.taisf.services.base.vo.BannerAppTreeNodeVO;
import com.taisf.services.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class BannerAppDao extends BaseDao {

    private String SQLID = "base.bannerAppDao.";

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:根据ID查询
     **/
    public BannerAppEntity getBannerAppById(Integer id){
        return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey",BannerAppEntity.class,id);
    }



    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:保存
     **/
    public int saveBannerApp(BannerAppEntity  entity){
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
    public int updateBannerApp(BannerAppEntity entity){
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", entity);
    }
    

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:查询所有
     **/
    public List<BannerAppTreeNodeVO> getBannerAppList(BannerAppRequest request){
		return mybatisDaoContext.findAll(SQLID+"getBannerAppList",request);
	}

    /**
     * @Description 获取banner列表 分页
     * @return
     */
    public PagingResult<BannerAppEntity> getBannerAppForPage(BannerAppRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID+"getBannerPage", BannerAppEntity.class, request, pageBounds);
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:根据appCode查询
     **/
    public BannerAppEntity getBannerAppByAppCode(String appCode){
        return mybatisDaoContext.findOne(SQLID + "getBannerAppByAppCode",BannerAppEntity.class,appCode);
    }

}