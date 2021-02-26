package com.roborock.springboot.service.controller;

import com.roborock.springboot.service.common.domain.AjaxResult;
import com.roborock.springboot.service.util.StringUtils;
import com.roborock.springboot.service.util.oss.AlibabaOssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/22 17:23
 * @Description 通用方法，文件上传下载
 */

@RestController
@RequestMapping(value = "/common")
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private AlibabaOssService service;

    @PostMapping(value = "/upload")
    public AjaxResult upload(MultipartFile file){
        String fileName = service.uploadFile(file,"/file");
        if (StringUtils.isEmpty(fileName)) {
            return AjaxResult.error("文件上传失败！");
        }
        AjaxResult result = AjaxResult.success();
        result.put("fileName",fileName);
        return result;
    }

    @GetMapping(value = "/download")
    public void download(String fileName, HttpServletResponse response) {
        try {
            service.exportFile(response.getOutputStream(), fileName);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

}
