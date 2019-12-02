package com.lvhaifeng.cloud.common.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author haifeng.lv
 * @create 2018/2/12.
 */
public class StringEscapeEditor extends PropertyEditorSupport {

    public StringEscapeEditor() {
        super();
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        } else {
            String value = clearXss(text);
            setValue(value);
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }
    /**
     * 处理字符转义
     *
     * @param value
     * @return
     */
    private String clearXss(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
}
