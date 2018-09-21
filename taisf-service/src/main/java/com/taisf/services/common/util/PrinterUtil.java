package com.taisf.services.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印机服务
 */
public class PrinterUtil {
    public static final String URL = "http://api.feieyun.cn/Api/Open/";//不需要修改



    //**********测试时，打开下面注释掉方法的即可,更多接口文档信息,请访问官网开放平台查看**********
    public static void main(String[] args) throws Exception{

        //==================添加打印机接口（支持批量）==================
        //***返回值JSON字符串***
        //正确例子：{"msg":"ok","ret":0,"data":{"ok":["sn#key#remark#carnum","316500011#abcdefgh#快餐前台"],"no":["316500012#abcdefgh#快餐前台#13688889999  （错误：识别码不正确）"]},"serverExecutedTime":3}
        //错误：{"msg":"参数错误 : 该帐号未注册.","ret":-2,"data":null,"serverExecutedTime":37}

        //提示：打印机编号(必填) # 打印机识别码(必填) # 备注名称(选填) # 流量卡号码(选填)，多台打印机请换行（\n）添加新打印机信息，每次最多100行(台)。
			//String snlist = "918525217#key1#remark1#carnum1\nsn2#key2#remark2#carnum2";
//			String snlist = "918525217#3nvz8kw5#remark1";
//            String method = addprinter(snlist);
//            System.out.println(method);
        String USER = "bd@chdwang.com";//*必填*：账号名
        String UKEY = "vdtCCaM6S4I87Y7S";//*必填*: 注册账号后生成的UKEY
        String SN = "918525217";//*必填*：打印机编号，必须要在管理后台里添加打印机或调用API接口添加之后，才能调用API

        String method1 = print(USER, UKEY, SN, "test");
        System.out.println(method1);


    }




    /**
     *
     * @param printerUser 打印机用户
     * @param printerKey 打印机key
     * @param printerSn 打印机编号
     * @param content 打印内容
     * @return
     */
    public static String print(String printerUser, String printerKey, String printerSn, String content){
        //标签说明：
        //单标签:
        //"<BR>"为换行,"<CUT>"为切刀指令(主动切纸,仅限切刀打印机使用才有效果)
        //"<LOGO>"为打印LOGO指令(前提是预先在机器内置LOGO图片),"<PLUGIN>"为钱箱或者外置音响指令
        //成对标签：
        //"<CB></CB>"为居中放大一倍,"<B></B>"为放大一倍,"<C></C>"为居中,<L></L>字体变高一倍
        //<W></W>字体变宽一倍,"<QR></QR>"为二维码,"<BOLD></BOLD>"为字体加粗,"<RIGHT></RIGHT>"为右对齐
        //拼凑订单内容时可参考如下格式
        //根据打印纸张的宽度，自行调整内容的格式，可参考下面的样例格式



//        String content;
//        String content1;
//        content1 = "档口A<BR>";
//        content1 += "糖醋小排　　　　　 单价  数量 30份<BR><CUT>";
//        content1 += "档口B<BR>";
//        content1 += "糖醋小排11　　　　　 单价  数量 30份<BR><CUT>";
//        content1 += "档口C<BR>";
//        content1 += "糖醋小排12　　　　　 单价  数量 30份<BR><CUT>";
//        content = "<CB>测试打印</CB><BR>";
//        content += "名称　　　　　 单价  数量 金额<BR>";
//        content += "--------------------------------<BR>";
//        content += "饭　　　　　　 1.0    1   1.0<BR>";
//        content += "炒饭　　　　　 10.0   10  10.0<BR>";
//        content += "蛋炒饭　　　　 10.0   10  100.0<BR>";
//        content += "鸡蛋炒饭　　　 100.0  1   100.0<BR>";
//        content += "番茄蛋炒饭　　 1000.0 1   100.0<BR>";
//        content += "西红柿蛋炒饭　 1000.0 1   100.0<BR>";
//        content += "西红柿鸡蛋炒饭 100.0  10  100.0<BR>";
//        content += "备注：加辣<BR>";
//        content += "--------------------------------<BR>";
//        content += "合计：xx.0元<BR>";
//        content += "送货地点：广州市南沙区xx路xx号<BR>";
//        content += "联系电话：13888888888888<BR>";
//        content += "订餐时间：2016-08-08 08:08:08<BR>";
//        content += "<QR>http://www.dzist.com</QR>";

        //通过POST请求，发送打印信息到服务器
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)//读取超时
                .setConnectTimeout(30000)//连接超时
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpPost post = new HttpPost(URL);
        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("user",printerUser));
        String STIME = String.valueOf(System.currentTimeMillis()/1000);
        nvps.add(new BasicNameValuePair("stime",STIME));
        nvps.add(new BasicNameValuePair("sig",signature(printerUser,printerKey,STIME)));
        nvps.add(new BasicNameValuePair("apiname","Open_printMsg"));//固定值,不需要修改
        nvps.add(new BasicNameValuePair("sn",printerSn));
        nvps.add(new BasicNameValuePair("content",content));
        nvps.add(new BasicNameValuePair("times","1"));//打印联数

        CloseableHttpResponse response = null;
        String result = null;
        try
        {
            post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
            response = httpClient.execute(post);
            int statecode = response.getStatusLine().getStatusCode();
            if(statecode == 200){
                HttpEntity httpentity = response.getEntity();
                if (httpentity != null){
                    //服务器返回的JSON字符串，建议要当做日志记录起来
                    result = EntityUtils.toString(httpentity);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally{
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                post.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }




    //生成签名字符串
    private static String signature(String USER,String UKEY,String STIME){
        String s = DigestUtils.sha1Hex(USER+UKEY+STIME);
        return s;
    }
}
