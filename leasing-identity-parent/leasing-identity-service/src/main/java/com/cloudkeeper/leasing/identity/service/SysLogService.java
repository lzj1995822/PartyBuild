package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysLog;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.CloudLogVO;
import com.cloudkeeper.leasing.identity.vo.SysLogVO;

import java.util.List;

/**
 * 系统日志 service
 * @author asher
 */
public interface SysLogService extends BaseService<SysLog> {

    /**
     * 新增系统操作日志
     * @param controllerName 系统控制器层
     * @param msg 操作内容
     * @param tableName 表名
     * @param businessId 相关业务表id
     * @return
     */
    SysLogVO pushLog(String controllerName, String msg, String tableName, String businessId);

    List<CloudLogVO> getCloudLog();
}