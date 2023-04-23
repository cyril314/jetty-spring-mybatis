package com.fit.controller;

import com.fit.base.BaseController;
import com.fit.base.PatternAndView;
import com.fit.base.TreeNode;
import com.fit.bean.SysRole;
import com.fit.service.SysRoleService;
import com.fit.service.ZtreeNodeService;
import com.fit.util.FastJsonUtils;
import com.fit.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author aim
 * @date 2017-11-10 22:43:51
 */
@Controller
@RequestMapping("/sysrole")
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private ZtreeNodeService ztreeNodeService;

	@GetMapping("/list")
	public String index() {
		return "sysRole/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public void findList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = basePageParam(request, response);
		List<SysRole> list = sysRoleService.findList(params);
		int findCount = sysRoleService.findCount(params);
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
		ModelAndView pav = new PatternAndView("sysRole/edit", request, response);
		SysRole srb = new SysRole();
		if (WebUtil.isNotEmpty(id)) {
			srb = sysRoleService.getById(id);
		}
		pav.addObject("srb", srb);
		return pav;
	}

	/**
	 * 状态设置按钮
	 */
	@RequestMapping(value = "/enabled", method = RequestMethod.POST)
	@ResponseBody
	public void enabled(HttpServletRequest request, HttpServletResponse response, Long id) {
		String msg = "请求失败";
		String code = "-1";
		if (WebUtil.isNotEmpty(id)) {
			SysRole srb = sysRoleService.getById(id);
			if (srb.getEnabled())
				srb.setEnabled(false);
			else
				srb.setEnabled(true);
			int update = sysRoleService.update(srb);
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
	 * 设置角色页面
	 */
	@RequestMapping("/setAssign")
	public ModelAndView setRoleView(HttpServletRequest request, HttpServletResponse response, String id) {
		ModelAndView pav = new PatternAndView("sysRole/assign", request, response);
		if (WebUtil.isNotEmpty(id)) {
			List<TreeNode> treeNodes = ztreeNodeService.menuZtreeChecked(id);
			pav.addObject("treeNodes", FastJsonUtils.getToJson(treeNodes));
		}
		pav.addObject("roleId", id);
		return pav;
	}

	/**
	 * 保存
	 */
	@PostMapping("/saveAssign")
	@ResponseBody
	public Object saveAssign(final String roleId, String menus) {
		return ztreeNodeService.saveAssign(roleId, menus);
	}
}
