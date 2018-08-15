
package com.taisf.services.message.api;


import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.message.dto.DateRequest;
import com.taisf.services.message.dto.MessageRequest;
import com.taisf.services.message.dto.MessageResponse;
import com.taisf.services.message.dto.MsgReq;
import com.taisf.services.message.entity.MessageEntity;
import com.taisf.services.message.vo.RemindTimeVo;

import java.util.List;

public interface MessageService {
	/**
	 * @author:zhangzhengguang
	 * @date:2017/9/4
	 * @description:知识列表分页查询
	 **/
	DataTransferObject<PagingResult<MessageEntity>> findMessagePageList(MessageRequest request);
	/**
	 * @author:zhangzhengguang
	 * @date:2017/9/4
	 * @description:新增保存知识
	 **/
	DataTransferObject<MessageEntity> saveMessage(MessageEntity entity);

	/**
	 * @author:zhangzhengguang
	 * @date:2017/11/7
	 * @description:定时任务,查询发送消息列表
	 **/
	DataTransferObject<List<MessageEntity>> findMessageList();

	/**
	 * @author:zhangzhengguang
	 * @date:2017/11/7
	 * @description:批量修改消息
	 **/
	DataTransferObject<Void> updateBatchMessage(List<MessageEntity> msgList);

	
	/**
	 * @Description 消息列表查询 
	 * @param entity
	 * @return
	 */
	DataTransferObject<MessageResponse> listMsg(MsgReq entity);



	DataTransferObject<RemindTimeVo> getMessageByDate(DateRequest request);

}

