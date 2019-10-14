package com.sinocarbon.test.client.service.impl;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.pojo.TDept;
import com.sinocarbon.test.client.dao.TDeptMapper;
import com.sinocarbon.test.client.service.TDeptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */
@Service
public class TDeptServiceImpl extends ServiceImpl<TDeptMapper, TDept> implements TDeptService {

	public TDept insertDept(TDept dept)
	{
		baseMapper.insert(dept);
		return dept;
	};
}
