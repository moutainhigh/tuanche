package com.taisf.services.message.dao;


import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.message.dto.DateRequest;
import com.taisf.services.message.dto.MessageRequest;
import com.taisf.services.message.dto.MsgReq;
import com.taisf.services.message.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("message.messageDao")
public class MessageDao extends BaseDao {

    private String SQLID = "message.messageDao.";

    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:新增保存消息
     **/
    public int saveMessage(MessageEntity entity) {
        if (Check.NuNObj(entity)) {
            return 0;
        }
        return mybatisDaoContext.save(SQLID + "insertSelective", entity);
    }
    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:消息列表分页查询
     **/
    public PagingResult<MessageEntity> findMessagePageList(MessageRequest MessageRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(MessageRequest.getLimit());
        pageBounds.setPage(MessageRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "findMessagePageList", MessageEntity.class, MessageRequest, pageBounds);
    }
    /**
     * @author:zhangzhengguang
     * @date:2017/11/7
     * @description:定时任务,查询发送消息列表
     **/
    public List<MessageEntity> findMessageList() {
        return mybatisDaoContext.findAll(SQLID + "findMessageList");
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:查询消息根据ID
     **/
    public MessageEntity getMessage(Integer id) {
        if (Check.NuNObj(id)) {
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "selectByPrimaryKey", MessageEntity.class, id);
    }



    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:修改消息根据主键
     **/
    public int updateMessageById(MessageEntity entity) {
        if (Check.NuNObj(entity)) {
            return 0;
        }
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", entity);
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:删除消息根据主键
     **/
    public int deleteMessageById(Integer id) {
        if (Check.NuNObj(id)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return mybatisDaoContext.delete(SQLID + "deleteByPrimaryKey", map);
    }
	/**
	 * @Description 消息列表查询
	 * @param request
	 * @return
	 */
	public List<MessageEntity> getMsgList(MsgReq request) {
		return mybatisDaoContext.findAll(SQLID + "getMsgList",request);
	}

    /**
     * 获取消息
     * @param request
     * @return
     */
    public long getMessageByDate(DateRequest request){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("msgType",request.getMsgType());
        params.put("lastTime",request.getLastTime());
        return mybatisDaoContext.count(SQLID+"getMessageByDate",params);
    }
}
