package com.ruoyi.knowledge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 标准参数库对象 knowledge_standard
 * 
 * @author mzj
 * @date 2022-04-20
 */
public class KnowledgeStandard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String standardName;

    /** 参数变量 */
    @Excel(name = "参数变量")
    private String standardParam;

    /** 参数单位 */
    @Excel(name = "参数单位")
    private String standardUnit;

    /** 标准值 */
    @Excel(name = "标准值")
    private String standardValue;

    /** 推荐值 */
    @Excel(name = "推荐值")
    private String standardRecommend;

    /** 其他值 */
    @Excel(name = "其他值")
    private String standardOther;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStandardName(String standardName) 
    {
        this.standardName = standardName;
    }

    public String getStandardName() 
    {
        return standardName;
    }
    public void setStandardParam(String standardParam) 
    {
        this.standardParam = standardParam;
    }

    public String getStandardParam() 
    {
        return standardParam;
    }
    public void setStandardUnit(String standardUnit) 
    {
        this.standardUnit = standardUnit;
    }

    public String getStandardUnit() 
    {
        return standardUnit;
    }
    public void setStandardValue(String standardValue) 
    {
        this.standardValue = standardValue;
    }

    public String getStandardValue() 
    {
        return standardValue;
    }
    public void setStandardRecommend(String standardRecommend) 
    {
        this.standardRecommend = standardRecommend;
    }

    public String getStandardRecommend() 
    {
        return standardRecommend;
    }
    public void setStandardOther(String standardOther) 
    {
        this.standardOther = standardOther;
    }

    public String getStandardOther() 
    {
        return standardOther;
    }
    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("standardName", getStandardName())
            .append("standardParam", getStandardParam())
            .append("standardUnit", getStandardUnit())
            .append("standardValue", getStandardValue())
            .append("standardRecommend", getStandardRecommend())
            .append("standardOther", getStandardOther())
            .append("notes", getNotes())
            .toString();
    }
}
