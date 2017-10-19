package com.verynet.gcint.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * Created by day on 08/10/2016.
 */
public class GeneralUtil {
    private static Logger logger = LoggerFactory.getLogger(GeneralUtil.class);

    public static String getApplicationDataDirectory() {
        String filepath = ConstantsUtil.APPLICATION_DATA_DIRECTORY;

        if (ConstantsUtil.UNIX_BASED_OPERATING_SYSTEM) {
            filepath = System.getProperty("user.home") + File.separator + ".ControlERM";
            if (!canWrite(new File(filepath))) {
                logger.warn("Unable to write to users home dir, fallback to: "
                        + ConstantsUtil.APPLICATION_DATA_DIRECTORY_FALLBACK_UNIX);
                filepath = ConstantsUtil.APPLICATION_DATA_DIRECTORY_FALLBACK_UNIX + File.separator + "ControlERM";
            }
        } else {
            filepath = System.getProperty("user.home") + File.separator + "Application Data" + File.separator
                    + "ControlERM";
            if (!canWrite(new File(filepath))) {
                logger.warn("Unable to write to users home dir, fallback to: "
                        + ConstantsUtil.APPLICATION_DATA_DIRECTORY_FALLBACK_WIN);
                filepath = ConstantsUtil.APPLICATION_DATA_DIRECTORY_FALLBACK_WIN + File.separator
                        + "ControlERM";
            }
        }

        filepath = filepath + File.separator;
        File folder = new File(filepath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        ConstantsUtil.APPLICATION_DATA_DIRECTORY = filepath;
        return filepath;
    }

    /**
     * Checks if we can write to a given folder.
     *
     * @param folder the directory to check.
     * @return true if we can write to it, else false.
     */
    private static boolean canWrite(File folder) {
        try {
            //We need to first create the folder if it does not exist,
            //else File.canWrite() will return false even when we
            //have the necessary permissions.
            if (!folder.exists()) {
                folder.mkdirs();
            }

            return folder.canWrite();
        } catch (SecurityException ex) {
            //all we wanted to know is whether we have permissions
        }

        return false;
    }

    public static boolean compareSystemProperties(String systemProperties) {
        String data = "";
        data += System.getProperty("user.name");
        data += System.getProperty("user.home");
        data += System.getProperty("os.name");
        data += System.getProperty("os.version");
        data += System.getProperty("sun.cpu.endian");
        data += System.getProperty("sun.desktop");
        Map<String, String> envData = System.getenv();
        if (ConstantsUtil.UNIX_BASED_OPERATING_SYSTEM) {
            data += envData.get("SSH_AGENT_PID");
        } else {
            data += System.getProperty("sun.cpu.isalist");
            data += envData.get("PROCESSOR_IDENTIFIER");
            data += envData.get("PROCESSOR_REVISION");
        }
        return systemProperties.equals(data);
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }
}
