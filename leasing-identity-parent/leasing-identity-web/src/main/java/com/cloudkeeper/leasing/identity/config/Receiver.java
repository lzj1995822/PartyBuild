//package com.cloudkeeper.leasing.identity.config;
//
//import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
//import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
//import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
//import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
//import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
//import com.cloudkeeper.leasing.identity.service.KPIVillageQuotaService;
//import com.cloudkeeper.leasing.identity.service.KPIVillageStatisticsService;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Component
//public class Receiver {
//
//    @Resource
//    private KPITownQuotaService kPITownQuotaService;
//    @Resource
//    private KPIVillageQuotaService kpiVillageQuotaService;
//
//    @Resource
//    private CadreTaskObjectService cadreTaskObjectService;
//
//    @Resource
//    private KPIVillageStatisticsService kpiVillageStatisticsService;
//
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, new PatternTopic("checkHasCompleted"));
//        return container;
//    }
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//    private static final Logger logger = LoggerFactory
//            .getLogger(Receiver.class);
//
//    @Transactional
//    public void receiveMessage(String message) {
//        logger.info("Redis接收到消息---------------------" + message);
//
////        //1.检查所有分项都填写
////        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageQuota.class);
////        detachedCriteria.add(Restrictions.or(Restrictions.eq("score",""), Restrictions.isNull("score")));
////        Integer num = kpiVillageQuotaService.getTotalCount(detachedCriteria);
////        //1.1存在未完成直接丢弃
////        if (num > 0){
////            return;
////        }
////
////        //2.监测所有考核任务是否都完成
////        DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(CadreTaskObject.class);
////        detachedCriteria1.add(Restrictions.eq("taskName","考核内容实施"))
////        .add(Restrictions.ne("status","2"));
////        Integer num1 = cadreTaskObjectService.getTotalCount(detachedCriteria1);
////
////        //2.1、存在未完成直接丢弃
////        if (num1 > 0){
////            return;
////        }
//        //2.2.1、获取全部考核数据
//        List<KPITownQuota> list = kPITownQuotaService.findAll();
//        List<KPIVillageStatistics> res = null;
//
//        for (KPITownQuota kpiTownQuota : list) {
//            for (KPIVillageQuota kpiVillageQuota : kpiTownQuota.getKpiVillageQuotas()) {
//                //整合至统计表
//                KPIVillageStatistics k = new KPIVillageStatistics();
//                k.setDistrictId(kpiVillageQuota.getDistrictId());
//                k.setDistrictName(kpiVillageQuota.getDistrictName());
//                k.setParentDistrictId(kpiTownQuota.getDistrictId());
//                k.setParentDistrictName(kpiTownQuota.getDistrictName());
//                k.setParentQuotaId(kpiTownQuota.getParentQuotaId());
//                k.setParentQuotaName(kpiTownQuota.getParentQuotaName());
//                k.setQuotaName(kpiTownQuota.getQuotaName());
//                k.setQuarter(kpiTownQuota.getQuarter());
//                k.setScore(StringUtils.isNotBlank(kpiVillageQuota.getScoreEnd()) ? kpiVillageQuota.getScoreEnd() : "0");
//                k.setTaskId(message);
//                k.setWeight(kpiVillageQuota.getWeight());
//                kpiVillageStatisticsService.saveAndFlush(k);//三级统计
//            }
//
//            //二级统计
////            String sql = "SELECT cast(sum(cast(score as FLOAT)) as VARCHAR) as score,districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId as quotaId,parentQuotaName as quotaName FROM KPI_Village_Statistics GROUP BY districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId,parentQuotaName";
////            res = kpiVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, sql);
////            for (KPIVillageStatistics kpiVillageStatistics : res){
////                kpiVillageStatistics.setTaskId(message);
////                kpiVillageStatistics.setParentQuotaId(kpiVillageStatistics.getQuotaId().substring(0,2));
////                kpiVillageStatisticsService.saveAndFlush(kpiVillageStatistics);
////            }
////
////            //一级考核
////            String sql1 = "SELECT cast(sum(cast(score as FLOAT)) as VARCHAR) as score,districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId as quotaId FROM KPI_Village_Statistics GROUP BY districtId,districtName, parentDistrictName,parentDistrictId";
////            res = kpiVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, sql1);
////            //获取所有村，生成MAP
////            Map<String,>
////            List<KPIVillageStatistics> cjsj = new ArrayList<>();
////            for (KPIVillageStatistics kpiVillageStatistics : res){
////                kpiVillageStatistics.setTaskId(message);
////                kpiVillageStatistics.setParentQuotaId("0");
////                kpiVillageStatisticsService.saveAndFlush(kpiVillageStatistics);
////            }
//        }
//    }
//}
