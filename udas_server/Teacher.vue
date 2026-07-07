<template>
  <div class="teacher-student-workbench">
    <!-- 顶部结对基础信息卡片 -->
    <el-card shadow="small" class="info-card">
      <div slot="header">
        <span class="title">结对基础信息</span>
        <el-tag :type="pairInfo.status === 'TRAINING' ? 'primary' : 'success'" style="margin-left:20px">
          {{ pairInfo.statusText }}
        </el-tag>
      </div>
      <el-row :gutter="20">
        <el-col span="6">
          <div class="label">师父：</div>
          <div class="value">{{ pairInfo.teacherName }}</div>
        </el-col>
        <el-col span="6">
          <div class="label">徒弟：</div>
          <div class="value">{{ pairInfo.studentName }}</div>
        </el-col>
        <el-col span="6">
          <div class="label">所属机构：</div>
          <div class="value">{{ pairInfo.orgName }}</div>
        </el-col>
        <el-col span="6">
          <div class="label">结对周期：</div>
          <div class="value">{{ pairInfo.startDate }} ~ {{ pairInfo.endDate }}</div>
        </el-col>
        <el-col span="6">
          <div class="label">剩余培养天数：</div>
          <div class="value" style="color: #f56c6c">{{ pairInfo.remainDay }}天</div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 操作按钮区域，按角色显示 -->
    <div class="btn-group" style="margin:15px 0;">
      <!-- 徒弟：提交周报/月报/问题清单 -->
      <el-button v-if="userRole === 'STUDENT'" type="primary" icon="el-icon-edit-outline" @click="openSubmitDialog">
        提交工作材料
      </el-button>
      <!-- 师父：审阅徒弟材料 -->
      <el-button v-if="userRole === 'TEACHER'" type="warning" icon="el-icon-message" @click="openReviewDialog">
        填写审阅评语
      </el-button>
      <!-- 管理员/部门负责人：新增考核记录 -->
      <el-button v-if="userRole === 'ADMIN'" type="success" icon="el-icon-document" @click="openExamDialog">
        录入考核记录
      </el-button>
    </div>

    <!-- 时间轴主体 对话气泡 -->
    <el-card shadow="small">
      <el-timeline v-if="timeLineList.length > 0">
        <el-timeline-item
          v-for="item in timeLineList"
          :key="item.id"
          :color="getLineColor(item.operateType)"
        >
          <!-- 操作时间 + 操作人 -->
          <div class="time-header">
            <span class="operate-time">{{ item.operateTime }}</span>
            <span class="operate-name">{{ item.operateUserName }}</span>
            <el-tag size="mini" :type="getTagType(item.operateType)">{{ getTypeName(item.operateType) }}</el-tag>
          </div>

          <!-- 对话气泡卡片 -->
          <div class="msg-bubble" :class="getBubbleClass(item.operateType)">
            <!-- 文本内容 -->
            <div class="msg-content">{{ item.content || '无文字描述' }}</div>
            <!-- 分数展示（审阅、考核才有分数） -->
            <div v-if="item.score !== null && item.score !== ''" class="score-text">
              得分：{{ item.score }} 分
            </div>
            <!-- 附件列表 -->
            <div v-if="item.fileUrls && item.fileUrls.length > 0" class="file-list">
              <div class="file-title">附件：</div>
              <div class="file-item" v-for="(file, fIdx) in item.fileUrls" :key="fIdx">
                <el-link type="primary" @click="previewFile(file)">{{ file.fileName }}</el-link>
              </div>
            </div>
          </div>
        </el-timeline-item>
      </el-timeline>

      <!-- 空状态 -->
      <div v-else class="empty-tip">
        <i class="el-icon-document-empty" style="font-size:40px;color:#ccc;"></i>
        <p>暂无结对记录，提交第一条材料开启带教记录</p>
      </div>
    </el-card>

    <!-- 弹窗1：徒弟提交材料弹窗 -->
    <el-dialog title="提交工作材料" :visible.sync="submitDialogVisible" width="600px">
      <el-form ref="submitFormRef" :model="submitForm" label-width="100px">
        <el-form-item label="材料类型" prop="materialType" required>
          <el-select v-model="submitForm.materialType" placeholder="请选择">
            <el-option label="周工作复盘" value="WEEK"></el-option>
            <el-option label="问题清单" value="QUESTION"></el-option>
            <el-option label="月度操作月报" value="MONTH"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容描述" prop="content" required>
          <el-input v-model="submitForm.content" type="textarea" rows="5" placeholder="填写本周/本月工作内容、遇到的问题"></el-input>
        </el-form-item>
        <el-form-item label="上传附件">
          <el-upload
            action=""
            :file-list="submitFileList"
            list-type="text"
            :on-change="handleSubmitFileChange"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="submitDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitMaterial">提交</el-button>
      </div>
    </el-dialog>

    <!-- 弹窗2：师父审阅弹窗 -->
    <el-dialog title="填写审阅评语" :visible.sync="reviewDialogVisible" width="600px">
      <el-form ref="reviewFormRef" :model="reviewForm" label-width="100px">
        <el-form-item label="审阅评语" prop="content" required>
          <el-input v-model="reviewForm.content" type="textarea" rows="5" placeholder="对徒弟工作内容进行点评、指导建议"></el-input>
        </el-form-item>
        <el-form-item label="过程打分" prop="score" required>
          <el-input-number v-model="reviewForm.score" :min="0" :max="100" step="0.5"></el-input-number>
        </el-form-item>
        <el-form-item label="审阅附件">
          <el-upload
            action=""
            :file-list="reviewFileList"
            list-type="text"
            :on-change="handleReviewFileChange"
          >
            <el-button size="small" type="warning">上传评分表</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="reviewDialogVisible = false">取消</el-button>
        <el-button type="warning" @click="submitReview">确认审阅</el-button>
      </div>
    </el-dialog>

    <!-- 弹窗3：管理员考核录入弹窗 -->
    <el-dialog title="录入考核记录" :visible.sync="examDialogVisible" width="600px">
      <el-form ref="examFormRef" :model="examForm" label-width="100px">
        <el-form-item label="考核类型" prop="examType" required>
          <el-select v-model="examForm.examType" placeholder="选择考核类型">
            <el-option label="半年笔试" value="HALF_YEAR_EXAM"></el-option>
            <el-option label="出师综合考核" value="GRADUATE_EXAM"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核说明" prop="content" required>
          <el-input v-model="examForm.content" type="textarea" rows="4"></el-input>
        </el-form-item>
        <el-form-item label="考核得分" prop="score" required>
          <el-input-number v-model="examForm.score" :min="0" :max="100" step="0.5"></el-input-number>
        </el-form-item>
        <el-form-item label="试卷/评分附件">
          <el-upload
            action=""
            :file-list="examFileList"
            list-type="text"
            :on-change="handleExamFileChange"
          >
            <el-button size="small" type="success">上传文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="examDialogVisible = false">取消</el-button>
        <el-button type="success" @click="submitExam">保存考核记录</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "TeacherStudentWorkbench",
  data() {
    return {
      // 路由传入结对ID
      pairId: this.$route.query.pairId || "",
      // 当前登录人角色 ADMIN管理员 TEACHER师父 STUDENT徒弟
      userRole: "STUDENT",
      // 结对基础信息
   pairInfo: {
  teacherName: "李建国（对公团队主管）",
  studentName: "王浩（新入职客户经理）",
  orgName: "杭州分行城西支行营业部",
  startDate: "2026-01-10",
  endDate: "2027-01-10",
  remainDay: 185,
  status: "TRAINING",
  statusText: "培养中"
},
      // 时间轴列表
      timeLineList: [
  {
    id: 1,
    operateType: "SIGN",
    operateUserName: "张三（部门管理员）",
    operateTime: "2026-01-10 09:30:22",
    content: "完成师徒结对签约，师父：李经理，徒弟：小王，培养周期12个月",
    score: null,
    fileUrls: [
      { fileName: "师徒结对承诺书.pdf", fileUrl: "/upload/file/20260110/sign1001.pdf" }
    ]
  },
  {
    id: 2,
    operateType: "STUDENT_SUBMIT",
    operateUserName: "小王（徒弟）",
    operateTime: "2026-01-17 18:20:10",
    content: "本周复盘：学习对公基础业务流程，练习客户信息录入；遇到问题：授信资料清单分不清小微企业标准，后续需要师父指导区分政策口径。",
    score: null,
    fileUrls: [
      { fileName: "第一周工作记录.docx", fileUrl: "/upload/file/20260117/week01.docx" }
    ]
  },
  {
    id: 3,
    operateType: "TEACHER_REVIEW",
    operateUserName: "李经理（师父）",
    operateTime: "2026-01-18 10:12:35",
    content: "本周整体上手较快，小微企业授信文件我整理了一份对照表发给你，下周重点练习客户准入判断，操作细节减少录入错误。",
    score: 86.5,
    fileUrls: [
      { fileName: "小微授信资料对照表.xlsx", fileUrl: "/upload/file/20260118/review01.xlsx" }
    ]
  },
  {
    id: 4,
    operateType: "STUDENT_SUBMIT",
    operateUserName: "小王（徒弟）",
    operateTime: "2026-02-05 17:45:18",
    content: "月度操作月报：本月累计接待客户12户，完成基础开户8户；疑难问题：客户异地开户佐证材料要求不清晰。",
    score: null,
    fileUrls: [
      { fileName: "1月月度操作月报.xlsx", fileUrl: "/upload/file/20260205/month01.xlsx" }
    ]
  },
  {
    id: 5,
    operateType: "EXAM_RECORD",
    operateUserName: "部门负责人 王总",
    operateTime: "2026-07-12 14:20:00",
    content: "上半年专业知识笔试，考核总分92分，基础政策掌握良好，实操流程少量疏漏。",
    score: 92,
    fileUrls: [
      { fileName: "半年笔试试卷.pdf", fileUrl: "/upload/file/20260712/exam01.pdf" }
    ]
  }
],

      // ========== 徒弟提交弹窗 ==========
      submitDialogVisible: false,
      submitForm: { materialType: "", content: "" },
      submitFileList: [],
      submitFileUrls: [],

      // ========== 师父审阅弹窗 ==========
      reviewDialogVisible: false,
      reviewForm: { content: "", score: 80 },
      reviewFileList: [],
      reviewFileUrls: [],

      // ========== 管理员考核弹窗 ==========
      examDialogVisible: false,
      examForm: { examType: "", content: "", score: 80 },
      examFileList: [],
      examFileUrls: []
    };
  },
  mounted() {
    this.initData();
  },
  methods: {
    // 初始化页面数据
    async initData() {
      if (!this.pairId) {
        this.$message.error("结对ID不能为空");
        return;
      }
      // 1. 查询结对基础信息
      const pairRes = await this.$api.pair.getPairDetail({ pairId: this.pairId });
      this.pairInfo = pairRes.data;
      // 2. 查询该结对全部时间轴记录
      const lineRes = await this.$api.pairTimeLine.getList({ pairId: this.pairId });
      this.timeLineList = lineRes.data;
    },

    // 获取时间轴圆点颜色
    getLineColor(type) {
      const map = {
        SIGN: "#409EFF", // 系统签约 蓝色
        STUDENT_SUBMIT: "#67C23A", // 徒弟提交 绿色
        TEACHER_REVIEW: "#E6A23C", // 师父审阅 橙色
        EXAM_RECORD: "#909399" // 考核记录 灰色
      };
      return map[type] || "#ccc";
    },
    // 获取标签文字
    getTypeName(type) {
      const map = {
        SIGN: "系统签约",
        STUDENT_SUBMIT: "徒弟提交",
        TEACHER_REVIEW: "师父审阅",
        EXAM_RECORD: "考核记录"
      };
      return map[type] || "未知";
    },
    // 标签类型
    getTagType(type) {
      const map = {
        SIGN: "primary",
        STUDENT_SUBMIT: "success",
        TEACHER_REVIEW: "warning",
        EXAM_RECORD: "info"
      };
      return map[type] || "";
    },
    // 气泡样式区分左右对话
    getBubbleClass(type) {
      if (type === "STUDENT_SUBMIT") return "bubble-left";
      if (type === "TEACHER_REVIEW") return "bubble-right";
      return "bubble-center";
    },

    // 文件预览
    previewFile(fileItem) {
      // 调用全局预览组件/新窗口下载
      window.open(fileItem.fileUrl);
    },

    // ========== 徒弟提交材料 ==========
    openSubmitDialog() {
      this.submitForm = { materialType: "", content: "" };
      this.submitFileList = [];
      this.submitFileUrls = [];
      this.submitDialogVisible = true;
    },
    handleSubmitFileChange(file, fileList) {
      this.submitFileList = fileList;
      this.submitFileUrls = fileList.map(f => ({ fileName: f.name, fileUrl: f.response.url }));
    },
    async submitMaterial() {
      await this.$refs.submitFormRef.validate();
      const params = {
        pairId: this.pairId,
        operateType: "STUDENT_SUBMIT",
        content: this.submitForm.content,
        fileUrls: this.submitFileUrls
      };
      await this.$api.pairTimeLine.saveLineRecord(params);
      this.$message.success("提交成功");
      this.submitDialogVisible = false;
      this.initData(); // 刷新时间轴
    },

    // ========== 师父审阅 ==========
    openReviewDialog() {
      this.reviewForm = { content: "", score: 80 };
      this.reviewFileList = [];
      this.reviewFileUrls = [];
      this.reviewDialogVisible = true;
    },
    handleReviewFileChange(file, fileList) {
      this.reviewFileList = fileList;
      this.reviewFileUrls = fileList.map(f => ({ fileName: f.name, fileUrl: f.response.url }));
    },
    async submitReview() {
      await this.$refs.reviewFormRef.validate();
      const params = {
        pairId: this.pairId,
        operateType: "TEACHER_REVIEW",
        content: this.reviewForm.content,
        score: this.reviewForm.score,
        fileUrls: this.reviewFileUrls
      };
      await this.$api.pairTimeLine.saveLineRecord(params);
      this.$message.success("审阅记录已保存");
      this.reviewDialogVisible = false;
      this.initData();
    },

    // ========== 管理员考核录入 ==========
    openExamDialog() {
      this.examForm = { examType: "", content: "", score: 80 };
      this.examFileList = [];
      this.examFileUrls = [];
      this.examDialogVisible = true;
    },
    handleExamFileChange(file, fileList) {
      this.examFileList = fileList;
      this.examFileUrls = fileList.map(f => ({ fileName: f.name, fileUrl: f.response.url }));
    },
    async submitExam() {
      await this.$refs.examFormRef.validate();
      const params = {
        pairId: this.pairId,
        operateType: "EXAM_RECORD",
        content: `${this.examForm.examType}:${this.examForm.content}`,
        score: this.examForm.score,
        fileUrls: this.examFileUrls
      };
      await this.$api.pairTimeLine.saveLineRecord(params);
      this.$message.success("考核记录录入成功");
      this.examDialogVisible = false;
      this.initData();
    }
  }
};
</script>

