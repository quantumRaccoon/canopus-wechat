package com.canopus.website.api.utils;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:45
 * @Description:
 */
@Slf4j
public final class PropertyFileUtil {
    private static Properties properties;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static String profileId = "";
    private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private static Properties activePropertyFiles = null;
    private static boolean initialized = false;

    private PropertyFileUtil() {
    }

    private static void init() {
        try {
            String fileNames = "application-files.properties";
            profileId = "";
            innerInit(fileNames);
            initialized = true;
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void init(String profile) {
        try {
            if (StringUtils.isBlank(profile)) {
                init();
            } else {
                profileId = profile;
                String fileNames = "application-" + profile + "-files.properties";
                innerInit(fileNames);
            }

            initialized = true;
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    private static void innerInit(String fileName) {
        try {
            String[] propFiles = activePropertyFiles(fileName);
            log.debug("读取属性文件：{}", ArrayUtils.toString(propFiles));
            properties = loadProperties(propFiles);
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    private static String[] activePropertyFiles(String fileName) {
        try {
            log.trace("read {}", fileName);
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream resourceAsStream = loader.getResourceAsStream(fileName);
            if (resourceAsStream == null) {
                return new String[0];
            } else {
                activePropertyFiles = new LinkedProperties();
                activePropertyFiles.load(resourceAsStream);
                Set<Object> fileKeySet = activePropertyFiles.keySet();
                String[] propFiles = new String[fileKeySet.size()];
                List<Object> fileList = new ArrayList<>();
                fileList.addAll(activePropertyFiles.keySet());

                for (int i = 0; i < propFiles.length; ++i) {
                    String fileKey = fileList.get(i).toString();
                    propFiles[i] = activePropertyFiles.getProperty(fileKey);
                }

                return propFiles;
            }
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    private static Properties loadProperties(String... resourcesPaths) {
        try {
            Properties props = new Properties();
            String[] var2 = resourcesPaths;
            int var3 = resourcesPaths.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String location = var2[var4];
                location = location.replace("classpath*:/", "");
                log.trace("Loading properties file from:" + location);
                if (resourceLoader.getResource(location).exists()) {
                    InputStreamReader isr = new InputStreamReader(resourceLoader.getResource(location).getInputStream(), "UTF-8");
                    Throwable var7 = null;

                    try {
                        propertiesPersister.load(props, isr);
                    } catch (Throwable var17) {
                        var7 = var17;
                        throw var17;
                    } finally {
                        if (var7 != null) {
                            try {
                                isr.close();
                            } catch (Throwable var16) {
                                var7.addSuppressed(var16);
                            }
                        } else {
                            isr.close();

                        }
                    }
                }
            }

            return props;
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    public static Set<String> getKeys() {
        return properties.stringPropertyNames();
    }

    public static Map<String, String> getKeyValueMap() {
        Set<String> keys = getKeys();
        Map<String, String> values = Maps.newHashMap();
        Iterator var2 = keys.iterator();

        while (var2.hasNext()) {
            String key = (String) var2.next();
            values.put(key, get(key));
        }

        return values;
    }

    public static String get(String key) {
        if (!initialized) {
            init();
        }

        String propertyValue = properties.getProperty(key);
        log.trace("获取属性key={}，value={}", key, propertyValue);
        return propertyValue;
    }

    public static String get(String key, String defaultValue) {
        try {
            if (!initialized) {
                init();
            }

            String propertyValue = properties.getProperty(key);
            String value = StringUtils.defaultString(propertyValue, defaultValue);
            log.trace("获取属性key={}，value={}", key, propertyValue);
            return value;
        } catch (Throwable var4) {
            throw var4;
        }
    }

    public static void add(String key, String value) {
        properties.put(key, value);
        log.debug("通过方法添加属性,key={},value={}", key, value);
    }

    public static Properties getActivePropertyFiles() {
        return activePropertyFiles;
    }

    public static String getProfile() {
        return profileId;
    }
}
