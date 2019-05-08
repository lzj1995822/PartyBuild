package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.Organization;
import com.cloudkeeper.leasing.identity.enumeration.OrganizationTypeEnum;
import com.cloudkeeper.leasing.identity.vo.OrganizationVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrganizationServiceTest {

    /** 组织service*/
    @Autowired
    private OrganizationService organizationService;

    @Test
    public void saveTest() {


    }

    private void createDept(@Nonnull Organization organization) {
        int deptSort = 1;
        Organization organizationDept1 = new Organization();
        organizationService.save(organizationDept1);

        Organization organizationPosition1 = new Organization();
        organizationService.save(organizationPosition1);

        Organization organizationDept2 = new Organization();
        organizationService.save(organizationDept2);
        createBDChild(organizationDept2);

        Organization organizationDept3 = new Organization();
        organizationService.save(organizationDept3);
        createFinanceDeptPosition(organizationDept3);
    }

    private void createBDChild(@Nonnull Organization organization) {
        int sort = 1;
        Organization organizationDept1 = new Organization();
        organizationService.save(organizationDept1);

        Organization organizationDept2 = new Organization();
        organizationService.save(organizationDept2);

        Arrays.asList(organizationDept1, organizationDept2).forEach(this::createBDPosition);
    }

    private void createBDPosition(@Nonnull Organization organization) {
        int sort = 1;
        Organization organizationPosition1 = new Organization();
        organizationService.save(organizationPosition1);

        Organization organizationPosition2 = new Organization();
        organizationService.save(organizationPosition2);
    }

    private void createFinanceDeptPosition(@Nonnull Organization organization) {
        int sort = 1;
        Organization organizationPosition1 = new Organization();
        organizationService.save(organizationPosition1);

        Organization organizationPosition2 = new Organization();
        organizationService.save(organizationPosition2);
    }

//    @Test
//    public void findTree() {
//        OrganizationVO organizationVO = organizationService.findTree();
//        System.out.println(organizationVO);
//    }
//
//    @Test
//    public void findAllByParentId() {
//        List<Organization> organizationList = organizationService.findAllByParentId("5058d487-4497-40eb-af8b-47bc3db36359");
//        assertEquals(organizationList.size(), 3);
//    }
//
//    @Test
//    public void uniqueCode() {
//        assertFalse(organizationService.existsCode("group", "b57dbc0e-5a41-4e47-8f07-3f57a8884636", "68476635-57d0-459a-9585-2da76727e14b"));
//        assertTrue(organizationService.existsCode("branchSZ", "b57dbc0e-5a41-4e47-8f07-3f57a8884636", "68476635-57d0-459a-9585-2da76727e14b"));
//        assertTrue(organizationService.existsCode("group", "b57dbc0e-5a41-4e47-8f07-3f57a8884636", ""));
//        assertFalse(organizationService.existsCode("group1", "b57dbc0e-5a41-4e47-8f07-3f57a8884636", ""));
//    }
}