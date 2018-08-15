
package com.taisf.services.message.proxy;


import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.log.utils.LogUtil;

import com.taisf.services.message.api.MessageService;
import com.taisf.services.message.dto.DateRequest;
import com.taisf.services.message.dto.MessageRequest;
import com.taisf.services.message.dto.MessageResponse;
import com.taisf.services.message.dto.MsgReq;
import com.taisf.services.message.entity.MessageEntity;
import com.taisf.services.message.manager.MessageManagerImpl;
import com.taisf.services.message.vo.RemindTimeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component("message.messageServiceProxy")
public class MessageServiceProxy implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceProxy.class);


    @Resource(name = "message.messageManagerImpl")
    private MessageManagerImpl messageService;


    /**
     * @author:zhangzhengguang
     * @date:2017/11/7
     * @description:定时任务,查询发送消息列表
     **/
    @Override
    public DataTransferObject<List<MessageEntity>> findMessageList() {
        DataTransferObject<List<MessageEntity>> dto = new DataTransferObject<>();
        try {
            List<MessageEntity> messageList = messageService.findMessageList();
            dto.setData(messageList);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "定时任务,查询发送消息列表失败:error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("定时任务,查询发送消息列表失败");
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:消息列表分页查询
     **/
    @Override
    public DataTransferObject<PagingResult<MessageEntity>> findMessagePageList(MessageRequest request) {
        DataTransferObject<PagingResult<MessageEntity>> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        try {
            PagingResult<MessageEntity> MessagePageList = messageService.findMessagePageList(request);
            dto.setData(MessagePageList);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "消息列表分页查询失败:error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("消息列表分页查询失败");
        }
        return dto;
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/9/7
     * @description:新增保存消息
     **/
    @Override
    public DataTransferObject<MessageEntity> saveMessage(MessageEntity req) {
        DataTransferObject<MessageEntity> dto = new DataTransferObject<>();
        if (Check.NuNObj(req)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        try {
            if (Check.NuNObj(req.getSendTime())){
                req.setSendTime(new Date());
            }
            messageService.saveMessage(req);
            dto.setData(req);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "新增保存消失败:error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("新增保存消息失败");
        }
        return dto;
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/11/7
     * @description:批量修改消息
     **/
    @Override
    public DataTransferObject<Void> updateBatchMessage(List<MessageEntity> msgList) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(msgList)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        try {
            for (MessageEntity messageEntity : msgList) {
                messageEntity.setState(1);
                messageService.updateMessageById(messageEntity);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "批量修改消息失败:error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("批量修改消息失败");
        }
        return dto;
    }

	/* (非 Javadoc)
	 * Description:消息列表查询
	 * @see com.jk.services.diabetes.api.MessageService#listMsg(com.jk.services.diabetes.dto.MessageRequest)
	 */
	@Override
	public DataTransferObject<MessageResponse> listMsg(MsgReq request) {
		DataTransferObject<MessageResponse> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        if (Check.NuNObjs(request.getMsgType())) {
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("参数异常");
        	return dto;
        }
        try {
        	MessageResponse MessagePageList = messageService.listMsg(request);
            dto.setData(MessagePageList);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "消息列表查询失败:error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("消息列表查询查询失败");
        }
        return dto;
	}


    @Override
    public DataTransferObject<RemindTimeVo> getMessageByDate(DateRequest request) {
        DataTransferObject<RemindTimeVo> dto = new DataTransferObject<>();
        if(Check.NuNObj(request)){
            dto.setErrorMsg("参数异常");
        }
        try {
            Date date = this.getQueryDate(request.getLastTime());
            request.setLastTime(date);
            long count = messageService.getMessageByDate(request);
            if(count>0){
                RemindTimeVo remindDiabetesTimeVo = new RemindTimeVo();
                remindDiabetesTimeVo.setRemindTime(new Date());
                remindDiabetesTimeVo.setMsgType(request.getMsgType());
                dto.setData(remindDiabetesTimeVo);
            }
            return dto;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询系统消控糖资讯失败:error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("查询系统消息控糖资讯失败");
        }
        return dto;
    }


    /**
     * 获取当前的有效时间节点
     * @param date
     * @return
     */
    private Date getQueryDate(Date date){
        if(Check.NuNObj(date)){
            return DateUtil.jumpDate(new Date(),-7);
        }
        if (date.before(DateUtil.jumpDate(new Date(),-7))){
            return DateUtil.jumpDate(new Date(),-7);
        }
        return date;
    }
}
