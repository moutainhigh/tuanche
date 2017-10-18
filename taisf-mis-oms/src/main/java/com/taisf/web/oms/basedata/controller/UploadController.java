package com.taisf.web.oms.basedata.controller;

import com.jk.framework.base.utils.DateUtil;
import com.taisf.web.oms.common.constant.PathConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhengguang
 * @create 2017-10-17
 **/
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private PathConstant pathConstant;

    /**
     * @author:zhangzhengguang
     * @date:2017/10/17
     * @description:图片上传支持一次上传多张图片
     **/
    @RequestMapping("/uploadPics")
    public
    @ResponseBody
    List<String> uploadPics(@RequestParam(required = false) MultipartFile[] pics, HttpServletRequest request) throws Exception {
        List<String> urls = new ArrayList<String>();
        String filePath = "";
        try {
            for (MultipartFile file : pics) {
                int date = DateUtil.currentTimeSecond();
                filePath = request.getSession().getServletContext().getRealPath("/") + "file/" + date + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                System.out.println("图片上传================================" + filePath);
                String str = "/file/" + date + file.getOriginalFilename();
                urls.add(pathConstant.IMAGE_URL + str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }
}
