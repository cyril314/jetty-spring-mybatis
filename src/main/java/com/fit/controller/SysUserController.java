package com.fit.controller;

import com.fit.base.BaseController;
import com.fit.base.PatternAndView;
import com.fit.bean.SysUser;
import com.fit.security.JettyPasswordEncoder;
import com.fit.service.SysUserService;
import com.fit.util.DateUtils;
import com.fit.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author aim
 * @date 2017-11-10 22:43:51
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("/list")
	public String index() {
		return "sysUser/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public void findList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = basePageParam(request, response);
		List<SysUser> list = sysUserService.findList(params);
		int findCount = sysUserService.findCount(params);
		params.clear();
		params.put("state", 202);
		if (list.size() > 0) {
			params.put("state", 200);
		}
		params.put("list", list);
		params.put("count", findCount);
		writeObjectAsJson(response, params);
	}

	/**
	 * 添加/编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, Long id) {
		ModelAndView pav = new PatternAndView("sysUser/edit", request, response);
		SysUser sub = new SysUser();
		if (WebUtil.isNotEmpty(id)) {
			sub = sysUserService.getById(id);
		}
		pav.addObject("sub", sub);
		return pav;
	}

	/**
	 * 保存/更新
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(HttpServletResponse response, SysUser sub) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isEmpty(sub.getId())) {
			sub.setCreatdate(DateUtils.nowadays());
			sub.setPassword(new JettyPasswordEncoder().encode(sub.getPassword()));
			sysUserService.save(sub);
			msg = "保存成功";
			code = "200";
		} else {
			int update = sysUserService.update(sub);
			if (update == 1) {
				msg = "更新成功";
				code = "200";
			} else {
				msg = "更新失败";
				code = "100";
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		map.put("code", code);
		writeJson(response, getJsonObject(map));
	}

	/**
	 * 状态设置按钮
	 */
	@RequestMapping(value = "/enabled", method = RequestMethod.POST)
	@ResponseBody
	public void enabled(HttpServletResponse response, Long id) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isNotEmpty(id)) {
			SysUser sub = sysUserService.getById(id);
			if (sub.getEnabled())
				sub.setEnabled(false);
			else
				sub.setEnabled(true);
			int update = sysUserService.update(sub);
			if (update == 1) {
				msg = "请求成功";
				code = "200";
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		map.put("code", code);
		writeJson(response, getJsonObject(map));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response, Long id) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isNotEmpty(id)) {
			int del = sysUserService.delete(id);
			if (del == 1) {
				msg = "请求成功";
				code = "200";
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		map.put("code", code);
		writeJson(response, getJsonObject(map));
	}
}
