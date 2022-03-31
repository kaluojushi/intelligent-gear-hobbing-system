<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="75px">
      <el-form-item label="滚刀名称" prop="hobName">
        <el-input
          v-model="queryParams.hobName"
          placeholder="请输入滚刀名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="滚刀头数" prop="hobHeads">
        <el-input
          v-model="queryParams.hobHeads"
          placeholder="请输入滚刀头数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="滚刀模数" prop="hobModule">
        <el-input
          v-model="queryParams.hobModule"
          placeholder="请输入滚刀模数(mm)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['device:hob:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['device:hob:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['device:hob:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['device:hob:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="滚刀名称" align="center" prop="hobName" />
      <el-table-column label="滚刀头数" align="center" prop="hobHeads" />
      <el-table-column label="滚刀模数(mm)" align="center" prop="hobModule" />
      <el-table-column label="滚刀压力角(°)" align="center" prop="hobPressureAngle" />
      <el-table-column label="滚刀螺旋升角(°)" align="center" prop="hobSpiralAngle" />
      <el-table-column label="滚刀长度(mm)" align="center" prop="hobLength" />
      <el-table-column label="滚刀外径(mm)" align="center" prop="hobOuterDiameter" />
      <el-table-column label="备注" align="center" prop="notes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['device:hob:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:hob:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改滚刀库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="滚刀名称" prop="hobName">
          <el-input v-model="form.hobName" placeholder="请输入滚刀名称" />
        </el-form-item>
        <el-form-item label="滚刀头数" prop="hobHeads">
          <el-input v-model="form.hobHeads" placeholder="请输入滚刀头数" />
        </el-form-item>
        <el-form-item label="滚刀模数(mm)" prop="hobModule">
          <el-input v-model="form.hobModule" placeholder="请输入滚刀模数(mm)" />
        </el-form-item>
        <el-form-item label="滚刀压力角(°)" prop="hobPressureAngle">
          <el-input v-model="form.hobPressureAngle" placeholder="请输入滚刀压力角(°)" />
        </el-form-item>
        <el-form-item label="滚刀螺旋升角(°)" prop="hobSpiralAngle">
          <el-input v-model="form.hobSpiralAngle" placeholder="请输入滚刀螺旋升角(°)" />
        </el-form-item>
        <el-form-item label="滚刀长度(mm)" prop="hobLength">
          <el-input v-model="form.hobLength" placeholder="请输入滚刀长度(mm)" />
        </el-form-item>
        <el-form-item label="滚刀外径(mm)" prop="hobOuterDiameter">
          <el-input v-model="form.hobOuterDiameter" placeholder="请输入滚刀外径(mm)" />
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="form.notes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHob, getHob, delHob, addHob, updateHob } from "@/api/device/hob";

export default {
  name: "Hob",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 滚刀库表格数据
      hobList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hobName: null,
        hobHeads: null,
        hobModule: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询滚刀库列表 */
    getList() {
      this.loading = true;
      listHob(this.queryParams).then(response => {
        this.hobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        hobName: null,
        hobHeads: null,
        hobModule: null,
        hobPressureAngle: null,
        hobSpiralAngle: null,
        hobLength: null,
        hobOuterDiameter: null,
        notes: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加滚刀库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHob(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改滚刀库";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHob(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHob(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除滚刀库编号为"' + ids + '"的数据项？').then(function() {
        return delHob(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('device/hob/export', {
        ...this.queryParams
      }, `hob_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
