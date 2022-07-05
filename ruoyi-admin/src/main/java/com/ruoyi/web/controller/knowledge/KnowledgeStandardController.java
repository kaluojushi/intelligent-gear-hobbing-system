package com.ruoyi.web.controller.knowledge;

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
import com.ruoyi.knowledge.domain.KnowledgeStandard;
import com.ruoyi.knowledge.service.IKnowledgeStandardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标准参数库Controller
 * 
 * @author mzj
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/knowledge/standard")
public class KnowledgeStandardController extends BaseController
{
    @Autowired
    private IKnowledgeStandardService knowledgeStandardService;

    /**
     * 查询标准参数库列表
     */
    @PreAuthorize("@ss.hasPermi('knowledge:standard:list')")
    @GetMapping("/list")
    public TableDataInfo list(KnowledgeStandard knowledgeStandard)
    {
        startPage();
        List<KnowledgeStandard> list = knowledgeStandardService.selectKnowledgeStandardList(knowledgeStandard);
        return getDataTable(list);
    }

    /**
     * 导出标准参数库列表
     */
    @PreAuthorize("@ss.hasPermi('knowledge:standard:export')")
    @Log(title = "标准参数库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KnowledgeStandard knowledgeStandard)
    {
        List<KnowledgeStandard> list = knowledgeStandardService.selectKnowledgeStandardList(knowledgeStandard);
        ExcelUtil<KnowledgeStandard> util = new ExcelUtil<KnowledgeStandard>(KnowledgeStandard.class);
        util.exportExcel(response, list, "标准参数库数据");
    }

    /**
     * 获取标准参数库详细信息
     */
    @PreAuthorize("@ss.hasPermi('knowledge:standard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(knowledgeStandardService.selectKnowledgeStandardById(id));
    }

    /**
     * 新增标准参数库
     */
    @PreAuthorize("@ss.hasPermi('knowledge:standard:add')")
    @Log(title = "标准参数库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KnowledgeStandard knowledgeStandard)
    {
        return toAjax(knowledgeStandardService.insertKnowledgeStandard(knowledgeStandard));
    }

    /**
     * 修改标准参数库
     */
    @PreAuthorize("@ss.hasPermi('knowledge:standard:edit')")
    @Log(title = "标准参数库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KnowledgeStandard knowledgeStandard)
    {
        return toAjax(knowledgeStandardService.updateKnowledgeStandard(knowledgeStandard));
    }

    /**
     * 删除标准参数库
     */
    @PreAuthorize("@ss.hasPermi('knowledge:standard:remove')")
    @Log(title = "标准参数库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(knowledgeStandardService.deleteKnowledgeStandardByIds(ids));
    }
}
