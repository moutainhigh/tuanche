package com.taisf.services.common.valenum;


import com.jk.framework.base.utils.Check;

/**
 * @ClassName MsgTypeConstant
 * @Description 消息类型
 * @author rxg
 * @Date 2018年6月20日 下午4:26:53
 * @version 1.0.0
 */
public enum MsgTypeEnum {
	SYSTEM(1,"系统"),

    ;

    private int code;
    private String name;

    MsgTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static MsgTypeEnum getTypeByCode(Integer code) {
        if (Check.NuNObj(code)){
            return null;
        }
        MsgTypeEnum[] enums = MsgTypeEnum.values();
        for(MsgTypeEnum enumtype:enums) {
            if(enumtype.getCode() == code) {
                return enumtype;
            }
        }
        return null;
    }
}
