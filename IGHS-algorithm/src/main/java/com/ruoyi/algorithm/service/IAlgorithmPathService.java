package com.ruoyi.algorithm.service;

import java.util.List;
import com.ruoyi.algorithm.domain.AlgorithmPath;

/**
 * 加工路径模块Service接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface IAlgorithmPathService 
{
    /**
     * 查询加工路径模块
     * 
     * @param id 加工路径模块主键
     * @return 加工路径模块
     */
    public AlgorithmPath selectAlgorithmPathById(Long id);

    /**
     * 查询加工路径模块列表
     * 
     * @param algorithmPath 加工路径模块
     * @return 加工路径模块集合
     */
    public List<AlgorithmPath> selectAlgorithmPathList(AlgorithmPath algorithmPath);

    /**
     * 新增加工路径模块
     * 
     * @param algorithmPath 加工路径模块
     * @return 结果
     */
    public int insertAlgorithmPath(AlgorithmPath algorithmPath);

    /**
     * 修改加工路径模块
     * 
     * @param algorithmPath 加工路径模块
     * @return 结果
     */
    public int updateAlgorithmPath(AlgorithmPath algorithmPath);

    /**
     * 批量删除加工路径模块
     * 
     * @param ids 需要删除的加工路径模块主键集合
     * @return 结果
     */
    public int deleteAlgorithmPathByIds(Long[] ids);

    /**
     * 删除加工路径模块信息
     * 
     * @param id 加工路径模块主键
     * @return 结果
     */
    public int deleteAlgorithmPathById(Long id);
}
