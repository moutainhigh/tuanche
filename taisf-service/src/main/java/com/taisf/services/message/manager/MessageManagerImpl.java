package com.taisf.services.message.manager;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.taisf.services.message.dao.MessageDao;
import com.taisf.services.message.dto.DateRequest;
import com.taisf.services.message.dto.MessageRequest;
import com.taisf.services.message.dto.MessageResponse;
import com.taisf.services.message.dto.MsgReq;
import com.taisf.services.message.entity.MessageEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("message.messageManagerImpl")
public class MessageManagerImpl {
	
	private static final int MSG_DATE_LIMIT = 7;

    @Resource(name = "message.messageDao")
    private MessageDao messageDao;

    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:消息列表分页查询
     **/
    public PagingResult<MessageEntity> findMessagePageList(MessageRequest request) {
        return messageDao.findMessagePageList(request);
    }
    /**
     * @author:zhangzhengguang
     * @date:2017/11/7
     * @description:定时任务,查询发送消息列表
     **/
    public List<MessageEntity> findMessageList() {
        return messageDao.findMessageList();
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:新增保存消息
     **/
    public void saveMessage(MessageEntity entity) {
        messageDao.saveMessage(entity);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/11/7
     * @description:修改消息根据主键
     **/
    public int updateMessageById(MessageEntity entity) {
        if (Check.NuNObj(entity)) {
            return 0;
        }
        return messageDao.updateMessageById(entity);
    }

	/**
	 * @Description 消息列表查询
	 * @param request
	 * @return
	 */
	public MessageResponse listMsg(MsgReq request) {
		Date lastTime = request.getLastTime();
		if(Check.NuNObj(lastTime)) {
			lastTime = getPastDate(MSG_DATE_LIMIT);
		}
		Date date = new Date();
		request.setNow(date);
		request.setLastTime(lastTime);
		List<MessageEntity> list = messageDao.getMsgList(request);
		MessageResponse response = new MessageResponse();
		response.setList(list);
		response.setSystemTime(date);
		return response;
	}
	
	private Date getPastDate(int past) {
	     Calendar calendar = Calendar.getInstance();  
	     calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
	     return calendar.getTime();
	 }


	public long getMessageByDate(DateRequest request){
		if(Check.NuNObj(request)){
			return 0;
		}
		return messageDao.getMessageByDate(request);
	}


}
