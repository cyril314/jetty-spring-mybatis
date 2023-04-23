package com.fit.dao;

import com.fit.base.BaseCrudDao;
import com.fit.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTO 
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
@Mapper
public interface SysUserDao extends BaseCrudDao<SysUser> {
}