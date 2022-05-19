package com.cmict.server.system.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chentianshu
 * @since 2021/5/17
 * 事件列表导出excel实体定义
 */
@Data
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 42)
public class EventExcelVo {

    @ExcelProperty("事件编号")
    private String caseNumber;

    @ExcelProperty("摄像头编号")
    private String deviceId;

    @ExcelProperty("摄像头名称")
    private String deviceName;

    @ExcelProperty("告警图URI")
    private String causeImageUri;

    @ExcelProperty("告警视频URI")
    private String causeVideoUri;

    @ExcelProperty("告警时间")
    private Date createTime;

    @ExcelProperty("告警地点")
    private String address;

    @ExcelProperty("经度")
    private BigDecimal lon;

    @ExcelProperty("纬度")
    private BigDecimal lat;

    @ExcelProperty("事件类型")
    private String description;

    @ExcelProperty("是否上报")
    private String report;


}
