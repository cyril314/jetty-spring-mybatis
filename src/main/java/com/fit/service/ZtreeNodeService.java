package com.fit.service;

import com.fit.base.AjaxResult;
import com.fit.base.TreeNode;
import com.fit.util.JdbcTemplateUtil;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @AUTO 获取Ztree节点服务
 * @Author AIM
 * @DATE 2019/4/28
 */
@Service
public class ZtreeNodeService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取角色树节点集合
	 */
	public List<TreeNode> menuZtree() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT e.`id`, e.`pid` AS `parentId`, e.`name` AS `text`  FROM `sys_resources` e ");
		List<TreeNode> treeNodes = JdbcTemplateUtil.queryForListBean(jdbcTemplate, sb.toString(), TreeNode.class);

		return treeNodes;
	}

	/**
	 * 设置
	 */
	public List<TreeNode> menuZtreeChecked(String roleId) {
		List<TreeNode> treeNodes = menuZtree();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT e.`id` FROM `sys_resources` e INNER JOIN `sys_role_auth` a ON e.`id`=a.`auth_id` WHERE a.`role_id`=?");
		List<String> lists = this.jdbcTemplate.queryForList(sb.toString(), new Object[]{roleId}, String.class);
		for (TreeNode treeNode : treeNodes) {
			if (lists.contains(treeNode.getId().toString().trim())) {
				treeNode.setChecked(true);
			}
		}
		return buildByRecursive(treeNodes);
	}

	public Object saveAssign(final String roleId, String menus) {
		try {
			StringBuffer sb = new StringBuffer("DELETE FROM `sys_role_auth` WHERE `role_id` = ?");
			this.jdbcTemplate.update(sb.toString(), new Object[]{roleId});
			sb.setLength(0);
			sb.append("INSERT INTO `sys_role_auth` (`auth_id`,`role_id`) VALUES (?,?)");
			final List<String> list = Arrays.asList(menus.split(","));
			this.jdbcTemplate.batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {
				@Override
				public int getBatchSize() {
					return list.size();
				}

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, list.get(i));
					ps.setObject(2, roleId);
				}
			});
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("保存权限失败");
		}
	}

	/**
	 * 使用递归方法建树
	 * @param treeNodes
	 */
	public static List<TreeNode> buildByRecursive(List<TreeNode> treeNodes) {
		List<TreeNode> trees = new ArrayList<TreeNode>();
		for (TreeNode treeNode : treeNodes) {
			if ("-1".equals(treeNode.getParentId().toString().trim())) {
				treeNode.setExpanded(true);
				trees.add(findChildren(treeNode, treeNodes, 0));
			}
		}
		return trees;
	}

	/**
	 * 递归查找子节点
	 */
	public static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodes, int root) {
		for (TreeNode it : treeNodes) {
			if (treeNode.getId().toString().trim().equals(it.getParentId().toString().trim())) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<TreeNode>());
				}
				treeNode.getChildren().add(findChildren(it, treeNodes, root + 1));
			}
		}
		return treeNode;
	}
}
