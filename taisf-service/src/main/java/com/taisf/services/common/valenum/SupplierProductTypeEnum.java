package com.taisf.services.common.valenum;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/25.
 * @version 1.0
 * @since 1.0
 */
public enum SupplierProductTypeEnum {

    PRODUCT("商品", 1),
    PACKAGE("礼包", 2),
    ;

    // 成员变量
    private String name;
    private Integer code;


    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    // 构造方法
    private SupplierProductTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }



    // 普通方法
    public static SupplierProductTypeEnum getByCode(Integer code) {
        for (SupplierProductTypeEnum c : SupplierProductTypeEnum.values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }



}