package com.cmict.activiti.service.impl;

import com.cmict.activiti.dto.ProcessDTO;
import com.cmict.activiti.service.IActivitiService;
import com.cmict.entity.QueryRequest;
import com.cmict.exception.CmictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.DataGrid;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luffy
 * @date 2021/8/20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ActivitiServiceImpl implements IActivitiService {
    private final RepositoryService rep;

    @Override
    public Map<String, Object> getProcesslist(QueryRequest queryRequest) {
        int firstrow = (queryRequest.getPageNum() - 1) * queryRequest.getPageSize();
        List<ProcessDefinition> list = rep.createProcessDefinitionQuery().listPage(firstrow, queryRequest.getPageSize());
        int total = rep.createProcessDefinitionQuery().list().size();
        List<ProcessDTO> processList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ProcessDTO p = new ProcessDTO();
            p.setDeploymentId(list.get(i).getDeploymentId());
            p.setId(list.get(i).getId());
            p.setKey(list.get(i).getKey());
            p.setName(list.get(i).getName());
            p.setResourceName(list.get(i).getResourceName());
            p.setDiagramResourceName(list.get(i).getDiagramResourceName());
            processList.add(p);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("rows", processList);
        data.put("total", total);
        return data;
    }

    @Override
    public void uploadWorkflow(MultipartFile uploadFile) {
        try {
            MultipartFile file = uploadFile;
            String filename = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            rep.createDeployment().addInputStream(filename, is).deploy();
        } catch (Exception e) {
            log.warn("upload workflow error", e);
            throw new CmictException("upload workflow error", e);
        }
    }

    @Override
    public void showResource(String pDid, String resource, HttpServletResponse response) {
        ProcessDefinition def = rep.createProcessDefinitionQuery().processDefinitionId(pDid).singleResult();
        try (InputStream is = rep.getResourceAsStream(def.getDeploymentId(), resource)) {
            ServletOutputStream output = response.getOutputStream();
            IOUtils.copy(is, output);
        } catch (IOException e) {
            log.warn("show resource error", e);
            throw new CmictException("show resource error", e);
        }
    }
}
