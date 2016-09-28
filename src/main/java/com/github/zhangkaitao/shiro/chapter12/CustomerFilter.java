package com.github.zhangkaitao.shiro.chapter12;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;



/**
 * 自定义403认证过滤器
 * check isAuthenticated for rest api.
 * unAuthenticated to 403
 * Created by zhangdx on 16/5/25.
 */
public class CustomerFilter extends FormAuthenticationFilter {
    private static final Logger logger = Logger.getLogger(CustomerFilter.class);


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        if (subject.isAuthenticated()) {
            logger.debug("authenticated");
        } else {
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            logger.debug("url : " + httpServletRequest.getServletPath());
            logger.debug("unAuthenticated");
//            unAuthenticatedTo403(request, response);
            return true;
        }
        return false;

    }

    /**
     * unAuthenticated to 403
     *
     * @param request
     * @param response
     */
    private void unAuthenticatedTo403(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("");
    }
}
