验证码相关接口
-----------------------------------  



## 1.获取token

###  地址

    send/getToken

###  提交方式
类型|描述
---|---
提交方式|get

参数 放在url



### 参数信息
无

返回信息:成功

    {
        "msg": {
            "info": "",
            "code": 0,
            "success": true
        },
        "data": {
            "token":"qweqwe"
        }
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
   
   
   



## 2.获取验证码

###  地址

    send/authCode?token=xxx

###  提交方式
类型|描述
---|---
提交方式|get

参数 放在url



### 参数信息
字段|类型|描述
---|---|---
token|String|上接口返回token




返回信息:成功

直接返回图片流





## 3.发送验证码

###  地址

    send/code?code=103&userTel=12313123123131

###  提交方式
类型|描述
---|---
提交方式|get

参数 放在url



### 参数信息
字段|类型|描述
---|---|---
code|String|修改支付密码传103 用户登录:101  对外用户注册 200
userTel|String|用户电话
random|String|随机数/如果是对外用户注册 传获取验证码的token
sign|String|验签的key:Oj0mUTVY
authCode|String|对外用户注册的验证码 图片验证码[其他情况当前参数不传]

返回信息:成功

    {
        "msg": {
            "info": "",
            "code": 0,
            "success": true
        },
        "data": {}
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
    

      
demo:


````
    curl -X GET \
      'http://59.110.222.82/send/code?code=101&userTel=18618499887&random=123&sign=E5DA874815ED0B2E7228C64D087B703C' \
      -H 'cache-control: no-cache' \
      -H 'content-type: application/json' \
      -H 'postman-token: cea12c2c-aeab-8eb5-7c51-e47ed906a6b4' \
      -H 'token: ff8080815f0bc096015f0bc096530001' \
      -H 'traceinfo: applicationCode=user;deviceUuid=deviceUuid;versionCode=versionCode;source=1;' \
      -d '{"userPhone":"15120095720","pwd":"fcea920f7412b5da7be0cf42b8c93759"}'

````

