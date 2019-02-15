package com.canopus.website.api.utils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:46
 * @Description:
 */
public class LinkedProperties extends Properties {
    private transient Map<Object, Object> linkMap = new LinkedHashMap<>();

    public LinkedProperties() {
    }

    public void clear() {
        this.linkMap.clear();
    }

    public boolean contains(Object value) {
        return this.linkMap.containsValue(value);
    }

    public boolean containsKey(Object key) {
        return this.linkMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.linkMap.containsValue(value);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.linkMap.entrySet();
    }

    public Object get(Object key) {
        return this.linkMap.get(key);
    }

    public String getProperty(String key) {
        Object oval = this.get(key);
        if (oval == null) {
            return null;
        } else {
            return oval instanceof String ? (String)oval : null;
        }
    }

    public boolean isEmpty() {
        return this.linkMap.isEmpty();
    }

    public Enumeration<Object> keys() {
        Set<Object> keys = this.linkMap.keySet();
        return Collections.enumeration(keys);
    }

    public Set<Object> keySet() {
        return this.linkMap.keySet();
    }

    public void list(PrintStream out) {
        this.list(new PrintWriter(out, true));
    }

    public void list(PrintWriter out) {
        out.println("-- listing properties --");

        String key;
        String val;
        for(Iterator var2 = this.entrySet().iterator(); var2.hasNext(); out.println(key + "=" + val)) {
            Map.Entry<Object, Object> e = (Map.Entry)var2.next();
            key = (String)e.getKey();
            val = (String)e.getValue();
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
        }

    }

    public Object put(Object key, Object value) {
        return this.linkMap.put(key, value);
    }

    public int size() {
        return this.linkMap.size();
    }

    public Collection<Object> values() {
        return this.linkMap.values();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LinkedProperties)) {
            return false;
        } else {
            LinkedProperties other = (LinkedProperties)o;
            return other.canEqual(this);
        }
    }

    private boolean canEqual(Object other) {
        return other instanceof LinkedProperties;
    }

    public int hashCode() {
        return 1;
    }
}
