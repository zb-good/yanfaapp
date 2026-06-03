package com.example.simpleapp;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(239, 244, 248);
    private static final int CARD = Color.WHITE;
    private static final int INK = Color.rgb(24, 32, 46);
    private static final int MUTED = Color.rgb(91, 104, 126);
    private static final int BLUE = Color.rgb(30, 95, 168);
    private static final int TEAL = Color.rgb(0, 137, 123);
    private static final int AMBER = Color.rgb(224, 139, 32);
    private static final int RED = Color.rgb(204, 55, 66);
    private static final int PURPLE = Color.rgb(106, 82, 176);

    private LinearLayout contentRoot;
    private LinearLayout bottomNav;
    private int selectedIndex = 0;

    private static final Module[] MODULES = {
            new Module("S1", "数据采集", "标准化建筑防渗漏数据采集",
                    "移动端完成建筑档案、现场环境、传感器、无损检测与预处理数据的统一采集。",
                    "离线档案 128 栋", "同步队列 23 条", "预处理完成率 96%",
                    new String[] {"建筑基础档案管理", "多源数据手动录入", "物联网设备数据对接", "无损检测数据上传", "数据预处理与同步"},
                    new String[] {"字段完整率 100%", "异常值自动提示", "默认 1 次/分钟采集", "支持 JPG/PDF/OCR", "离线存储 ≥100 栋"}),
            new Module("S2", "风险评估", "建筑渗漏风险聚类评估",
                    "调用改进密度峰值聚类算法，输出低、中、较高、高四级风险并可视化。",
                    "高风险 12 栋", "平均耗时 2.4 秒", "置信度 0.91",
                    new String[] {"批量风险评估", "风险等级可视化", "单建筑风险详情", "风险预警"},
                    new String[] {"单栋 ≤3 秒", "地图支持筛选缩放", "报告可导出 PDF", "推送延迟 ≤1 分钟"}),
            new Module("S3", "演化预测", "建筑渗漏全工况演化预测",
                    "基于 LSTM 静态示例结果展示 5-20 年无维修干预下的渗漏发展趋势。",
                    "默认周期 10 年", "阈值线 50", "首次超阈 第 3 年",
                    new String[] {"演化预测发起", "演化结果可视化", "分流决策展示"},
                    new String[] {"单栋 ≤5 秒", "曲线可查看年度值", "S32 分流逻辑"}),
            new Module("S4", "策略生成", "匹配式初始防渗漏维修策略生成",
                    "根据主导渗漏类型匹配标准策略库，生成材料、工艺、成本和年限参数。",
                    "策略库 12 类", "匹配准确率 95%", "备选策略 3 组",
                    new String[] {"标准化策略库管理", "初始策略自动匹配", "策略对比"},
                    new String[] {"≥10 种主流类型", "参数调整实时估算", "一键选择最优策略"}),
            new Module("S5", "智能优化", "建筑防渗漏维修策略智能优化",
                    "调用改进粒子群优化算法示例结果，对维修参数组合进行迭代优化。",
                    "粒子数 40", "迭代 120 次", "耐久提升 18%",
                    new String[] {"优化参数配置", "智能优化执行", "优化结果展示", "策略确认与下发"},
                    new String[] {"保存配置模板", "收敛率 ≥99%", "年限提升 ≥15%", "支持撤回重发"})
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentRoot = findViewById(R.id.contentRoot);
        bottomNav = findViewById(R.id.bottomNav);
        buildBottomNav();
        renderPage(0);
    }

    private void renderPage(int index) {
        selectedIndex = index;
        contentRoot.removeAllViews();
        buildHero();
        buildOverview();
        buildModulePage(MODULES[index]);
        refreshTabs();
    }

    private void buildHero() {
        LinearLayout hero = vertical(18, BLUE);
        hero.setBackground(rounded(BLUE, 22));
        hero.addView(label("建筑防渗漏智能评估", 13, Color.rgb(206, 232, 255), Typeface.BOLD));
        TextView title = label("专利流程移动端驾驶舱", 27, Color.WHITE, Typeface.BOLD);
        title.setPadding(0, dp(6), 0, dp(6));
        hero.addView(title);
        hero.addView(label("静态演示版已按 S1-S5 组织，支持底部切换、图表展示和模块化验收信息查看。", 14, Color.rgb(226, 238, 249), Typeface.NORMAL));

        LinearLayout chips = new LinearLayout(this);
        chips.setOrientation(LinearLayout.HORIZONTAL);
        chips.setPadding(0, dp(16), 0, 0);
        chips.addView(chip("128 栋建筑", Color.rgb(221, 239, 255), BLUE));
        chips.addView(chip("36 份策略", Color.rgb(220, 247, 241), TEAL));
        hero.addView(chips);
        contentRoot.addView(hero, marginParams(0, 0, 0, 14));
    }

    private void buildOverview() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.addView(statCard("采集完整率", "96%", TEAL), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row.addView(statCard("高风险", "12", RED), new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        contentRoot.addView(row, marginParams(0, 0, 0, 12));

        LinearLayout chartCard = card(16);
        chartCard.addView(sectionTitle("风险分布图表"));
        chartCard.addView(label("低 / 中 / 较高 / 高风险建筑数量", 13, MUTED, Typeface.NORMAL));
        chartCard.addView(new ChartView(this, ChartView.TYPE_BARS), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(170)));
        contentRoot.addView(chartCard, marginParams(0, 0, 0, 14));
    }

    private void buildModulePage(Module module) {
        LinearLayout page = card(18);
        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setOrientation(LinearLayout.HORIZONTAL);

        TextView step = label(module.step, 15, Color.WHITE, Typeface.BOLD);
        step.setGravity(Gravity.CENTER);
        step.setBackground(rounded(stepColor(module.step), 12));
        top.addView(step, new LinearLayout.LayoutParams(dp(48), dp(38)));

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);
        titleBox.setPadding(dp(12), 0, 0, 0);
        titleBox.addView(label(module.shortName, 13, stepColor(module.step), Typeface.BOLD));
        titleBox.addView(label(module.fullName, 19, INK, Typeface.BOLD));
        top.addView(titleBox, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        page.addView(top);

        TextView goal = label(module.goal, 14, MUTED, Typeface.NORMAL);
        goal.setPadding(0, dp(14), 0, dp(12));
        goal.setLineSpacing(dp(2), 1f);
        page.addView(goal);

        LinearLayout metrics = new LinearLayout(this);
        metrics.setOrientation(LinearLayout.VERTICAL);
        metrics.addView(metricRow(module.metricA, TEAL));
        metrics.addView(metricRow(module.metricB, AMBER));
        metrics.addView(metricRow(module.metricC, BLUE));
        page.addView(metrics);

        if ("S3".equals(module.step) || "S5".equals(module.step)) {
            page.addView(chartPanel("S3".equals(module.step) ? "10 年渗漏演化趋势" : "优化适应度收敛曲线", ChartView.TYPE_LINE));
        }

        page.addView(sectionTitle("核心子功能"));
        for (int i = 0; i < module.features.length; i++) {
            page.addView(featureRow(i + 1, module.features[i], module.acceptance[i], stepColor(module.step)));
        }

        Button action = new Button(this);
        action.setText(module.step + " 生成静态演示报告");
        action.setAllCaps(false);
        action.setTextColor(Color.WHITE);
        action.setBackground(rounded(stepColor(module.step), 14));
        action.setOnClickListener(v -> Toast.makeText(this, module.fullName + "：报告已生成（静态演示）", Toast.LENGTH_SHORT).show());
        page.addView(action, marginParams(0, 14, 0, 0));
        contentRoot.addView(page, marginParams(0, 0, 0, 22));
    }

    private View chartPanel(String title, int type) {
        LinearLayout panel = vertical(14, CARD);
        panel.setBackground(rounded(Color.rgb(247, 250, 252), 16));
        panel.addView(label(title, 15, INK, Typeface.BOLD));
        panel.addView(new ChartView(this, type), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(160)));
        return panel;
    }

    private View statCard(String title, String value, int color) {
        LinearLayout box = vertical(14, CARD);
        box.setBackground(rounded(CARD, 18, Color.rgb(221, 228, 236)));
        box.addView(label(title, 13, MUTED, Typeface.BOLD));
        TextView val = label(value, 28, color, Typeface.BOLD);
        val.setPadding(0, dp(4), 0, 0);
        box.addView(val);
        LinearLayout.LayoutParams params = marginParams(0, 0, 6, 0);
        box.setLayoutParams(params);
        return box;
    }

    private View metricRow(String text, int color) {
        TextView view = label(text, 14, color, Typeface.BOLD);
        view.setPadding(dp(12), dp(8), dp(12), dp(8));
        view.setBackground(rounded(light(color), 12));
        return withMargin(view, 0, 0, 0, 8);
    }

    private View featureRow(int index, String title, String acceptance, int color) {
        LinearLayout row = vertical(12, CARD);
        row.setBackground(rounded(Color.rgb(250, 252, 254), 14, Color.rgb(225, 231, 238)));
        row.addView(label(String.format("%02d  %s", index, title), 15, INK, Typeface.BOLD));
        TextView standard = label("验收：" + acceptance, 13, MUTED, Typeface.NORMAL);
        standard.setPadding(0, dp(6), 0, 0);
        row.addView(standard);
        TextView status = label("静态数据已配置", 12, color, Typeface.BOLD);
        status.setPadding(0, dp(8), 0, 0);
        row.addView(status);
        return withMargin(row, 0, 0, 0, 10);
    }

    private void buildBottomNav() {
        bottomNav.removeAllViews();
        for (int i = 0; i < MODULES.length; i++) {
            final int index = i;
            TextView tab = label(MODULES[i].step + "\n" + MODULES[i].shortName, 12, MUTED, Typeface.BOLD);
            tab.setGravity(Gravity.CENTER);
            tab.setOnClickListener(v -> renderPage(index));
            bottomNav.addView(tab, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        }
    }

    private void refreshTabs() {
        for (int i = 0; i < bottomNav.getChildCount(); i++) {
            TextView tab = (TextView) bottomNav.getChildAt(i);
            boolean selected = i == selectedIndex;
            tab.setTextColor(selected ? Color.WHITE : MUTED);
            tab.setBackground(rounded(selected ? stepColor(MODULES[i].step) : Color.TRANSPARENT, 16));
        }
    }

    private LinearLayout card(int padding) {
        LinearLayout layout = vertical(padding, CARD);
        layout.setBackground(rounded(CARD, 22, Color.rgb(220, 228, 236)));
        return layout;
    }

    private LinearLayout vertical(int padding, int color) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(padding), dp(padding), dp(padding), dp(padding));
        layout.setBackgroundColor(color);
        return layout;
    }

    private TextView sectionTitle(String title) {
        TextView view = label(title, 17, INK, Typeface.BOLD);
        view.setPadding(0, dp(12), 0, dp(8));
        return view;
    }

    private TextView label(String value, int sp, int color, int style) {
        TextView textView = new TextView(this);
        textView.setText(value);
        textView.setTextSize(sp);
        textView.setTextColor(color);
        textView.setTypeface(Typeface.DEFAULT, style);
        textView.setIncludeFontPadding(true);
        return textView;
    }

    private TextView chip(String value, int bg, int fg) {
        TextView chip = label(value, 12, fg, Typeface.BOLD);
        chip.setGravity(Gravity.CENTER);
        chip.setPadding(dp(10), dp(6), dp(10), dp(6));
        chip.setBackground(rounded(bg, 20));
        chip.setLayoutParams(marginParams(0, 0, 8, 0));
        return chip;
    }

    private View withMargin(View view, int l, int t, int r, int b) {
        view.setLayoutParams(marginParams(l, t, r, b));
        return view;
    }

    private LinearLayout.LayoutParams marginParams(int l, int t, int r, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(dp(l), dp(t), dp(r), dp(b));
        return params;
    }

    private GradientDrawable rounded(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    private GradientDrawable rounded(int color, int radius, int stroke) {
        GradientDrawable drawable = rounded(color, radius);
        drawable.setStroke(dp(1), stroke);
        return drawable;
    }

    private int stepColor(String step) {
        if ("S1".equals(step)) return BLUE;
        if ("S2".equals(step)) return AMBER;
        if ("S3".equals(step)) return RED;
        if ("S4".equals(step)) return TEAL;
        return PURPLE;
    }

    private int light(int color) {
        int r = Math.min(255, Color.red(color) + 205);
        int g = Math.min(255, Color.green(color) + 190);
        int b = Math.min(255, Color.blue(color) + 175);
        return Color.rgb(r, g, b);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static class Module {
        final String step;
        final String shortName;
        final String fullName;
        final String goal;
        final String metricA;
        final String metricB;
        final String metricC;
        final String[] features;
        final String[] acceptance;

        Module(String step, String shortName, String fullName, String goal, String metricA, String metricB,
               String metricC, String[] features, String[] acceptance) {
            this.step = step;
            this.shortName = shortName;
            this.fullName = fullName;
            this.goal = goal;
            this.metricA = metricA;
            this.metricB = metricB;
            this.metricC = metricC;
            this.features = features;
            this.acceptance = acceptance;
        }
    }

    public static class ChartView extends View {
        static final int TYPE_BARS = 1;
        static final int TYPE_LINE = 2;
        private final int type;
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public ChartView(android.content.Context context, int type) {
            super(context);
            this.type = type;
            setPadding(0, 8, 0, 8);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (type == TYPE_BARS) {
                drawBars(canvas);
            } else {
                drawLine(canvas);
            }
        }

        private void drawBars(Canvas canvas) {
            int[] values = {62, 34, 20, 12};
            int[] colors = {Color.rgb(46, 160, 94), Color.rgb(229, 181, 60), Color.rgb(230, 126, 34), Color.rgb(204, 55, 66)};
            String[] labels = {"低", "中", "较高", "高"};
            float w = getWidth();
            float h = getHeight();
            float base = h - 34;
            float barW = w / 8f;
            paint.setTextSize(28);
            paint.setColor(Color.rgb(150, 161, 176));
            canvas.drawLine(8, base, w - 8, base, paint);
            for (int i = 0; i < values.length; i++) {
                float left = w * (0.12f + i * 0.22f);
                float top = base - values[i] * (h - 60) / 70f;
                paint.setColor(colors[i]);
                canvas.drawRoundRect(new RectF(left, top, left + barW, base), 14, 14, paint);
                paint.setColor(Color.rgb(32, 42, 58));
                paint.setTextSize(26);
                canvas.drawText(String.valueOf(values[i]), left + 4, top - 8, paint);
                paint.setColor(Color.rgb(91, 104, 126));
                paint.setTextSize(24);
                canvas.drawText(labels[i], left + 8, h - 6, paint);
            }
        }

        private void drawLine(Canvas canvas) {
            int[] values = {18, 27, 43, 58, 66, 72, 79};
            float w = getWidth();
            float h = getHeight();
            float left = 16;
            float right = w - 16;
            float top = 24;
            float bottom = h - 30;
            paint.setStrokeWidth(2);
            paint.setColor(Color.rgb(226, 232, 240));
            for (int i = 0; i < 4; i++) {
                float y = top + i * (bottom - top) / 3f;
                canvas.drawLine(left, y, right, y, paint);
            }
            paint.setColor(Color.rgb(204, 55, 66));
            paint.setStrokeWidth(3);
            float threshold = bottom - 50 * (bottom - top) / 90f;
            canvas.drawLine(left, threshold, right, threshold, paint);

            Path path = new Path();
            for (int i = 0; i < values.length; i++) {
                float x = left + i * (right - left) / (values.length - 1);
                float y = bottom - values[i] * (bottom - top) / 90f;
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            paint.setColor(Color.rgb(30, 95, 168));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawPath(path, paint);
            paint.setStyle(Paint.Style.FILL);
            for (int i = 0; i < values.length; i++) {
                float x = left + i * (right - left) / (values.length - 1);
                float y = bottom - values[i] * (bottom - top) / 90f;
                canvas.drawCircle(x, y, 7, paint);
            }
            paint.setColor(Color.rgb(91, 104, 126));
            paint.setTextSize(24);
            canvas.drawText("阈值 50", left, threshold - 8, paint);
        }
    }
}
