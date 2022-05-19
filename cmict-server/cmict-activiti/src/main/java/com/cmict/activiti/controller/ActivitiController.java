package com.cmict.activiti.controller;

import com.cmict.activiti.feign.UserClient;
import com.cmict.activiti.service.IActivitiService;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.QueryRequest;
import com.cmict.untils.CmictUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Api(value = "请假流程接口", tags = "请假流程接口")
public class ActivitiController {

    private final UserClient userClient;
    private final IActivitiService activitiService;

    @ApiOperation("测试")
    @GetMapping("/test")
    public CmictResponse getUserRouters() {
        String username = CmictUtil.getCurrentUsername();

        if (username == null) {
            System.out.println(userClient.getUserDetail("admin"));
        } else {
            System.out.println(userClient.getUserDetail(username));
        }
        return CmictResponse.ok("test");
    }


    @ApiOperation("查询已部署工作流列表")
    @PostMapping(value = "/getProcesslist")
    public CmictResponse getProcesslist(QueryRequest queryRequest) {
        return CmictResponse.ok(activitiService.getProcesslist(queryRequest));
    }

    @ApiOperation("上传一个工作流文件")
    @RequestMapping(value = "/uploadWorkflow", method = RequestMethod.POST)
    public CmictResponse uploadWorkflow(@RequestParam MultipartFile uploadFile) {
        activitiService.uploadWorkflow(uploadFile);
        return CmictResponse.ok("ok");
    }

    @ApiOperation("查看工作流图片")
    @RequestMapping(value = "/showResource", method = RequestMethod.GET)
    public void showResource(@RequestParam("pDid") String pDid, @RequestParam("resource") String resource,
                             HttpServletResponse response) {
        activitiService.showResource(pDid,resource, response);
    }

    @ApiOperation("查看工作流图片")
    @RequestMapping(value = "/showResourcePng", method = RequestMethod.GET)
    public void showResourcePng(@RequestParam("resource") String resource,
                                HttpServletResponse response) throws Exception {
        try (InputStream is = new FileInputStream(resource)) {
            ServletOutputStream output = response.getOutputStream();
            IOUtils.copy(is, output);
        }
    }

}
