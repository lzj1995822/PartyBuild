package com.cloudkeeper.leasing.base.service.impl;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.base.utils.BeanConverts;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 基础service 实现
 * @param <T> 泛型
 * @author jerry
 */
public abstract class BaseNoHttpServiceImpl<T> implements BaseService<T> {

    /** 实体manager*/
    @Autowired
    protected EntityManager entityManager;

    /**
     * 子类实现该方法
     * @return IBaseRepository
     * @author jerry
     */
    protected abstract BaseRepository<T> getBaseRepository();

    /**
     * 获取泛型类型
     * @return 泛型类型
     */
    protected Class<T> getEntityClass() {
        return (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public HttpSession getHttpSession() {
        return null;
    }

    @Override
    public String getCurrentPrincipalId() {
        return null;
    }

    @Override
    public String getTableName() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()
        Class clazz = (Class) type.getActualTypeArguments()[0];
        return ((Table) clazz.getAnnotation(Table.class)).name();
    }
    /**
     * 获取hibernate session
     * @return session
     */
    private Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    @Override
    @Nonnull
    @Transactional(rollbackFor = Exception.class)
    public T save(@Nonnull T entity) {
        return getBaseRepository().save(entity);
    }

    @Nonnull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T saveAndFlush(@Nonnull T entity) {
        return getBaseRepository().saveAndFlush(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@Nonnull T entity) {
        getBaseRepository().delete(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(@Nonnull String id) {
        getBaseRepository().deleteById(id);
    }

    @Override
    public T getOne(@Nonnull String id) {
        return getBaseRepository().getOne(id);
    }

    @Override
    public T findById(@Nonnull String id) {
        return findOptionalById(id).orElse(null);
    }

    @Override
    @Nonnull
    public Optional<T> findOptionalById(@Nonnull String id) {
        return getBaseRepository().findById(id);
    }

    @Override
    @Nonnull
    public List<T> findAll() {
        return getBaseRepository().findAll();
    }

    @Override
    @Nonnull
    public List<T> findAll(@Nonnull DetachedCriteria detachedCriteria) {
        return detachedCriteria.getExecutableCriteria(getSession()).list();
    }

    @Override
    @Nonnull
    public Page<T> findAll(@Nonnull DetachedCriteria detachedCriteria, @Nonnull Pageable pageable) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setProjection(Projections.rowCount());
        int resultCount = ((Long) criteria.uniqueResult()).intValue();
        //清空projection，以便取得记录
        criteria.setProjection(null);
        //设置查询结果为实体对象
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        return new PageImpl<>((List<T>) criteria.list(), pageable, resultCount);
    }

    @Nonnull
    public Page<T> findAll(@Nonnull DetachedCriteria detachedCriteria, @Nonnull Pageable pageable,Integer resultCount) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setProjection(Projections.rowCount());
        //清空projection，以便取得记录
        criteria.setProjection(null);
        //设置查询结果为实体对象
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        return new PageImpl<>((List<T>) criteria.list(), pageable, resultCount);
    }

    public Integer getTotalCount(@Nonnull DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }

    @Override
    @Nonnull
    public List<T> findAll(@Nonnull Sort sort) {
        return getBaseRepository().findAll(sort);
    }

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull BaseSearchable searchable) {
        return findAll(defaultExample(searchable));
    }

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull BaseSearchable searchable, @Nonnull Sort sort) {
        return findAll(defaultExample(searchable), sort);
    }

    @Override
    @Nonnull
    public <S extends T> List<S> findAll(@Nonnull Example<S> example) {
        return getBaseRepository().findAll(example);
    }

    @Override
    @Nonnull
    public <S extends T> List<S> findAll(@Nonnull Example<S> example, @Nonnull Sort sort) {
        return getBaseRepository().findAll(example, sort);
    }

    @Override
    @Nonnull
    public List<T> findAllById(@Nonnull Iterable<String> ids) {
        return getBaseRepository().findAllById(ids);
    }

    @Override
    @Nonnull
    public Page<T> findAll(@Nonnull Pageable pageable) {
        return getBaseRepository().findAll(pageable);
    }

    @Nonnull
    @Override
    public Page<T> findAll(@Nonnull BaseSearchable searchable, @Nonnull Pageable pageable) {
        return findAll(defaultExample(searchable), pageable);
    }

    @Override
    @Nonnull
    public Page<T> findAll(@Nonnull Example<T> example, @Nonnull Pageable pageable) {
        return getBaseRepository().findAll(example, pageable);
    }

    @Override
    @Nonnull
    public List<T> findAll(@Nonnull Specification<T> specification) {
        return getBaseRepository().findAll(specification);
    }

    @Override
    @Nonnull
    public Page<T> findAll(@Nonnull Specification<T> specification, @Nonnull Pageable pageable) {
        return getBaseRepository().findAll(specification, pageable);
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return ExampleMatcher.matching();
    }

    @Override
    public Example<T> defaultExample(@Nonnull BaseSearchable searchable) {
        return defaultExample(searchable, defaultExampleMatcher());
    }

    @Override
    public Example<T> defaultExample(@Nonnull BaseSearchable searchable, @Nonnull ExampleMatcher exampleMatcher) {
        Class <T> entityClass = getEntityClass();
        return Example.of(searchable.convert(entityClass), exampleMatcher);
    }

    // 原生sql查询返回体映射


    @Override
    public <D> D findBySql(@Nonnull Class<D> clazz, @Nonnull String sql) {
        Object singleResult = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getSingleResult();
        if (singleResult == null) {
            return null;
        }
        return BeanConverts.mapToObj(clazz, (Map<String, Object>) singleResult);
    }

    @Override
    public <D> List<D> findAllBySql(@Nonnull Class<D> clazz, @Nonnull String sql) {
        List<Map<String, Object>> list = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return BeanConverts.mapToObj(clazz, list);
    }

    @Override
    public String actionLog(String action, String taskType, String title) {
        return null;
    }
}
