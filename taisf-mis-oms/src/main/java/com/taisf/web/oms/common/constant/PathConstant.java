package com.taisf.web.oms.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("pathConstant")
public class PathConstant {

	@Value("#{'${static.resource.url}'}")
	public  String IMAGE_URL;


}
