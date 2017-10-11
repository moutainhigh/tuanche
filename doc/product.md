菜单相关文档
-----------------------------------  

## 1.商品分类

###  地址

    supplier/classify


###  提交方式
类型|描述
---|---
提交方式|get
参数|放在url


### 参数
    
supplierCode:企业code

返回信息:成功

    {
        "msg": {
            "info": "",
            "code": 0,
            "success": true
        },
        "data": [
            {
                "productClassify": "100",
                "productClassifyName": "推荐"
            },
            {
                "productClassify": "1",
                "productClassifyName": "大荤"
            },
            {
                "productClassify": "2",
                "productClassifyName": "小荤"
            },
            {
                "productClassify": "3",
                "productClassifyName": "素"
            },
            {
                "productClassify": "4",
                "productClassifyName": "汤"
            },
            {
                "productClassify": "5",
                "productClassifyName": "饮品"
            },
            {
                "productClassify": "6",
                "productClassifyName": "主食"
            },
            {
                "productClassify": "7",
                "productClassifyName": "水果"
            }
        ]
    }

返回信息:失败

    {
    msg: {
        info: "参数异常",
        code: 1,
        success: false
    },
    data: null
    }
    
返回关键字段解释
    
字段|类型|描述
---|---|---
productClassify|Integer|类型code,又有的菜单的分类都需要传这个参数
productClassifyName|String|展示名称

demo:


````
        curl -X GET \
        'http://localhost:8080/supplier/classify?supplierCode=code' \
        -H 'cache-control: no-cache' \
        -H 'postman-token: 106c9cc2-e98a-d445-a5e9-b9e05a569564'

````

            
## 2.获取商品分类

###  地址

    supplier/productList


###  提交方式
类型|描述
---|---
提交方式|GET
参数|放在url中


### 参数信息
字段|类型|描述
---|---|---
supplierCode|String|供应商code
productClassify|Integer|上一个接口返回的菜单分类

返回信息:成功
{
    "msg": {
        "info": "",
        "code": 0,
        "success": true
    },
    "data": []
}


返回关键字段解释
    
字段|类型|描述
---|---|---
productName|String|商品名称
productClassify|Integer|商品分类
productType|Integer|商品类型
productSource|Integer|商品属性
productDes|String|描述信息
productPic|String|图片信息
priceSale|Integer|销售价 单位:分


返回信息:失败

    {
        "msg": {
            "info": "异常的头信息",
            "code": 1,
            "success": false
        },
        "data": {}
    }
   
demo:

````
        curl -X GET \
        'http://localhost:8080/supplier/productList?supplierCode=code&productClassify=100' \
        -H 'cache-control: no-cache' \
        -H 'postman-token: 5480b730-adb5-a831-d7c3-1fe60aebef8d'

````
