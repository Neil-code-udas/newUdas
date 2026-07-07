<template>
  <div class="pair-match-page">
    <!-- 顶部筛选栏 -->
    <el-card shadow="small" class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="所属机构">
          <el-select v-model="searchForm.orgId" placeholder="全部机构" clearable>
            <el-option label="城西支行营业部" value="ORG0101"></el-option>
            <el-option label="城东支行营业部" value="ORG0102"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="岗位类型">
          <el-select v-model="searchForm.jobType" placeholder="全部" clearable>
            <el-option label="普通岗位(本方案)" value="NORMAL"></el-option>
            <el-option label="客户经理(旧方案)" value="CUSTOMER"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名搜索">
          <el-input v-model="searchForm.keyword" placeholder="输入姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="loadTeacherStudentList">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 左右分栏：可选师父 / 可选徒弟 -->
    <el-row :gutter="20" style="margin:15px 0;">
      <!-- 左侧师父列表 -->
      <el-col span="12">
        <el-card shadow="small" header="可选师父（13职等及以上、在岗满2年）">
          <div class="list-wrap">
            <div
              class="staff-card"
              v-for="t in teacherList"
              :key="t.id"
              :class="{active: selectTeacher && selectTeacher.id === t.id}"
              @click="selectTeacher = t"
            >
              <div class="name">{{t.name}}</div>
              <div class="desc">职等：{{t.level}} | 在岗{{t.workYear}}年</div>
              <el-tag v-if="t.isManager" size="mini" type="primary">部门负责人</el-tag>
            </div>
            <div v-if="teacherList.length === 0" class="empty-text">暂无符合条件师父</div>
          </div>
        </el-card>
      </el-col>
      <!-- 右侧徒弟列表 -->
      <el-col span="12">
        <el-card shadow="small" header="可选徒弟（14/15职等、入岗未满1年）">
          <div class="list-wrap">
            <div
              class="staff-card"
              v-for="s in studentList"
              :key="s.id"
              :class="{active: selectStudent && selectStudent.id === s.id}"
              @click="selectStudent = s"
            >
              <div class="name">{{s.name}}</div>
              <div class="desc">职等：{{s.level}} | 入职{{s.entryMonth}}个月</div>
            </div>
            <div v-if="studentList.length === 0" class="empty-text">暂无符合条件徒弟</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 匹配操作按钮 -->
    <div class="btn-bar" style="text-align:center;margin-bottom:15px;">
      <el-button
        type="primary"
        size="large"
        icon="el-icon-link"
        :disabled="!selectTeacher || !selectStudent"
        @click="openSignDialog"
      >
        创建师徒结对签约
      </el-button>
      <div v-if="!selectTeacher || !selectStudent" style="color:#999;margin-top:5px;">请左侧选1位师父、右侧选1名徒弟</div>
    </div>

    <!-- 底部：本部门结对台账表格 -->
    <el-card shadow="small" header="本部门师徒结对台账">
      <el-table :data="pairTableList" border stripe>
        <el-table-column label="结对编号" prop="pairId"></el-table-column>
        <el-table-column label="师父" prop="teacherName"></el-table-column>
        <el-table-column label="徒弟" prop="studentName"></el-table-column>
        <el-table-column label="结对周期">
          <template slot-scope="scope">{{scope.row.startDate}} ~ {{scope.row.endDate}}</template>
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status==='TRAINING'?'primary':'success'">{{scope.row.statusText}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="previewSignFile(scope.row)">承诺书预览</el-button>
            <el-button size="mini" type="primary" @click="goWorkbench(scope.row.pairId)">查看工作台</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 签约弹窗 -->
    <el-dialog title="师徒结对签约" :visible.sync="signDialogVisible" width="650px">
      <el-form ref="signFormRef" :model="signForm" label-width="110px">
        <el-form-item label="选中师父">
          <el-input v-model="signForm.teacherName" disabled></el-input>
        </el-form-item>
        <el-form-item label="选中徒弟">
          <el-input v-model="signForm.studentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="结对生效日期" prop="startDate" required>
          <el-date-picker v-model="signForm.startDate" type="date" placeholder="选择日期" @change="calcEndDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="自动到期日期">
          <el-input v-model="signForm.endDate" disabled placeholder="生效日期+12个月"></el-input>
        </el-form-item>
        <el-form-item label="上传结对承诺书" required>
          <el-upload
            action=""
            :file-list="signFileList"
            list-type="text"
            :on-change="handleSignFileChange"
          >
            <el-button size="small" type="primary">上传PDF/图片</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="signDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitSign">确认签约创建结对</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "TeacherStudentMatch",
  data() {
    return {
      // 搜索条件
      searchForm: { orgId: "", jobType: "", keyword: "" },
      // 师父、徒弟列表
      teacherList: [],
      studentList: [],
      // 选中师徒
      selectTeacher: null,
      selectStudent: null,
      // 结对台账
      pairTableList: [],
      // 签约弹窗
      signDialogVisible: false,
      signForm: {
        teacherName: "",
        studentName: "",
        startDate: "",
        endDate: ""
      },
      signFileList: [],
      signFileUrl: ""
    };
  },
  mounted() {
    this.loadTeacherStudentList();
    this.loadPairTable();
  },
  methods: {
    // 重置搜索
    resetSearch() {
      this.searchForm = { orgId: "", jobType: "", keyword: "" };
      this.loadTeacherStudentList();
    },
    // 加载可选师父徒弟
    async loadTeacherStudentList() {
      // 模拟接口
      this.teacherList = [
        { id: "T001", name: "李建国", level: 13, workYear: 3, isManager: true },
        { id: "T002", name: "张敏", level: 14, workYear: 2, isManager: false }
      ];
      this.studentList = [
        { id: "S001", name: "王浩", level: 15, entryMonth: 6 },
        { id: "S002", name: "刘小雨", level: 14, entryMonth: 3 }
      ];
      // 清空选中
      this.selectTeacher = null;
      this.selectStudent = null;
    },
    // 加载结对台账测试数据
    loadPairTable() {
      this.pairTableList = [
        {
          pairId: "TP20260110001",
          teacherName: "李建国",
          studentName: "王浩",
          startDate: "2026-01-10",
          endDate: "2027-01-10",
          status: "TRAINING",
          statusText: "培养中",
          signFileUrl: "/upload/file/20260110/sign1001.pdf"
        }
      ];
    },
    // 打开签约弹窗
    openSignDialog() {
      // 客户经理岗拦截提示
      if(this.selectStudent.jobType === "CUSTOMER") {
        this.$alert("客户经理岗请使用原有旧方案，不可在此页面创建结对", "提示");
        return;
      }
      this.signForm.teacherName = this.selectTeacher.name;
      this.signForm.studentName = this.selectStudent.name;
      this.signForm.startDate = new Date();
      this.calcEndDate();
      this.signFileList = [];
      this.signFileUrl = "";
      this.signDialogVisible = true;
    },
    // 自动计算12个月到期日
    calcEndDate() {
      if(!this.signForm.startDate) return;
      const sDate = new Date(this.signForm.startDate);
      sDate.setMonth(sDate.getMonth() + 12);
      this.signForm.endDate = this.formatDate(sDate);
    },
    // 日期格式化
    formatDate(date) {
      const y = date.getFullYear();
      const m = String(date.getMonth()+1).padStart(2,"0");
      const d = String(date.getDate()).padStart(2,"0");
      return `${y}-${m}-${d}`;
    },
    // 承诺书上传
    handleSignFileChange(file, list) {
      this.signFileList = list;
      this.signFileUrl = file.response?.url || "/upload/test/sign.pdf";
    },
    // 提交签约
    async submitSign() {
      await this.$refs.signFormRef.validate();
      if(!this.signFileUrl) {
        this.$message.warning("请上传结对承诺书");
        return;
      }
      // 模拟后端创建结对，同时后端自动插入时间轴签约记录
      this.$message.success("结对签约创建成功！");
      this.signDialogVisible = false;
      // 刷新台账
      this.loadPairTable();
      // 清空选中
      this.selectTeacher = null;
      this.selectStudent = null;
    },
    // 预览承诺书
    previewSignFile(row) {
      window.open(row.signFileUrl);
    },
    // 跳转工作台（携带结对ID）
    goWorkbench(pairId) {
      this.$router.push({ path: "/teacherStudent/workbench", query: { pairId } });
    }
  }
};
</script>

<style scoped>
.search-card {
  margin-bottom: 10px;
}
.list-wrap {
  max-height: 320px;
  overflow-y: auto;
}
.staff-card {
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  padding: 10px 12px;
  margin-bottom: 8px;
  cursor: pointer;
}
.staff-card.active {
  border-color: #409EFF;
  background: #ecf5ff;
}
.name {
  font-weight: 500;
  font-size: 14px;
}
.desc {
  font-size: 12px;
  color: #666;
  margin: 4px 0;
}
.empty-text {
  text-align: center;
  padding: 40px 0;
  color: #999;
}
</style>