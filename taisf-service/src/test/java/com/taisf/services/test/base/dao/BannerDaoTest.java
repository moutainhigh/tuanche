package com.taisf.services.test.base.dao;


import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.base.dao.BannerDao;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/13.
 * @version 1.0
 * @since 1.0
 */
public class BannerDaoTest extends BaseTest {


   @Autowired
   private BannerDao bannerDao;


    @Test
    public void testGetBannerForPage() {
    	BannerReq request = new BannerReq();
    	request.setPage(1);
    	request.setLimit(3);
    	request.setAppCode("jkHospital");
		PagingResult<BannerEntity> bannerForPage = bannerDao.getBannerForPage(request );
		System.out.println(JsonEntityTransform.Object2Json(bannerForPage));
    }
    

}
