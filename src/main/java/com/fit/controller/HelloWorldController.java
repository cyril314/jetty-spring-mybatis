package com.fit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fit.base.PatternAndView;

@Controller
public class HelloWorldController {

	@RequestMapping({ "/", "/index" })
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new PatternAndView("index", request, response);
	}

	@RequestMapping("/login")
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			if (auth != null) {
				UserDetails userDetails = (UserDetails) auth.getPrincipal();
				if (userDetails != null) {
					return "admin/main";
				}
			}
		} catch (Exception e) {
			return "redirect:/logout";
		}
		return "login";
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "login";
	}
}
