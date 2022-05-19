package com.cmict.activiti.service;

import com.cmict.entity.QueryRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author luffy
 * @date 2021/8/20
 */
public interface IActivitiService {
    Map<String, Object> getProcesslist(QueryRequest queryRequest);

    void uploadWorkflow(MultipartFile uploadFile);

    void showResource(String pDid, String resource, HttpServletResponse response);
}
