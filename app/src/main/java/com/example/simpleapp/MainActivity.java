package com.example.simpleapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int COLOR_PRIMARY = Color.rgb(24, 77, 125);
    private static final int COLOR_ACCENT = Color.rgb(11, 128, 115);
    private static final int COLOR_WARNING = Color.rgb(219, 112, 25);
    private static final int COLOR_DANGER = Color.rgb(201, 54, 66);
    private static final int COLOR_TEXT = Color.rgb(28, 38, 54);
    private static final int COLOR_MUTED = Color.rgb(91, 103, 123);
    private static final int COLOR_CARD = Color.WHITE;

    private static final Module[] MODULES = new Module[] {
            new Module(
                    "S1",
                    "标准化建筑防渗漏数据采集",
                    "移动端标准化录入、设备对接与预处理，确保数据格式统一并可直接输入后端算法模型。",
                    new String[] {"离线档案 128 栋", "设备接入 6 类", "预处理完成率 96%"},
                    new Feature[] {
                            new Feature("建筑基础档案管理",
                                    "新建、编辑、查询建筑档案；录入建造年限、结构形式、围护结构材质、防水构造层级、服役老化程度；支持 GPS、全景照片/视频、平面图标注与 Excel 批量导入。",
                                    "字段与专利实施例二保持一致；支持离线新建档案，网络恢复后自动同步。"),
                            new Feature("多源数据手动录入",
                                    "分模块录入场地环境、建筑使用、渗漏维护历史、居住人员反馈等数据。",
                                    "提供下拉选择、数值输入、日期选择等标准化控件；必填校验与异常值提示。"),
                            new Feature("物联网设备数据对接",
                                    "通过蓝牙/NFC 连接温湿度传感器、裂缝测宽仪、渗水压力计，形成墙体温湿度、裂缝宽度、防水层形变、结构渗水压力等时序数据。",
                                    "支持 Modbus、BLE；采集频率可配置，默认 1 次/分钟。"),
                            new Feature("无损检测数据上传",
                                    "导入红外热像仪、超声波检测仪结构化数据，标注空鼓区域、表层损伤、裂缝位置与长度。",
                                    "支持 JPG、PDF 报告上传与 OCR 识别；裂缝长度/面积自动计算与标注。"),
                            new Feature("数据预处理与同步",
                                    "执行缺失值填补、异常值剔除、Z-Score 标准化；本地缓存并断点续传；加密传输与本地加密存储。",
                                    "预处理逻辑与专利保持一致；离线可存储不少于 100 栋建筑完整数据。")
                    }),
            new Module(
                    "S2",
                    "建筑渗漏风险聚类评估",
                    "调用后端改进密度峰值聚类算法，完成渗漏风险自动分级与可视化展示。",
                    new String[] {"高风险 12 栋", "平均耗时 2.4 秒", "聚类置信度 0.91"},
                    new Feature[] {
                            new Feature("批量风险评估",
                                    "选择单栋或多栋建筑发起评估；返回低/中/较高/高风险等级，并展示耗时、特征维度和聚类置信度。",
                                    "单栋响应不超过 3 秒；100 栋批量响应不超过 30 秒。"),
                            new Feature("风险等级可视化",
                                    "地图按绿、黄、橙、红标注风险；列表按风险排序；统计看板展示等级占比和区域热力。",
                                    "地图支持缩放、平移与筛选；统计数据实时更新。"),
                            new Feature("单建筑风险详情",
                                    "展示核心风险特征、簇标签、同簇参考建筑历史，并生成 PDF 风险评估报告。",
                                    "特征提取与聚类逻辑一致；报告包含评估结论和初步处置建议。"),
                            new Feature("风险预警",
                                    "对高/较高风险建筑自动推送预警，可设置阈值并查询导出预警记录。",
                                    "预警延迟不超过 1 分钟；支持短信与应用内通知。")
                    }),
            new Module(
                    "S3",
                    "建筑渗漏全工况演化预测",
                    "基于 LSTM 模型预测无维修干预下的 5-20 年渗漏趋势，识别主导渗漏类型。",
                    new String[] {"默认周期 10 年", "阈值线 50", "预测成功率 98%"},
                    new Feature[] {
                            new Feature("演化预测发起",
                                    "自动对中、较高、高风险建筑发起预测；支持手动调整周期并展示进度和剩余时间。",
                                    "单栋响应不超过 5 秒；失败自动重试并提示原因。"),
                            new Feature("演化结果可视化",
                                    "生成渗漏程度变化曲线，标注阈值线并高亮首次超阈年份与渗漏类型。",
                                    "曲线支持缩放和点击查看数值；渗漏类型与策略库分类一致。"),
                            new Feature("分流决策展示",
                                    "低风险进入常态化监测；中/较高/高风险跳转到维修策略生成，并输出预测报告。",
                                    "分流逻辑与专利步骤 S32 一致；报告可导出与分享。")
                    }),
            new Module(
                    "S4",
                    "匹配式初始防渗漏维修策略生成",
                    "基于主导渗漏类型匹配标准化策略库，生成带量化参数的初始维修基准策略。",
                    new String[] {"策略类型 12 种", "匹配准确率 95%", "方案对比 3 组"},
                    new Feature[] {
                            new Feature("标准化策略库管理",
                                    "内置屋面、外墙、地下室、厨卫等防渗漏策略；管理员可维护干预强度、设防等级、覆盖范围、构造层数和版本。",
                                    "策略库包含不少于 10 种主流渗漏类型；参数范围与专利一致。"),
                            new Feature("初始策略自动匹配",
                                    "根据主导渗漏类型匹配基准策略，展示工艺、材料、参数、预估成本与防渗年限。",
                                    "与专家匹配结果对比准确率不低于 95%；参数调整后实时更新估算。"),
                            new Feature("策略对比",
                                    "同时展示 2-3 种备选策略，对比参数、成本、防渗年限和施工难度，并支持导出。",
                                    "对比维度覆盖专利核心参数；支持一键选择最优策略。")
                    }),
            new Module(
                    "S5",
                    "建筑防渗漏维修策略智能优化",
                    "调用改进粒子群优化算法，对初始策略参数迭代优化，生成最优维修参数组合。",
                    new String[] {"粒子数 40", "迭代 120 次", "耐久提升 18%"},
                    new Feature[] {
                            new Feature("优化参数配置",
                                    "展示 4 个核心参数及可行区间；支持最大化耐久年限、成本优先、工期优先；配置粒子数量、迭代次数和适应度阈值。",
                                    "参数区间与专利实施例六一致；支持保存常用模板。"),
                            new Feature("智能优化执行",
                                    "一键发起优化，实时显示进度、迭代次数和当前最优适应度值，完成后生成参数组合。",
                                    "单策略优化不超过 10 秒；优化结果收敛率不低于 99%。"),
                            new Feature("优化结果展示",
                                    "对比初始与优化后策略参数、年限和成本，展示适应度变化曲线并生成策略书。",
                                    "防渗年限提升不低于 15%；策略书可直接用于施工指导。"),
                            new Feature("策略确认与下发",
                                    "管理人员审核、批注、修改后下发至施工终端，并记录时间、接收人与执行状态。",
                                    "下发后实时通知；支持撤回与重新下发。")
                    })
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout root = findViewById(R.id.contentRoot);
        buildHeader(root);
        buildSummary(root);
        for (Module module : MODULES) {
            root.addView(createModuleCard(module));
        }
    }

    private void buildHeader(LinearLayout root) {
        TextView label = text("建筑防渗漏智能评估与维修系统", 13, COLOR_ACCENT, Typeface.BOLD);
        root.addView(label);

        TextView title = text("专利流程移动端静态展示版", 27, COLOR_TEXT, Typeface.BOLD);
        title.setPadding(0, dp(6), 0, dp(4));
        root.addView(title);

        TextView desc = text("覆盖 S1 数据采集、S2 聚类评估、S3 演化预测、S4 策略匹配、S5 智能优化。当前版本使用静态示例数据，适合演示业务流程和界面结构。", 15, COLOR_MUTED, Typeface.NORMAL);
        desc.setLineSpacing(dp(2), 1.0f);
        root.addView(desc);

        Button action = new Button(this);
        action.setText("模拟同步 128 栋建筑数据");
        action.setAllCaps(false);
        action.setTextColor(Color.WHITE);
        action.setBackgroundColor(COLOR_PRIMARY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, dp(16), 0, dp(12));
        action.setLayoutParams(params);
        action.setOnClickListener(view -> Toast.makeText(this, "静态演示：数据已完成预处理并进入 S2 聚类评估", Toast.LENGTH_LONG).show());
        root.addView(action);
    }

    private void buildSummary(LinearLayout root) {
        LinearLayout grid = new LinearLayout(this);
        grid.setOrientation(LinearLayout.VERTICAL);
        grid.setPadding(0, dp(4), 0, dp(8));
        root.addView(grid);

        String[][] stats = {
                {"建筑档案", "128 栋"},
                {"高风险预警", "12 条"},
                {"策略库", "12 类"},
                {"优化方案", "36 份"}
        };
        for (String[] stat : stats) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(dp(14), dp(10), dp(14), dp(10));
            row.setBackgroundColor(Color.rgb(232, 241, 249));

            TextView name = text(stat[0], 14, COLOR_MUTED, Typeface.BOLD);
            row.addView(name, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            TextView value = text(stat[1], 18, COLOR_PRIMARY, Typeface.BOLD);
            row.addView(value);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, dp(8));
            grid.addView(row, rowParams);
        }
    }

    private View createModuleCard(Module module) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackgroundColor(COLOR_CARD);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, dp(8), 0, dp(14));
        card.setLayoutParams(cardParams);

        LinearLayout titleRow = new LinearLayout(this);
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);
        card.addView(titleRow);

        TextView step = text(module.step, 14, Color.WHITE, Typeface.BOLD);
        step.setGravity(Gravity.CENTER);
        step.setBackgroundColor(stepColor(module.step));
        titleRow.addView(step, new LinearLayout.LayoutParams(dp(46), dp(34)));

        TextView title = text(module.title, 19, COLOR_TEXT, Typeface.BOLD);
        title.setPadding(dp(12), 0, 0, 0);
        titleRow.addView(title, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView goal = text(module.goal, 14, COLOR_MUTED, Typeface.NORMAL);
        goal.setPadding(0, dp(12), 0, dp(10));
        goal.setLineSpacing(dp(2), 1.0f);
        card.addView(goal);

        LinearLayout metrics = new LinearLayout(this);
        metrics.setOrientation(LinearLayout.VERTICAL);
        card.addView(metrics);
        for (String metric : module.metrics) {
            TextView metricView = text("• " + metric, 14, COLOR_PRIMARY, Typeface.BOLD);
            metricView.setPadding(0, dp(2), 0, dp(2));
            metrics.addView(metricView);
        }

        for (Feature feature : module.features) {
            card.addView(createFeature(feature));
        }

        Button button = new Button(this);
        button.setText(module.step + " 查看静态演示数据");
        button.setAllCaps(false);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(COLOR_ACCENT);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(0, dp(12), 0, 0);
        button.setLayoutParams(buttonParams);
        button.setOnClickListener(view -> Toast.makeText(this, module.title + "：当前为静态展示数据", Toast.LENGTH_SHORT).show());
        card.addView(button);

        return card;
    }

    private View createFeature(Feature feature) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(0, dp(12), 0, 0);

        TextView name = text(feature.name, 16, COLOR_TEXT, Typeface.BOLD);
        box.addView(name);

        TextView details = text("功能详情：" + feature.details, 14, COLOR_MUTED, Typeface.NORMAL);
        details.setPadding(0, dp(5), 0, 0);
        details.setLineSpacing(dp(2), 1.0f);
        box.addView(details);

        TextView acceptance = text("验收标准：" + feature.acceptance, 14, COLOR_WARNING, Typeface.BOLD);
        acceptance.setPadding(0, dp(5), 0, 0);
        acceptance.setLineSpacing(dp(2), 1.0f);
        box.addView(acceptance);

        return box;
    }

    private TextView text(String value, int sp, int color, int style) {
        TextView textView = new TextView(this);
        textView.setText(value);
        textView.setTextSize(sp);
        textView.setTextColor(color);
        textView.setTypeface(Typeface.DEFAULT, style);
        textView.setIncludeFontPadding(true);
        return textView;
    }

    private int stepColor(String step) {
        if ("S1".equals(step)) {
            return COLOR_PRIMARY;
        }
        if ("S2".equals(step)) {
            return COLOR_WARNING;
        }
        if ("S3".equals(step)) {
            return COLOR_DANGER;
        }
        if ("S4".equals(step)) {
            return COLOR_ACCENT;
        }
        return Color.rgb(95, 73, 156);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static class Module {
        final String step;
        final String title;
        final String goal;
        final String[] metrics;
        final Feature[] features;

        Module(String step, String title, String goal, String[] metrics, Feature[] features) {
            this.step = step;
            this.title = title;
            this.goal = goal;
            this.metrics = metrics;
            this.features = features;
        }
    }

    private static class Feature {
        final String name;
        final String details;
        final String acceptance;

        Feature(String name, String details, String acceptance) {
            this.name = name;
            this.details = details;
            this.acceptance = acceptance;
        }
    }
}
