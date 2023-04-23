package com.fit.base;

import java.util.ArrayList;
import java.util.List;

/**
 * tree 插件的节点
 */
public class TreeNode {
	/**
	 * 父节点ID
	 */
	private Long parentId;

	/**
	 * 节点id
	 */
	private Long id;

	/**
	 * 节点名称
	 */
	private String text;

	/**
	 * 节点的图标样式
	 */
	private String cls;

	/**
	 * 是否默认展开
	 */
	private boolean expanded = false;

	/**
	 * 是否默认选中
	 */
	private boolean checked = false;

	/**
	 * 叶子节点
	 */
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}
