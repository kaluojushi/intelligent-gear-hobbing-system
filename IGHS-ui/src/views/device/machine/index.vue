<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="75px">
      <el-form-item label="机床型号" prop="machineName">
        <el-input
          v-model="queryParams.machineName"
          placeholder="请输入机床型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数控系统" prop="cncId">
        <el-select
          v-model="queryParams.cncId"
          filterable
          clearable
          placeholder="请选择或搜索数控系统型号">
          <el-option
            v-for="cnc in cncOptions"
            :key="cnc.value"
            :label="cnc.label"
            :value="cnc.value">
          </el-option>
        </el-select>
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
          v-hasPermi="['device:machine:add']"
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
          v-hasPermi="['device:machine:edit']"
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
          v-hasPermi="['device:machine:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['device:machine:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="machineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键id" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
      <el-table-column label="机床型号" align="center" prop="machineName" />
      <el-table-column label="机床描述" align="center" prop="machineDescribe" />
      <el-table-column label="最大工件外径(mm)" align="center" prop="maxWorkpieceDiameter" />
      <el-table-column label="最大工件模数(mm)" align="center" prop="maxWorkpieceModulus" />
      <el-table-column label="数控系统型号" align="center" prop="cncId" :formatter="cncFormat" />
      <el-table-column label="备注" align="center" prop="notes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['device:machine:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:machine:remove']"
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

    <!-- 添加或修改机床库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="机床型号" prop="machineName">
          <el-input v-model="form.machineName" placeholder="请输入机床型号" />
        </el-form-item>
        <el-form-item label="机床描述" prop="machineDescribe">
          <el-input v-model="form.machineDescribe" placeholder="请输入机床描述" />
        </el-form-item>
        <el-form-item label="最大工件外径(mm)" prop="maxWorkpieceDiameter">
          <el-input v-model="form.maxWorkpieceDiameter" placeholder="请输入最大工件外径(mm)" />
        </el-form-item>
        <el-form-item label="最大工件模数(mm)" prop="maxWorkpieceModulus">
          <el-input v-model="form.maxWorkpieceModulus" placeholder="请输入最大工件模数(mm)" />
        </el-form-item>
        <el-form-item label="数控系统型号" prop="cncId">
          <el-select
            v-model="form.cncId"
            filterable
            display="inline-block"
            style="width: 100%"
            placeholder="请选择或搜索数控系统型号">
            <el-option
              v-for="cnc in cncOptions"
              :key="cnc.value"
              :label="cnc.label"
              :value="cnc.value">
            </el-option>
          </el-select>
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
import { listMachine, getMachine, delMachine, addMachine, updateMachine } from "@/api/device/machine";
import { listCnc } from "@/api/algorithm/cnc";

export default {
  name: "Machine",
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
      // 机床库表格数据
      machineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        machineName: null,
        cncId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      cncOptions: [],
    };
  },
  created() {
    this.getList();
    this.getCncOptions();
  },
  methods: {
    indexMethod(index) {
      return index + 1;
    },
    /** 查询机床库列表 */
    getList() {
      this.loading = true;
      listMachine(this.queryParams).then(response => {
        this.machineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 数控系统列表获取 */
    getCncOptions() {
      listCnc().then(response => {
        this.cncOptions = [];
        for (let row of response.rows) {
          let item = {
            value: row.id,
            label: row.cncName
          };
          this.cncOptions.push(item);
        }
      });
    },
    cncFormat(row, column) {
      const cnc = this.cncOptions.find(cnc => cnc.value === row.cncId);
      return cnc ? cnc.label : "";
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
        machineName: null,
        machineDescribe: null,
        maxWorkpieceDiameter: null,
        maxWorkpieceModulus: null,
        cncId: null,
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
      this.title = "添加机床库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMachine(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改机床库";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMachine(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMachine(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除机床库编号为"' + ids + '"的数据项？').then(function() {
        return delMachine(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('device/machine/export', {
        ...this.queryParams
      }, `machine_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