<style scoped>
.info-card {
  margin-bottom: 15px;
}
.label {
  color: #666;
  font-size: 13px;
}
.value {
  font-size: 14px;
  font-weight: 500;
}
.time-header {
  margin-bottom: 8px;
}
.operate-time {
  color: #999;
  font-size: 12px;
  margin-right: 10px;
}
.operate-name {
  font-size: 13px;
  margin-right: 10px;
}
.msg-bubble {
  padding: 12px 16px;
  border-radius: 8px;
  max-width: 80%;
}
/* 徒弟消息 左对齐 绿色背景 */
.bubble-left {
  background: #f0f9eb;
  margin-right: auto;
}
/* 师父消息 右对齐 浅橙背景 */
.bubble-right {
  background: #fff7e6;
  margin-left: auto;
}
/* 系统/考核居中 */
.bubble-center {
  background: #f5f7fa;
  margin: 0 auto;
}
.msg-content {
  font-size: 14px;
  line-height: 1.6;
}
.score-text {
  margin-top: 6px;
  color: #e6a23c;
  font-weight: 500;
}
.file-list {
  margin-top: 8px;
}
.file-title {
  font-size: 12px;
  color: #666;
}
.file-item {
  margin-top: 4px;
}
.empty-tip {
  text-align: center;
  padding: 60px 0;
  color: #ccc;
}
</style>