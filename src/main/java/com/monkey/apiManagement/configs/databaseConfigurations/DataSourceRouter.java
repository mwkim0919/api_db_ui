package com.monkey.apiManagement.configs.databaseConfigurations;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public final class DataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getCurrentDatasource();
    }
}
