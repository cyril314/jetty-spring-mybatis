package com.fit.controller;

import com.fit.base.PatternAndView;
import com.fit.util.SystemUtil;
import com.fit.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author AIM
 * @Des
 * @DATE 2021/6/18
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String main(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails != null) {
			model.addAttribute("users", userDetails);
		}
		return "admin/main";
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("================ 后台首页 ================");
		ModelAndView pav = new PatternAndView("admin/welcome", request, response);

		pav.addObject("os", SystemUtil.getOsInfo());
		pav.addObject("port", WebUtil.getPort(request));
		return pav;
	}
}
