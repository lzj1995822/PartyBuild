package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FeedbackTemplate;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 反馈配置模板 repository
 * @author asher
 */
@Repository
public interface FeedbackTemplateRepository extends BaseRepository<FeedbackTemplate> {

}