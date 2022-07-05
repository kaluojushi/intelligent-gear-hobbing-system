package com.ruoyi.knowledge.service;

import java.util.List;
import com.ruoyi.knowledge.domain.KnowledgeCases;

/**
 * 加工实例库Service接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface IKnowledgeCasesService 
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
     * 批量删除加工实例库
     * 
     * @param ids 需要删除的加工实例库主键集合
     * @return 结果
     */
    public int deleteKnowledgeCasesByIds(Long[] ids);

    /**
     * 删除加工实例库信息
     * 
     * @param id 加工实例库主键
     * @return 结果
     */
    public int deleteKnowledgeCasesById(Long id);
}
