package com.webdrp.controller.sys;

import com.webdrp.common.Result;
import com.webdrp.util.QiNiuUtils;
import com.webdrp.util.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuanming on 2018/8/13.
 */

@Api(tags = "后台接口七牛云配置")
@RestController
@RequestMapping("/sys/qiniu")
public class QiniuController {

        @GetMapping("/config")
        @ApiOperation("七牛云整合")
        public Result config(@RequestHeader(value="token") String token){
                String uuid = UUIDUtils.getUUID();
               return Result.success().addAttribute("token", QiNiuUtils.GetToken())
                        .addAttribute("uploadUrl", QiNiuUtils.getUploadAPI())
                        .addAttribute("key", uuid)
                        .addAttribute("domain", QiNiuUtils.getBucketDomain()+"");
        }
}
