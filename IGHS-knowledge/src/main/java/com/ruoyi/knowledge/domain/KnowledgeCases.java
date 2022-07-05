package com.ruoyi.knowledge.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 加工实例库对象 knowledge_cases
 * 
 * @author mzj
 * @date 2022-04-20
 */
public class KnowledgeCases extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 实例编号 */
    @Excel(name = "实例编号")
    private String caseName;

    /** 加工信息 */
    @Excel(name = "加工信息")
    private String caseInfo;

    /** 加工代码 */
    @Excel(name = "加工代码")
    private String caseCode;

    /** 加工时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "加工时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date caseProcessTime;
    private Date caseProcessTimeLimit;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date caseEntryTime;
    private Date caseEntryTimeLimit;

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
    public void setCaseName(String caseName) 
    {
        this.caseName = caseName;
    }

    public String getCaseName() 
    {
        return caseName;
    }
    public void setCaseInfo(String caseInfo) 
    {
        this.caseInfo = caseInfo;
    }

    public String getCaseInfo() 
    {
        return caseInfo;
    }
    public void setCaseCode(String caseCode) 
    {
        this.caseCode = caseCode;
    }

    public String getCaseCode() 
    {
        return caseCode;
    }
    public void setCaseProcessTime(Date caseProcessTime) 
    {
        this.caseProcessTime = caseProcessTime;
        this.caseProcessTimeLimit = new Date(this.caseProcessTime.getTime() + 86400000);
    }

    public Date getCaseProcessTime() 
    {
        return caseProcessTime;
    }
    public void setCaseEntryTime(Date caseEntryTime) 
    {
        this.caseEntryTime = caseEntryTime;
        this.caseEntryTimeLimit = new Date(this.caseEntryTime.getTime() + 86400000);
    }

    public Date getCaseEntryTime() 
    {
        return caseEntryTime;
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
            .append("caseName", getCaseName())
            .append("caseInfo", getCaseInfo())
            .append("caseCode", getCaseCode())
            .append("caseProcessTime", getCaseProcessTime())
            .append("caseEntryTime", getCaseEntryTime())
            .append("notes", getNotes())
            .toString();
    }
}
