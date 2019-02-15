package com.canopus.website.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 10:24
 * @Description:
 */
public final class TemplateUtil {
    public static String parse(String template, Map args) {
        try {
            if (StringUtils.isBlank(template)) {
                return "";
            } else {
                StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
                Configuration cfg = Configuration.defaultConfiguration();
                GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
                Template t = gt.getTemplate(template);
                t.binding(args);
                return t.render();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TemplateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
