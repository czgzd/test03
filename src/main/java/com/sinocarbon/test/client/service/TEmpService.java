package com.sinocarbon.test.client.service;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.pojo.TEmp;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */
public interface TEmpService extends IService<TEmp> {

	public TEmp selectById(Integer id);
}
