package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.paractivityfeedback.ParActivityFeedbackSearchable;
import com.cloudkeeper.leasing.identity.vo.ParActivityFeedbackVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 移动端执行记录 service
 * @author lxw
 */
public interface ParActivityFeedbackService extends BaseService<ParActivityFeedback> {

     Page<ParActivityFeedbackVO> phonePage(ParActivityFeedbackSearchable parActivityFeedbackSearchable, Pageable pageable);
}
