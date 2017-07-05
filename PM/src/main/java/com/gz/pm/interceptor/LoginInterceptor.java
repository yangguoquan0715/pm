package com.gz.pm.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gz.pm.constant.Config;
import com.gz.pm.pojo.vo.PmUserVo;
import com.gz.pm.util.ResourcesUtil;

/**
 * 
 * @author YQ-YGQ
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 校验用户身份是否合法
		HttpSession session = request.getSession();
		PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
		if (activeUser != null) {
			// 用户已经登陆，放行
			return true;
		}

		// 校验用户访问是否是公开资源 地址
		List<String> open_urls = ResourcesUtil.gekeyList(Config.ANONYMOUS_ACTIONS);

		// 用户访问的url
		String url = request.getRequestURI();
		for (String open_url : open_urls) {
			if (url.indexOf(open_url) >= 0) {
				// 如果访问的是公开 地址则放行
				return true;
			}
		}
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		response.sendRedirect(basePath+"login.action");

		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
