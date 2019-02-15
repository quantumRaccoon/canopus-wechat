package com.canopus.website.api.utils;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 10:35
 * @Description:
 */
@Slf4j
public final class HessianUtil {
    private static final SerializerFactory serializerFactory = new SerializerFactory();

    public static byte[] encode(Object obj) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        Hessian2Output h2o = new Hessian2Output(os);
        h2o.setSerializerFactory(serializerFactory);

        try {
            h2o.writeObject(obj);
        } finally {
            closeQuietly((Hessian2Output)h2o, os);
        }

        return os.toByteArray();
    }

    public static <T> T decode(byte[] data) throws IOException {
        Object obj = null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Hessian2Input hessian2Input = new Hessian2Input(in);
        hessian2Input.setSerializerFactory(serializerFactory);

        try {
            obj = hessian2Input.readObject();
        } finally {
            closeQuietly((Hessian2Input)hessian2Input, in);
        }

        return (T)obj;
    }

    private static void closeQuietly(Hessian2Output hessian2Output, Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }

            if (hessian2Output != null) {
                hessian2Output.close();
            }
        } catch (Exception var3) {
            log.warn("close hessian2Output failed", var3);
        }

    }

    private static void closeQuietly(Hessian2Input hessian2Input, Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }

            if (hessian2Input != null) {
                hessian2Input.close();
            }
        } catch (IOException var3) {
            log.warn("close hessian2Input failed", var3);
        }

    }

    private HessianUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
