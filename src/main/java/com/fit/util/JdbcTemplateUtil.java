package com.fit.util;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @AUTO Spring JDBC 查询工具
 * @Author AIM
 * @DATE 2019/5/31
 */
public class JdbcTemplateUtil {

	/**
	 * 查询数据返回List<Map>
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param obj
	 *            不定参数
	 */
	public static List<Map<String, Object>> queryForListMap(JdbcTemplate jdbcTemplate, String sql, Object... obj) {
		return jdbcTemplate.queryForList(sql, obj);
	}

	/**
	 * 查询数据返回List<Map>
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数集合
	 */
	public static List<Map<String, Object>> queryForListMap(JdbcTemplate jdbcTemplate, String sql, List<String> params) {
		return jdbcTemplate.queryForList(sql, ConverterUtils.list2Array(params));
	}

	/**
	 * 查询对象列表(基础类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param javaBean
	 *            返回基础类型对象
	 * @param obj
	 *            不定参数
	 */
	public static <T> List<T> queryForList(JdbcTemplate jdbcTemplate, String sql, Class<T> javaBean, Object... obj) {
		return jdbcTemplate.queryForList(sql, obj, javaBean);
	}

	/**
	 * 查询对象列表(实体类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数集合
	 * @param javaBean
	 *            返回实体类型对象
	 */
	public static <T> List<T> queryForList(JdbcTemplate jdbcTemplate, String sql, List<?> params, Class<T> javaBean) {
		return jdbcTemplate.queryForList(sql, ConverterUtils.list2Array(params), javaBean);
	}

	/**
	 * 查询对象列表(实体类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param javaBean
	 *            返回实体类型对象
	 */
	public static <T> List<T> queryForListBean(JdbcTemplate jdbcTemplate, String sql, Class<T> javaBean) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(javaBean));
	}

	/**
	 * 查询对象列表(实体类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数集合
	 * @param javaBean
	 *            返回实体类型对象
	 */
	public static <T> List<T> queryForListBean(JdbcTemplate jdbcTemplate, String sql, List<?> params, Class<T> javaBean) {
		return jdbcTemplate.query(sql, ConverterUtils.list2Array(params), new BeanPropertyRowMapper<T>(javaBean));
	}

	/**
	 * 查询对象列表(实体类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param javaBean
	 *            返回实体类型对象
	 * @param obj
	 *            不定参数
	 */
	public static <T> List<T> queryForListBean(JdbcTemplate jdbcTemplate, String sql, Class<T> javaBean, Object... obj) {
		return jdbcTemplate.query(sql, obj, BeanPropertyRowMapper.newInstance(javaBean));
	}

	/**
	 * 查询指定对象结果(基础类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param javaBean
	 *            返回基础类型对象
	 * @param obj
	 *            不定参数
	 */
	public static <T> T queryForObject(JdbcTemplate jdbcTemplate, String sql, Class<T> javaBean, Object... obj) {
		return queryForObject(jdbcTemplate, sql, obj, javaBean);
	}

	/**
	 * 查询指定对象结果(基础类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数集合
	 * @param javaBean
	 *            返回基础类型对象
	 */
	public static <T> T queryForObject(JdbcTemplate jdbcTemplate, String sql, List<?> params, Class<T> javaBean) {
		return queryForObject(jdbcTemplate, sql, ConverterUtils.list2Array(params), javaBean);
	}

	/**
	 * 查询指定对象结果(基础类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数集合
	 * @param javaBean
	 *            返回基础类型对象
	 */
	public static <T> T queryForObject(JdbcTemplate jdbcTemplate, String sql, Object[] params, Class<T> javaBean) {
		return jdbcTemplate.queryForObject(sql, params, javaBean);
	}

	/**
	 * 查询指定对象结果(实体类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param javaBean
	 *            返回实体类型对象
	 * @param obj
	 *            不定参数
	 */
	public static <T> T queryForBean(JdbcTemplate jdbcTemplate, String sql, Class<T> javaBean, Object... obj) {
		return queryForBean(jdbcTemplate, sql, obj, javaBean);
	}

	/**
	 * 查询指定对象结果(实体类型)
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数集合
	 * @param javaBean
	 *            返回实体类型对象
	 */
	public static <T> T queryForBean(JdbcTemplate jdbcTemplate, String sql, Object[] params, Class<T> javaBean) {
		return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<T>(javaBean));
	}

	/**
	 * 更新操作
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param sql
	 *            操作语句
	 * @param obj
	 *            不定参数
	 */
	public static int update(JdbcTemplate jdbcTemplate, String sql, Object... obj) {
		return jdbcTemplate.update(sql, obj);
	}

	/**
	 * 更新操作
	 * 
	 * @param jdbcTemplate
	 *            SpringJdbc对象
	 * @param params
	 *            参数集合
	 * @param sql
	 *            操作语句
	 */
	public static int update(JdbcTemplate jdbcTemplate, Object[] params, String sql) {
		return jdbcTemplate.update(sql, params);
	}
}
