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
import com.ruoyi.algorithm.domain.AlgorithmPath;
import com.ruoyi.algorithm.service.IAlgorithmPathService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 加工路径模块Controller
 * 
 * @author mzj
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/algorithm/path")
public class AlgorithmPathController extends BaseController
{
    @Autowired
    private IAlgorithmPathService algorithmPathService;

    /**
     * 查询加工路径模块列表
     */
    @PreAuthorize("@ss.hasPermi('algorithm:path:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlgorithmPath algorithmPath)
    {
        startPage();
        List<AlgorithmPath> list = algorithmPathService.selectAlgorithmPathList(algorithmPath);
        return getDataTable(list);
    }

    /**
     * 导出加工路径模块列表
     */
    @PreAuthorize("@ss.hasPermi('algorithm:path:export')")
    @Log(title = "加工路径模块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlgorithmPath algorithmPath)
    {
        List<AlgorithmPath> list = algorithmPathService.selectAlgorithmPathList(algorithmPath);
        ExcelUtil<AlgorithmPath> util = new ExcelUtil<AlgorithmPath>(AlgorithmPath.class);
        util.exportExcel(response, list, "加工路径模块数据");
    }

    /**
     * 获取加工路径模块详细信息
     */
    @PreAuthorize("@ss.hasPermi('algorithm:path:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(algorithmPathService.selectAlgorithmPathById(id));
    }

    /**
     * 新增加工路径模块
     */
    @PreAuthorize("@ss.hasPermi('algorithm:path:add')")
    @Log(title = "加工路径模块", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlgorithmPath algorithmPath)
    {
        return toAjax(algorithmPathService.insertAlgorithmPath(algorithmPath));
    }

    /**
     * 修改加工路径模块
     */
    @PreAuthorize("@ss.hasPermi('algorithm:path:edit')")
    @Log(title = "加工路径模块", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlgorithmPath algorithmPath)
    {
        return toAjax(algorithmPathService.updateAlgorithmPath(algorithmPath));
    }

    /**
     * 删除加工路径模块
     */
    @PreAuthorize("@ss.hasPermi('algorithm:path:remove')")
    @Log(title = "加工路径模块", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(algorithmPathService.deleteAlgorithmPathByIds(ids));
    }
}
