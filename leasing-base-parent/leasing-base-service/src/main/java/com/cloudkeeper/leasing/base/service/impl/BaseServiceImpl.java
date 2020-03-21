package com.cloudkeeper.leasing.base.service.impl;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.base.utils.BeanConverts;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
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
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    /** 实体manager*/
    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected HttpServletRequest request;

    public HSSFWorkbook export(String title, String[] rowName, List<Object[]> dataList) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTitle = rowm.createCell(0);


            //sheet样式定义【】
            HSSFCellStyle columnTopStyle=this.getColumnTopStyle(workbook);
            HSSFCellStyle style=this.getStyle(workbook);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
            cellTitle.setCellStyle(columnTopStyle);
            cellTitle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(columnTopStyle);

            }
            // 将查询到的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);// 遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());
                        }
                    }
                    cell.setCellStyle(style);

                }

            }

            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }
    /*
     * 列头单元格样式
     */
    private HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();

        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置低边框
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置低边框颜色
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置右边框
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框颜色
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 在样式中应用设置的字体
        style.setFont(font);
        // 设置自动换行
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐；
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;

    }
    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 字体加粗
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        // 设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
    /**
     * 子类实现该方法
     * @return IBaseRepository
     * @author jerry
     */
    protected abstract BaseRepository<T> getBaseRepository();

    @Override
    public String getCurrentPrincipalId() {
        HttpSession httpSession = getHttpSession();
        Object principalId = httpSession.getAttribute(AuthorizationConstants.CURRENT_USER_ID);
        return (String) principalId;
    }
    /**
     * 获取泛型类型
     * @return 泛型类型
     */
    protected Class<T> getEntityClass() {
        return (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public HttpSession getHttpSession() {
        return request.getSession();
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
//    @Override
//    public <D> List<D> findPageBySql(@Nonnull Class<D> clazz, @Nonnull String sql,@Nonnull Integer page,@Nonnull Integer pageSize) {
//        page = (page-1)*pageSize;
//        sql += " limit "+page +","+pageSize;
//        List<Map<String, Object>> list = entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//        return BeanConverts.mapToObj(clazz, list);
//    }
    @Override
    public String actionLog(String action, String taskType, String title) {
        String type;
        if(taskType.equals("Party")){
            type="[党建任务]";
        }
        else if(taskType.equals("DistLearning")){
            type="[远教任务]";
        }
        else{
            type=taskType;
        }
        String msg = action +'"'+ type+'-'+title+'"';
        return msg;
    }
}
