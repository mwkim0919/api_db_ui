package com.monkey.apiManagement.configs.databaseConfigurations;

public final class DataSourceContext {
    private static ThreadLocal<DatabaseEnvironment> currentDatasource = new ThreadLocal<>();

    public static void setCurrentDatasource(DatabaseEnvironment databaseEnvironment) {
        currentDatasource.set(databaseEnvironment);
    }

    public static DatabaseEnvironment getCurrentDatasource() {
        return currentDatasource.get();
    }

    public static void clear() {
        currentDatasource.remove();
    }
}
