package com.sinocarbon.test.client.dao;

import com.sinocarbon.test.client.pojo.TEmp;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */
public interface TEmpMapper extends BaseMapper<TEmp> {

	TEmp selectById(Integer id);
}
