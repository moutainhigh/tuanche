package com.taisf.services.common.valenum;

import com.jk.framework.base.constant.YesNoEnum;

/**
 * <p>订单类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public enum OrderTypeEnum {

    LUNCH_COMMON(20,"午餐正常"){
        @Override
        public boolean checkSuit(int forLunch, int forDinner) {
            return forLunch == YesNoEnum.YES.getCode();
        }
    },
    LUNCH_EXT(21,"午餐补餐"){
        @Override
        public boolean isExt() {
            return true;
        }
    },
    DINNER_COMMON(30,"晚餐正常"){
        @Override
        public boolean checkSuit(int forLunch, int forDinner) {
            return forDinner == YesNoEnum.YES.getCode();
        }
    },
    DINNER_EXT(31,"晚餐补单"){
        @Override
        public boolean isExt() {
            return true;
        }
    },
    FACE(41,"面对面收款"){
    },
    ;
    private int code;
    private String name;

    private OrderTypeEnum(int code, String name) {
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

    public static OrderTypeEnum getTypeByCode(int code) {
        OrderTypeEnum[] enums = OrderTypeEnum.values();
        for(OrderTypeEnum enumtype:enums) {
            if(enumtype.getCode() == code) {
                return enumtype;
            }
        }
        return null;
    }

    /**
     * 默认不是补单
     * @return
     */
    public boolean isExt(){
        return false;
    }

    /**
     * 当前是否匹配
     * @param forLunch
     * @param forDinner
     * @return
     */
    public boolean checkSuit(int forLunch,int forDinner){
        return false;
    }

}
