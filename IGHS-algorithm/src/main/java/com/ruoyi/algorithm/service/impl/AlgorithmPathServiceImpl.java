package com.ruoyi.algorithm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.algorithm.mapper.AlgorithmPathMapper;
import com.ruoyi.algorithm.domain.AlgorithmPath;
import com.ruoyi.algorithm.service.IAlgorithmPathService;

/**
 * 加工路径模块Service业务层处理
 * 
 * @author mzj
 * @date 2022-04-20
 */
@Service
public class AlgorithmPathServiceImpl implements IAlgorithmPathService 
{
    @Autowired
    private AlgorithmPathMapper algorithmPathMapper;

    /**
     * 查询加工路径模块
     * 
     * @param id 加工路径模块主键
     * @return 加工路径模块
     */
    @Override
    public AlgorithmPath selectAlgorithmPathById(Long id)
    {
        return algorithmPathMapper.selectAlgorithmPathById(id);
    }

    /**
     * 查询加工路径模块列表
     * 
     * @param algorithmPath 加工路径模块
     * @return 加工路径模块
     */
    @Override
    public List<AlgorithmPath> selectAlgorithmPathList(AlgorithmPath algorithmPath)
    {
        return algorithmPathMapper.selectAlgorithmPathList(algorithmPath);
    }

    /**
     * 新增加工路径模块
     * 
     * @param algorithmPath 加工路径模块
     * @return 结果
     */
    @Override
    public int insertAlgorithmPath(AlgorithmPath algorithmPath)
    {
        return algorithmPathMapper.insertAlgorithmPath(algorithmPath);
    }

    /**
     * 修改加工路径模块
     * 
     * @param algorithmPath 加工路径模块
     * @return 结果
     */
    @Override
    public int updateAlgorithmPath(AlgorithmPath algorithmPath)
    {
        return algorithmPathMapper.updateAlgorithmPath(algorithmPath);
    }

    /**
     * 批量删除加工路径模块
     * 
     * @param ids 需要删除的加工路径模块主键
     * @return 结果
     */
    @Override
    public int deleteAlgorithmPathByIds(Long[] ids)
    {
        return algorithmPathMapper.deleteAlgorithmPathByIds(ids);
    }

    /**
     * 删除加工路径模块信息
     * 
     * @param id 加工路径模块主键
     * @return 结果
     */
    @Override
    public int deleteAlgorithmPathById(Long id)
    {
        return algorithmPathMapper.deleteAlgorithmPathById(id);
    }
}
