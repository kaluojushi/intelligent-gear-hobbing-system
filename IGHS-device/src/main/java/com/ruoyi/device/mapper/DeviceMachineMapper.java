package com.ruoyi.device.mapper;

import java.util.List;
import com.ruoyi.device.domain.DeviceMachine;

/**
 * 机床库Mapper接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface DeviceMachineMapper 
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
     * 删除机床库
     * 
     * @param id 机床库主键
     * @return 结果
     */
    public int deleteDeviceMachineById(Long id);

    /**
     * 批量删除机床库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceMachineByIds(Long[] ids);
}
