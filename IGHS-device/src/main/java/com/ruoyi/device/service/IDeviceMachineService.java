package com.ruoyi.device.service;

import java.util.List;
import com.ruoyi.device.domain.DeviceMachine;

/**
 * 机床库Service接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface IDeviceMachineService 
{
    /**
     * 查询机床库
     * 
     * @param id 机床库主键
     * @return 机床库
     */
    public DeviceMachine selectDeviceMachineById(Long id);

    /**
     * 查询机床库列表
     * 
     * @param deviceMachine 机床库
     * @return 机床库集合
     */
    public List<DeviceMachine> selectDeviceMachineList(DeviceMachine deviceMachine);

    /**
     * 新增机床库
     * 
     * @param deviceMachine 机床库
     * @return 结果
     */
    public int insertDeviceMachine(DeviceMachine deviceMachine);

    /**
     * 修改机床库
     * 
     * @param deviceMachine 机床库
     * @return 结果
     */
    public int updateDeviceMachine(DeviceMachine deviceMachine);

    /**
     * 批量删除机床库
     * 
     * @param ids 需要删除的机床库主键集合
     * @return 结果
     */
    public int deleteDeviceMachineByIds(Long[] ids);

    /**
     * 删除机床库信息
     * 
     * @param id 机床库主键
     * @return 结果
     */
    public int deleteDeviceMachineById(Long id);
}
