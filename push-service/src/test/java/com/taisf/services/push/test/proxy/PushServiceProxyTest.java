package com.taisf.services.push.test.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.device.dto.PushMessage;
import com.taisf.services.device.entity.DeviceEntity;
import com.taisf.services.device.proxy.PushServiceProxy;
import com.taisf.services.push.request.MoneySendRequest;
import com.taisf.services.push.test.jiguang.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class PushServiceProxyTest extends BaseTest {

	@Autowired
	private PushServiceProxy pushService;



	@Test
	public void sendMoneySuccessTest() {

		MoneySendRequest moneySendRequest = new MoneySendRequest();
		moneySendRequest.setMoney("money");
		moneySendRequest.setName("name");
		moneySendRequest.setPhone("phone");
		moneySendRequest.setUserId("baozi");
		DataTransferObject<Void> voidDataTransferObject = pushService.sendMoneySuccess(moneySendRequest);
		System.out.println(voidDataTransferObject.toJsonString());
	}


	@Test
	public void testregistDevice() {


		DeviceEntity entity = new DeviceEntity();
		entity.setRegId("F9365E230312414199CC34122F6F1D70");
		entity.setDeviceToken("c1474c0bbd89117493cf57376c4bfdd4782c9beeee133cb7d5e68575e7d8cf6a");
		entity.setUserId("111");
		System.out.println("===============================" + JsonEntityTransform.Object2Json(pushService.registDevice(entity)));
	}

	@Test
	public void testlogoutDevice() {
		DeviceEntity entity = new DeviceEntity();
		entity.setRegId("tetete");
		entity.setUserId("3445");
		System.out.println("===============================" + JsonEntityTransform.Object2Json(pushService.logoutDevice(entity)));
	}

	@Test
	public void testPushMessage() {
		List<String> userIds = new ArrayList<String>();
		userIds.add("12344454");
		PushMessage message = new PushMessage();
		message.setTitle("测试标题");
		message.setContent("测试内容");
		System.out.println("===============================" + JsonEntityTransform.Object2Json(pushService.pushMessage(userIds,message)));
	}

	@Test
	public void testPushAllMessage() {
		PushMessage message = new PushMessage();
		message.setTitle("测试标题");
		message.setContent("测试内容");
		//System.out.println("===============================" + JsonEntityTransform.Object2Json(pushService.pushAllMessage(message)));
	}
	
} 
