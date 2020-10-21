package com.wkh;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class ClassFileVersionUtil {

    private static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    /*
     * Target   Major.minor Hex
     * 1.1      45.3        0x2D
     * 1.2      46.0        0x2E
     * 1.3      47.0        0x2F
     * 1.4      48.0        0x30
     * 5 (1.5)  49.0        0x31
     * 6 (1.6)  50.0        0x32
     * 7 (1.7)  51.0        0x33
     * 8 (1.8)  52.0        0x34
     * 9        53.0        0x35
     */
    // So you most certainly can't use sqljdbc4.jar with Java 5

    // 50.0 - JDK 6
    // private static final String DRIVER_CLASS_FILE_NAME = "\\sqljdbc4-3.0.0.jar\\SQLServerDriver.class";

    // 50.0 - JDK 6
    // private static final String DRIVER_CLASS_FILE_NAME = "\\sqljdbc4-2.0\\SQLServerDriver.class";

    // 49.0 - JDK 5
    // private static final String DRIVER_CLASS_FILE_NAME = "\\sqljdbc-2.0.jar\\SQLServerDriver.class";

    // 50.0 - JDK 6
    // private static final String DRIVER_CLASS_FILE_NAME = "\\sqljdbc3.jar\\SQLServerDriver.class";

    // 48.0 - JDK 4
    // private static final String DRIVER_CLASS_FILE_NAME = "\\sqljdbc_v1_2.jar\\SQLServerDriver.class";

    
    public static void printClassfileVersion(String classFileName) {
        try {
            ClassLoader loader = ClassFileVersionUtil.class.getClassLoader();
            InputStream in = new FileInputStream(classFileName);
            DataInputStream data = new DataInputStream(in);
            if (0xCAFEBABE != data.readInt()) {
                throw new IOException("invalid header");
            }
            int minor = data.readUnsignedShort();
            int major = data.readUnsignedShort();
            System.out.println(classFileName + ": " + major + "." + minor);

        } catch (IOException exception1) {
            throw new RuntimeException(exception1);
        }
    }
}