package com.ruoyi.device.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.DeviceMachineMapper;
import com.ruoyi.device.domain.DeviceMachine;
import com.ruoyi.device.service.IDeviceMachineService;

/**
 * 机床库Service业务层处理
 * 
 * @author mzj
 * @date 2022-04-20
 */
@Service
public class DeviceMachineServiceImpl implements IDeviceMachineService 
{
    @Autowired
    private DeviceMachineMapper deviceMachineMapper;

    /**
     * 查询机床库
     * 
     * @param id 机床库主键
     * @return 机床库
     */
    @Override
    public DeviceMachine selectDeviceMachineById(Long id)
    {
        return deviceMachineMapper.selectDeviceMachineById(id);
    }

    /**
     * 查询机床库列表
     * 
     * @param deviceMachine 机床库
     * @return 机床库
     */
    @Override
    public List<DeviceMachine> selectDeviceMachineList(DeviceMachine deviceMachine)
    {
        return deviceMachineMapper.selectDeviceMachineList(deviceMachine);
    }

    /**
     * 新增机床库
     * 
     * @param deviceMachine 机床库
     * @return 结果
     */
    @Override
    public int insertDeviceMachine(DeviceMachine deviceMachine)
    {
        return deviceMachineMapper.insertDeviceMachine(deviceMachine);
    }

    /**
     * 修改机床库
     * 
     * @param deviceMachine 机床库
     * @return 结果
     */
    @Override
    public int updateDeviceMachine(DeviceMachine deviceMachine)
    {
        return deviceMachineMapper.updateDeviceMachine(deviceMachine);
    }

    /**
     * 批量删除机床库
     * 
     * @param ids 需要删除的机床库主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMachineByIds(Long[] ids)
    {
        return deviceMachineMapper.deleteDeviceMachineByIds(ids);
    }

    /**
     * 删除机床库信息
     * 
     * @param id 机床库主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMachineById(Long id)
    {
        return deviceMachineMapper.deleteDeviceMachineById(id);
    }
}
