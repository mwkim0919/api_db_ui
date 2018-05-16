package com.monkey.apiManagement.daos;

import com.monkey.apiManagement.configs.databaseConfigurations.DataSourceContext;
import com.monkey.apiManagement.configs.databaseConfigurations.DatabaseEnvironment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EndpointPermissionDaoTest {
    @Autowired
    private EndpointPermissionDao endpointPermissionDao;

    @Before
    public void initialize() {
        DataSourceContext.setCurrentDatasource(DatabaseEnvironment.QA);
    }

    @After
    public void cleanUp() {
        DataSourceContext.clear();
    }

    @Test
    public void testInsertPermissionsForAuth() {
        boolean result = false;
        try {
            result = endpointPermissionDao.insertPermissionsForAuth(373);
            Assert.assertTrue(result);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            if (result) {
                try {
                    boolean deleteResult = endpointPermissionDao.deleteAllPermissionsForAuth(373);
                    if (!deleteResult) {
                        Assert.fail("Nothing has deleted.");
                    }
                } catch (Exception e) {
                    Assert.fail(e.getMessage());
                }
            }
        }
    }
}
