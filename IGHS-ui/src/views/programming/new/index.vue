<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="card-title">
            新代码生成
          </div>
          <el-form ref="form" :model="form" label-width="95px" :inline="true">
            <div class="form-subtitle">
              齿轮类型选择
            </div>
            <el-form-item label="齿轮类型">
              <el-cascader
                v-model="form.gearType"
                :options="gearTypeOptions"
                :props="{expandTrigger: 'hover'}"
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
            <div class="form-subtitle">
              齿轮数据
            </div>
            <el-form-item label="齿轮齿数">
              <el-input v-model="form.gearTeeth"></el-input>
            </el-form-item>
            <el-form-item label="齿轮模数">
              <el-input v-model="form.gearModulu">
                <template slot="append">
                  mm
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="压力角">
              <el-input v-model="form.pressureAngle">
                <template slot="append">
                  °
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="齿顶高系数">
              <el-input v-model="form.ha"></el-input>
            </el-form-item>
            <el-form-item label="顶隙系数">
              <el-input v-model="form.c"></el-input>
            </el-form-item>
            <el-form-item label="齿宽">
              <el-input v-model="form.b">
                <template slot="append">
                  mm
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="外径">
              <el-input v-model="form.da">
                <template slot="append">
                  mm
                </template>
              </el-input>
            </el-form-item>
            <div class="form-subtitle">
              加工设备选择
            </div>
            <el-form-item label="机床">
              <el-select></el-select>
            </el-form-item>
            <el-form-item label="滚刀">
              <el-select></el-select>
            </el-form-item>
            <div style="margin-bottom: 15px;"></div>
            <el-form-item>
              <el-button type="primary" @click="">更新模型</el-button>
              <el-button type="success" @click="">生成代码</el-button>
              <el-button type="danger">清空</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <div slot="header" class="card-title">
            SVG模型
          </div>
          <el-image :src="require('@/assets/images/gear.png')"></el-image>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "New",
  data() {
    return {
      form:{},
      gearTypeOptions: [{
        value: "圆柱齿轮",
        label: "圆柱齿轮",
        children: [{
          value: "直齿圆柱齿轮",
          label: "直齿圆柱齿轮"
        }, {
          value: "斜齿圆柱齿轮",
          label: "斜齿圆柱齿轮"
        }]
      }, {
        value: "轴向异型齿轮",
        label: "轴向异型齿轮",
        children: [{
          value: "小锥度齿轮",
          label: "小锥度齿轮"
        }, {
          value: "鼓形齿轮",
          label: "鼓形齿轮"
        }]
      }, {
        value: "其他",
        label: "其他",
        children: [{
          value: "花键",
          label: "花键"
        }, {
          value: "蜗轮",
          label: "蜗轮"
        }]
      }]
    }
  },
  created() {
  },
  methods: {

  }
}
</script>

<style scoped>
.card-title {
  font: 20px bold;
}

.form-subtitle {
  font:18px bold;
  margin-top: 10px;
  margin-bottom: 15px;
}
</style>
