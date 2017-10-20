package com.taisf.services.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("pathConstant")
public class PathConstant {

	@Value("#{'${static.resource.url}'}")
	public  String IMAGE_URL;

	@Value("#{'${image.path}'}")
	public  String IMAGE_PATH;


}
