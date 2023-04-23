package com.fit.controller;

import com.fit.base.BaseController;
import com.fit.base.PatternAndView;
import com.fit.bean.SysResources;
import com.fit.util.DateUtils;
import com.fit.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.fit.service.SysResourcesService;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源表
 *
 * @author aim
 * @date 2017-11-10 22:43:51
 */
@Controller
@RequestMapping("/sysres")
public class SysResourcesController extends BaseController {

	@Autowired
	private SysResourcesService sysResourcesService;

	@GetMapping("/list")
	public String index() {
		return "sysRes/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public void findList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = basePageParam(request, response);
		List<SysResources> list = sysResourcesService.findList(params);
		int findCount = sysResourcesService.findCount(params);
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
		ModelAndView pav = new PatternAndView("sysRes/edit", request, response);
		SysResources srb = new SysResources();
		if (WebUtil.isNotEmpty(id)) {
			srb = sysResourcesService.getById(id);
		}
		pav.addObject("srb", srb);
		return pav;
	}

	/**
	 * 保存/更新
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response, SysResources srb) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isEmpty(srb.getId())) {
			srb.setCreatdate(DateUtils.nowadays());
			sysResourcesService.save(srb);
			msg = "保存成功";
			code = "200";
		} else {
			int update = sysResourcesService.update(srb);
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
	 * 删除
	 */
	@PostMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response, Long id) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isNotEmpty(id)) {
			int del = sysResourcesService.delete(id);
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

	/**
	 * 状态设置按钮
	 */
	@RequestMapping(value = "/enabled", method = RequestMethod.POST)
	@ResponseBody
	public void enabled(HttpServletResponse response, Long id) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isNotEmpty(id)) {
			SysResources srb = sysResourcesService.getById(id);
			if (srb.getEnabled())
				srb.setEnabled(false);
			else
				srb.setEnabled(true);
			int update = sysResourcesService.update(srb);
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
}
