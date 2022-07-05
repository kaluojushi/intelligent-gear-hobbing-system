package com.ruoyi.device.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机床库对象 device_machine
 * 
 * @author mzj
 * @date 2022-04-20
 */
public class DeviceMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 机床型号 */
    @Excel(name = "机床型号")
    private String machineName;

    /** 机床描述 */
    @Excel(name = "机床描述")
    private String machineDescribe;

    /** 最大工件外径(mm) */
    @Excel(name = "最大工件外径(mm)")
    private String maxWorkpieceDiameter;

    /** 最大工件模数(mm) */
    @Excel(name = "最大工件模数(mm)")
    private String maxWorkpieceModulus;

    /** 数控系统型号 */
    @Excel(name = "数控系统型号")
    private Long cncId;

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
    public void setMachineName(String machineName) 
    {
        this.machineName = machineName;
    }

    public String getMachineName() 
    {
        return machineName;
    }
    public void setMachineDescribe(String machineDescribe) 
    {
        this.machineDescribe = machineDescribe;
    }

    public String getMachineDescribe() 
    {
        return machineDescribe;
    }
    public void setMaxWorkpieceDiameter(String maxWorkpieceDiameter) 
    {
        this.maxWorkpieceDiameter = maxWorkpieceDiameter;
    }

    public String getMaxWorkpieceDiameter() 
    {
        return maxWorkpieceDiameter;
    }
    public void setMaxWorkpieceModulus(String maxWorkpieceModulus) 
    {
        this.maxWorkpieceModulus = maxWorkpieceModulus;
    }

    public String getMaxWorkpieceModulus() 
    {
        return maxWorkpieceModulus;
    }
    public void setCncId(Long cncId) 
    {
        this.cncId = cncId;
    }

    public Long getCncId() 
    {
        return cncId;
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
            .append("machineName", getMachineName())
            .append("machineDescribe", getMachineDescribe())
            .append("maxWorkpieceDiameter", getMaxWorkpieceDiameter())
            .append("maxWorkpieceModulus", getMaxWorkpieceModulus())
            .append("cncId", getCncId())
            .append("notes", getNotes())
            .toString();
    }
}
