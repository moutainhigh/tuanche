package com.jk.web.diabetes.test.permission.dao;


import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.base.utils.UUIDGenerator;
import com.jk.web.diabetes.test.BaseTest;
import com.taisf.web.oms.permission.dao.ResourceDao;
import com.taisf.web.oms.permission.dto.ResourceRequest;
import com.taisf.web.oms.permission.entity.ResourceEntity;
import com.taisf.web.oms.permission.vo.ResourceVo;
import com.taisf.web.oms.permission.vo.TreeNodeVo;

import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>资源测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/25.
 * @version 1.0
 * @since 1.0
 */
public class ResourceDaoTest extends BaseTest {

    @Resource(name = "ups.resourceDao")
    private ResourceDao resourceDao;

    private ResourceRequest resourceRequest = new ResourceRequest();

    @Test
    public void TestUpdateMenuByFid() {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setFid("8a9e9c9053698cc10153698cc14a0000");
        resourceEntity.setResName("test");
        resourceDao.updateMenuByFid(resourceEntity);
    }

    @Test
    public void findMenuOperTest() {

        resourceRequest.setLimit(5);
        resourceRequest.setPage(1);
        resourceRequest.setParent_fid("8a9e9c905368a073015368a073cb0000");

        PagingResult<ResourceEntity> list = resourceDao.findMenuOperPageList(resourceRequest);
//        System.out.println(JsonEntityTransform.Object2Json(list.getRows()));
    }


    @Test
    public void insertMenuOperTest() {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setFid(UUIDGenerator.hexUUID());
        resourceEntity.setIsLeaf(0);
        resourceEntity.setOrderCode(1);
        // menuResourceEntity.setParentFid();
        resourceEntity.setResLevel(0);
        resourceEntity.setResName("系统管理");
        resourceEntity.setResType(2);
        resourceEntity.setResUrl("http://localhost:8080/test3");
        resourceEntity.setIsDel(0);
        resourceDao.insertMenuResource(resourceEntity);

    }

    @Test
    public void findMenuTreeNodeVo() {
        List<TreeNodeVo> treeNodeVos = resourceDao.findTreeNodeVoList();
        System.out.println(JsonEntityTransform.Object2Json(treeNodeVos));
    }


    @Test
    public void findResourceVoTest(){

        List<ResourceVo> reVos=resourceDao.findResourceByCurrentuserId("8a9e9aaf537e3f7501537e3f75af0000");
        System.out.println(JsonEntityTransform.Object2Json(reVos));
    }

    
    @Test
    public void findResourceByUrlTest(){
    	
    	List<ResourceEntity> list = this.resourceDao.selectByUserId("1");
    	
    	System.out.println(list);
    }
    

}
