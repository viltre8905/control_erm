package com.verynet.gcint.api.util;

/**
 * Created by day on 08/10/2016.
 */
public final class ConstantsUtil {
    public static String APPLICATION_DATA_DIRECTORY = null;
    /**
     * The directory which ControlERM should attempt to use as its application data directory
     * in case the current users home dir is not writeable (e.g. when using application servers
     * like tomcat to deploy ControlERM).
     *
     */
    public static String APPLICATION_DATA_DIRECTORY_FALLBACK_UNIX = "/var/lib";

    public static String APPLICATION_DATA_DIRECTORY_FALLBACK_WIN = System.getenv("%appdata%");

    public static final String OPERATING_SYSTEM_KEY = "os.name";

    public static final String OPERATING_SYSTEM = System.getProperty(OPERATING_SYSTEM_KEY);

    public static final String OPERATING_SYSTEM_WINDOWS_XP = "Windows XP";

    public static final String OPERATING_SYSTEM_WINDOWS_VISTA = "Windows Vista";

    public static final String OPERATING_SYSTEM_LINUX = "Linux";

    public static final String OPERATING_SYSTEM_SUNOS = "SunOS";

    public static final String OPERATING_SYSTEM_FREEBSD = "FreeBSD";

    public static final String OPERATING_SYSTEM_OSX = "Mac OS X";
    /**
     * Shortcut booleans used to make some OS specific checks more generic; note the *nix flavored
     * check is missing some less obvious choices
     */
    public static final boolean UNIX_BASED_OPERATING_SYSTEM = (OPERATING_SYSTEM.indexOf(OPERATING_SYSTEM_LINUX) > -1
            || OPERATING_SYSTEM.indexOf(OPERATING_SYSTEM_SUNOS) > -1
            || OPERATING_SYSTEM.indexOf(OPERATING_SYSTEM_FREEBSD) > -1 || OPERATING_SYSTEM.indexOf(OPERATING_SYSTEM_OSX) > -1);
}
