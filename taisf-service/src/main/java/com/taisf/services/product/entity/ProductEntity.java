package com.taisf.services.product.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>菜单</p>
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
public class ProductEntity extends BaseEntity {
    /**
     * 逻辑id
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品分类
     */
    private Integer productClassify;

    /**
     * 商品配型
     */
    private Integer productType;

    /**
     * 商品属性
     */
    private Integer productSource;

    /**
     * 描述信息
     */
    private String productDes;

    /**
     * 默认图片
     */
    private String productPic;

    /**
     * 销售价
     */
    private Integer priceSale;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0：否 1：是
     */
    private Integer isDel;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductClassify() {
        return productClassify;
    }

    public void setProductClassify(Integer productClassify) {
        this.productClassify = productClassify;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getProductSource() {
        return productSource;
    }

    public void setProductSource(Integer productSource) {
        this.productSource = productSource;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public Integer getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Integer priceSale) {
        this.priceSale = priceSale;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productName=").append(productName);
        sb.append(", productClassify=").append(productClassify);
        sb.append(", productType=").append(productType);
        sb.append(", productSource=").append(productSource);
        sb.append(", productDes=").append(productDes);
        sb.append(", productPic=").append(productPic);
        sb.append(", priceSale=").append(priceSale);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}