package com.ruoyi.web.controller.algorithm;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.algorithm.domain.AlgorithmCnc;
import com.ruoyi.algorithm.service.IAlgorithmCncService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数控系统模块Controller
 * 
 * @author mzj
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/algorithm/cnc")
public class AlgorithmCncController extends BaseController
{
    @Autowired
    private IAlgorithmCncService algorithmCncService;

    /**
     * 查询数控系统模块列表
     */
    @PreAuthorize("@ss.hasPermi('algorithm:cnc:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlgorithmCnc algorithmCnc)
    {
        startPage();
        List<AlgorithmCnc> list = algorithmCncService.selectAlgorithmCncList(algorithmCnc);
        return getDataTable(list);
    }

    /**
     * 导出数控系统模块列表
     */
    @PreAuthorize("@ss.hasPermi('algorithm:cnc:export')")
    @Log(title = "数控系统模块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlgorithmCnc algorithmCnc)
    {
        List<AlgorithmCnc> list = algorithmCncService.selectAlgorithmCncList(algorithmCnc);
        ExcelUtil<AlgorithmCnc> util = new ExcelUtil<AlgorithmCnc>(AlgorithmCnc.class);
        util.exportExcel(response, list, "数控系统模块数据");
    }

    /**
     * 获取数控系统模块详细信息
     */
    @PreAuthorize("@ss.hasPermi('algorithm:cnc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(algorithmCncService.selectAlgorithmCncById(id));
    }

    /**
     * 新增数控系统模块
     */
    @PreAuthorize("@ss.hasPermi('algorithm:cnc:add')")
    @Log(title = "数控系统模块", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlgorithmCnc algorithmCnc)
    {
        return toAjax(algorithmCncService.insertAlgorithmCnc(algorithmCnc));
    }

    /**
     * 修改数控系统模块
     */
    @PreAuthorize("@ss.hasPermi('algorithm:cnc:edit')")
    @Log(title = "数控系统模块", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlgorithmCnc algorithmCnc)
    {
        return toAjax(algorithmCncService.updateAlgorithmCnc(algorithmCnc));
    }

    /**
     * 删除数控系统模块
     */
    @PreAuthorize("@ss.hasPermi('algorithm:cnc:remove')")
    @Log(title = "数控系统模块", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(algorithmCncService.deleteAlgorithmCncByIds(ids));
    }
}
