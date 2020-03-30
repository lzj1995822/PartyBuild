package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.identity.domain.ExcelColumn;
import lombok.Data;

@Data
public class ImportVO {
    @ExcelColumn(value = "村名", col = 1)
    private String districtName;
    @ExcelColumn(value = "日常工作", col = 2)
    private String rcgz;
    @ExcelColumn(value = "村级实绩", col = 3)
    private String cjsj;
}
