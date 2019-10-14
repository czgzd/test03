package com.sinocarbon.test.client.service;

import com.sinocarbon.test.client.pojo.TDept;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */
public interface TDeptService extends IService<TDept> {
	public TDept insertDept(TDept dept);
}
