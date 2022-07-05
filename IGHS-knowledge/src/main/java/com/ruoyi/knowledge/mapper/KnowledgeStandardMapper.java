package com.ruoyi.knowledge.mapper;

import java.util.List;
import com.ruoyi.knowledge.domain.KnowledgeStandard;

/**
 * 标准参数库Mapper接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface KnowledgeStandardMapper 
{
    /**
     * 查询标准参数库
     * 
     * @param id 标准参数库主键
     * @return 标准参数库
     */
    public KnowledgeStandard selectKnowledgeStandardById(Long id);

    /**
     * 查询标准参数库列表
     * 
     * @param knowledgeStandard 标准参数库
     * @return 标准参数库集合
     */
    public List<KnowledgeStandard> selectKnowledgeStandardList(KnowledgeStandard knowledgeStandard);

    /**
     * 新增标准参数库
     * 
     * @param knowledgeStandard 标准参数库
     * @return 结果
     */
    public int insertKnowledgeStandard(KnowledgeStandard knowledgeStandard);

    /**
     * 修改标准参数库
     * 
     * @param knowledgeStandard 标准参数库
     * @return 结果
     */
    public int updateKnowledgeStandard(KnowledgeStandard knowledgeStandard);

    /**
     * 删除标准参数库
     * 
     * @param id 标准参数库主键
     * @return 结果
     */
    public int deleteKnowledgeStandardById(Long id);

    /**
     * 批量删除标准参数库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKnowledgeStandardByIds(Long[] ids);
}
