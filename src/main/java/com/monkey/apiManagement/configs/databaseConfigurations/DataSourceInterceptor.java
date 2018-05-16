package com.monkey.apiManagement.configs.databaseConfigurations;

import com.monkey.apiManagement.exceptions.DataSourceException;
import com.monkey.apiManagement.exceptions.NotAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws NotAuthorizedException, DataSourceException {
        String environment = request.getHeader("ENV");
        if ("QA".equalsIgnoreCase(environment)) {
            DataSourceContext.setCurrentDatasource(DatabaseEnvironment.QA);
        } else if ("STAGE".equalsIgnoreCase(environment)) {
            DataSourceContext.setCurrentDatasource(DatabaseEnvironment.STAGE);
        } else if ("PRODUCTION".equalsIgnoreCase(environment)) {
            if (request.isUserInRole("ROLE_ADMIN")) {
                DataSourceContext.setCurrentDatasource(DatabaseEnvironment.PRODUCTION);
            } else {
                throw new NotAuthorizedException("User is not authorized.");
            }
        } else {
            throw new DataSourceException("A valid value for header, ENV, must be provided.");
        }
        return true;
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        DataSourceContext.clear();
    }
}
