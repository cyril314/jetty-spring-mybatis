package org.mybatis.generator.ext.comment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 代码注释生成器扩展程序 增加细粒度的注释控制，为每个注释控制设置默认值 修改suppressDate的默认值为true
 */
public class CommentGeneratorExt extends DefaultCommentGenerator {

	/**
	 * The properties.
	 */
	private Properties properties;
	/**
	 * The suppress date.
	 */
	private boolean suppressDate = true;
	/**
	 * The suppress all comments.
	 */
	private boolean suppressAllComments = false;
	/**
	 * MAPPER文件中的element是否增加注释
	 */
	private boolean suppressElementComments = true;
	/**
	 * MAPPER文件中的根结点是否增加注释
	 */
	private boolean suppressRootElementComments = true;
	/**
	 * java 类是否增加注释
	 */
	private boolean suppressClassComments = false;
	/**
	 * field是否增加注释
	 */
	private boolean suppressFieldComments = true;
	/**
	 * 数据表字段对应的field是否增加注释
	 */
	private boolean suppressColumnFieldComments = false;
	/**
	 * method是否增加注释
	 */
	private boolean suppressMethodComments = true;
	/**
	 * getter方法是否增加注释
	 */
	private boolean suppressGetterComments = true;
	/**
	 * setter方法是否增加注释
	 */
	private boolean suppressSetterComments = true;

	/**
	 * Instantiates a new default comment generator.
	 */
	public CommentGeneratorExt() {
		super();
		properties = new Properties();
	}

	public void addConfigurationProperties(Properties properties) {
		super.addConfigurationProperties(properties);
		this.properties.putAll(properties);
		suppressElementComments = isTrue(properties.getProperty("suppressElementComments", String.valueOf(suppressElementComments)));
		suppressRootElementComments = isTrue(properties.getProperty("suppressRootElementComments", String.valueOf(suppressRootElementComments)));
		suppressClassComments = isTrue(properties.getProperty("suppressClassComments", String.valueOf(suppressClassComments)));
		suppressFieldComments = isTrue(properties.getProperty("suppressFieldComments", String.valueOf(suppressFieldComments)));
		suppressMethodComments = isTrue(properties.getProperty("suppressMethodComments", String.valueOf(suppressMethodComments)));
		suppressGetterComments = isTrue(properties.getProperty("suppressGetterComments", String.valueOf(suppressGetterComments)));
		suppressSetterComments = isTrue(properties.getProperty("suppressSetterComments", String.valueOf(suppressSetterComments)));
		suppressColumnFieldComments = isTrue(properties.getProperty("suppressColumnFieldComments", String.valueOf(suppressColumnFieldComments)));
		suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, String.valueOf(suppressAllComments)));
		suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE, String.valueOf(suppressDate)));
	}

	protected String getDateString() {
		if (suppressDate) {
			return null;
		} else {
			return new Date().toString();
		}
	}

	/*
	 * 生成的注释位于文件最顶端
	 */
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		super.addJavaFileComment(compilationUnit);
	}

	/**
	 * 公共注释头
	 */
	private void commonJavaDoc(JavaElement javaElement) {
		javaElement.addJavaDocLine("/**");
		javaElement.addJavaDocLine(" * @AUTO ");
		javaElement.addJavaDocLine(" * @DATE " + new SimpleDateFormat("yyyy年M月d日HH:mm:ss").format(new Date()));
		javaElement.addJavaDocLine(" * @Author " + properties.getProperty("author"));
		javaElement.addJavaDocLine(" * @Version " + properties.getProperty("version"));
		javaElement.addJavaDocLine(" */");
	}

	public void addComment(XmlElement xmlElement) {
		if (suppressElementComments) {
			return;
		}
		super.addComment(xmlElement);
	}

	public void addRootComment(XmlElement rootElement) {
		// add no document level comments by default
		if (suppressRootElementComments) {
			return;
		}
		super.addComment(rootElement);
	}

	@Override
	protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
		// super.addJavadocTag(javaElement, markAsDoNotDelete);
		commonJavaDoc(javaElement);
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		if (suppressClassComments) {
			return;
		}
		addClassComment(innerClass, introspectedTable, false);
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		if (suppressClassComments || suppressAllComments) {
			return;
		}
		commonJavaDoc(innerClass);
		super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
	}

	@Override
	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
		if (suppressClassComments) {
			return;
		}
		// super.addEnumComment(innerEnum, introspectedTable);
		commonJavaDoc(innerEnum);
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if (suppressColumnFieldComments || suppressAllComments) {
			return;
		}
		String remark = introspectedColumn.getRemarks();
		String columnName = introspectedColumn.getActualColumnName();
		List<IntrospectedColumn> primaryKey = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn pk : primaryKey) {
			if (columnName.equals(pk.getActualColumnName())) {
				remark += " (主健ID)";
				continue; // 主健属性上无需生明可选项跟必填项介绍
			}
		}
		String defaultValue = introspectedColumn.getDefaultValue();
		remark += null != defaultValue ? "  (默认值为: " + defaultValue + ")" : " (无默认值)";
		field.addJavaDocLine("/** " + remark + " */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		if (suppressFieldComments || suppressAllComments) {
			return;
		}
		super.addFieldComment(field, introspectedTable);
	}

	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		if (suppressMethodComments) {
			return;
		}
		super.addGeneralMethodComment(method, introspectedTable);
	}

	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if (suppressGetterComments || suppressAllComments) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		method.addJavaDocLine("/**"); //$NON-NLS-1$
		sb.append(" * This method returns the value of the database column "); //$NON-NLS-1$
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		method.addJavaDocLine(" *"); //$NON-NLS-1$

		sb.setLength(0);
		sb.append(" * @return the value of "); //$NON-NLS-1$
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		addJavadocTag(method, false);

		method.addJavaDocLine(" */"); //$NON-NLS-1$
	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if (suppressSetterComments || suppressAllComments) {
			return;
		}

		method.addJavaDocLine("/**"); //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		sb.append(" * This method sets the value of the database column "); //$NON-NLS-1$
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());

		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" *"); //$NON-NLS-1$

		Parameter parm = method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param "); //$NON-NLS-1$
		sb.append(parm.getName());
		sb.append(" the value for "); //$NON-NLS-1$
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append('.');
		sb.append(introspectedColumn.getActualColumnName());
		method.addJavaDocLine(sb.toString());

		addJavadocTag(method, false);

		method.addJavaDocLine(" */"); //$NON-NLS-1$
	}
}
