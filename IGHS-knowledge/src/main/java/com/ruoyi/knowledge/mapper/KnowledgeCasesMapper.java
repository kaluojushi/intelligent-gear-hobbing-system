package com.ruoyi.knowledge.mapper;

import java.util.List;
import com.ruoyi.knowledge.domain.KnowledgeCases;

/**
 * 加工实例库Mapper接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface KnowledgeCasesMapper 
{
    /**
     * 查询加工实例库
     * 
     * @param id 加工实例库主键
     * @return 加工实例库
     */
    public KnowledgeCases selectKnowledgeCasesById(Long id);

    /**
     * 查询加工实例库列表
     * 
     * @param knowledgeCases 加工实例库
     * @return 加工实例库集合
     */
    public List<KnowledgeCases> selectKnowledgeCasesList(KnowledgeCases knowledgeCases);

    /**
     * 新增加工实例库
     * 
     * @param knowledgeCases 加工实例库
     * @return 结果
     */
    public int insertKnowledgeCases(KnowledgeCases knowledgeCases);

    /**
     * 修改加工实例库
     * 
     * @param knowledgeCases 加工实例库
     * @return 结果
     */
    public int updateKnowledgeCases(KnowledgeCases knowledgeCases);

    /**
     * 删除加工实例库
     * 
     * @param id 加工实例库主键
     * @return 结果
     */
    public int deleteKnowledgeCasesById(Long id);

    /**
     * 批量删除加工实例库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKnowledgeCasesByIds(Long[] ids);
}
