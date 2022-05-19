package com.cmict.activiti.dto;

import lombok.Data;

/**
 * @author luffy
 * @date 2021/8/20
 */
@Data
public class ProcessDTO {
    String id;
    String deploymentId;
    String name;
    String resourceName;
    String key;
    String diagramResourceName;
}
