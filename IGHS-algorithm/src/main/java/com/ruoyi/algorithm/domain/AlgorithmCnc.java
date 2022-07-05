package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 数控系统模块对象 algorithm_cnc
 * 
 * @author mzj
 * @date 2022-04-20
 */
public class AlgorithmCnc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 数控系统名称 */
    @Excel(name = "数控系统名称")
    private String cncName;

    /** 数控系统参数 */
    @Excel(name = "数控系统参数")
    private String cncParam;

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
    public void setCncName(String cncName) 
    {
        this.cncName = cncName;
    }

    public String getCncName() 
    {
        return cncName;
    }
    public void setCncParam(String cncParam) 
    {
        this.cncParam = cncParam;
    }

    public String getCncParam() 
    {
        return cncParam;
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
            .append("cncName", getCncName())
            .append("cncParam", getCncParam())
            .append("notes", getNotes())
            .toString();
    }
}
