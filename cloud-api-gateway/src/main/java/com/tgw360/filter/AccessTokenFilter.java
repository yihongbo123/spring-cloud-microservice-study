package com.tgw360.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class AccessTokenFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
//        int i = 10/0;
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());

        String accessToken = request.getParameter("accessToken");
        if (accessToken == null){
            log.warn("access token is empty!!!!");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.set("error.status_code", HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        log.info("access token ok!!!");
        return null;
    }
}
