package com.lvhaifeng.cloud.generator;

import com.lvhaifeng.generator.window.GeneratorWindow;

/**
 * @Description 单表生成器
 * @Author haifeng.lv
 * @Date 2019/12/16 18:01
 */
public class Generator {
    /**
     * 是否生成 java
     */
    private static final boolean ISJAVA = true;
    /**
     * 是否生成 vue
     */
    private static final boolean ISVUE = true;

    public static void main(String[] args) {
        // 默认构造器则都为 true
        // new GeneratorWindow();
        new GeneratorWindow(ISJAVA, ISVUE).pack();
    }

}
