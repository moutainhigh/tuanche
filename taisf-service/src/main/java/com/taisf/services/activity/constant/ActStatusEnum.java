package com.taisf.services.activity.constant;

import com.jk.framework.base.utils.Check;


/**
 * <p>活动状态枚举 </p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public enum ActStatusEnum {

    NOT_ENABLE(1,"未启用"),
    ENABLE(2,"已启用"),
    ABORTED(3,"已中止"),
	PAST_DUE(4,"已过期");


    private int code;
    private String name;
    private ActStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    /**
     * 获取当前的类型
     * @author afi
     * @param code
     * @return
     */
    public static ActStatusEnum getTypeByCode(Integer code) {
        if (Check.NuNObj(code)){
            return null;
        }
        ActStatusEnum[] enums = ActStatusEnum.values();
    	for(ActStatusEnum enumtype:enums) {
            if(enumtype.getCode() == code) {
                return enumtype;
            }
        }
        return null;
    }
    
    /**
     * 获取当前的类型
     * @author afi
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code) {
        if (Check.NuNObj(code)){
            return null;
        }
        ActStatusEnum[] enums = ActStatusEnum.values();
    	for(ActStatusEnum enumtype:enums) {
            if(enumtype.getCode() == code) {
                return enumtype.getName();
            }
        }
        return null;
    }
}
