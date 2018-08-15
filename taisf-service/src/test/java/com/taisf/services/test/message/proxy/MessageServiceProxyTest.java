package com.taisf.services.test.message.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.message.api.MessageService;
import com.taisf.services.message.dto.DateRequest;
import com.taisf.services.message.dto.MessageRequest;
import com.taisf.services.message.entity.MessageEntity;
import com.taisf.services.message.vo.RemindTimeVo;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/31.
 * @version 1.0
 * @since 1.0
 */
public class MessageServiceProxyTest extends BaseTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void saveDataTest() {
        DataTransferObject<PagingResult<MessageEntity>> messagePageList = messageService.findMessagePageList(new MessageRequest());
        System.out.println(JsonEntityTransform.Object2Json(messagePageList));
    }
    @Test
    public void findMessageList() {
        DataTransferObject<List<MessageEntity>> messageList = messageService.findMessageList();
        System.out.println(JsonEntityTransform.Object2Json(messageList));
    }


    @Test
    public void getMessageByDate(){
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String s = "2017-11-07 00:00:00";

        DateRequest dateRequest = new DateRequest();
        dateRequest.setMsgType(1);
        dateRequest.setPlatform(1);
        try {
            Date date1 =  sdf2.parse(s);
            dateRequest.setLastTime(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DataTransferObject<RemindTimeVo> dto = messageService.getMessageByDate(dateRequest);
        System.out.println(JsonEntityTransform.Object2Json(dto));

    }

}
