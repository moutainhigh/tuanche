package com.taisf.api.message.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.Check;
import com.taisf.api.common.abs.AbstractController;
import com.taisf.services.common.valenum.MsgTypeEnum;
import com.taisf.services.message.api.MessageService;
import com.taisf.services.message.dto.DateRequest;
import com.taisf.services.message.dto.MessageResponse;
import com.taisf.services.message.dto.MsgReq;
import com.taisf.services.message.vo.RemindTimeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaozeyu
 * @Description:
 * @Date: 2017/11/14.
 */
@Controller
@RequestMapping("msg")
public class MessageController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    
    @Resource(name = "messageService")
    private MessageService messageService;


    /**
     * 拉取指定是时间的列表信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto list(HttpServletRequest request , HttpServletResponse response){
    	MsgReq entity = getEntity(request, MsgReq.class);
    	if(Check.NuNObj(entity)){
            return  new ResponseDto("参数异常");
        }
        if(Check.NuNObj(entity.getMsgType())){
            return  new ResponseDto("参数异常");
        }
		Header header = getHeader(request);
        entity.setPlatform(header.getSource());
		DataTransferObject<MessageResponse> result = messageService.listMsg(entity);
		return result.trans2Res();
    }


    /**
     * 获取提醒信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/boxRemind", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto boxRemind(HttpServletRequest request , HttpServletResponse response){
        DataTransferObject<Map<Integer,RemindTimeVo>> dto = new DataTransferObject<>();
        Header header = getHeader(request);
        int source = header.getSource();
        String userId = getUserId(request);
        if(Check.NuNObj(source)){
            return new ResponseDto("没有平台");
        }
        if(Check.NuNObj(userId)){
            return new ResponseDto("userID为空");
        }
        List<DateRequest> dateRequestList = getList(request,DateRequest.class);
        if(Check.NuNCollection(dateRequestList)){
            return  new ResponseDto("参数异常");
        }
        for (DateRequest dateRequest : dateRequestList) {
                dateRequest.setPlatform(source);
                dateRequest.setCustomerId(userId);
        }
        Map<Integer,RemindTimeVo> map = new HashMap<>();

        for (DateRequest dateRequest : dateRequestList) {
            if(dateRequest.getMsgType() == MsgTypeEnum.SYSTEM.getCode()){
                DataTransferObject<RemindTimeVo> messageByDate = messageService.getMessageByDate(dateRequest);
                RemindTimeVo remindTimeVo = messageByDate.getData();
                //加list
                if(Check.NuNObj(remindTimeVo)) {
                    remindTimeVo = new RemindTimeVo();
                    remindTimeVo.setMsgType(dateRequest.getMsgType());
                }
                map.put(dateRequest.getMsgType(),remindTimeVo);
            }
        }
        dto.setData(map);
        return dto.trans2Res();
    }


}
