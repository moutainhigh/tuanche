package com.taisf.services.message.dto;

import com.jk.framework.base.entity.BaseEntity;
import com.taisf.services.message.entity.MessageEntity;

import java.util.Date;
import java.util.List;

public class MessageResponse extends BaseEntity{

	/**
	 * @Field @serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MessageEntity> list;
	
	private Date systemTime;

	public List<MessageEntity> getList() {
		return list;
	}

	public void setList(List<MessageEntity> list) {
		this.list = list;
	}

	public Date getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}

}
