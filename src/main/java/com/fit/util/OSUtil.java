package com.fit.util;

public class OSUtil {

	private static String OS = System.getProperty("os.name").toLowerCase();

	private static OSUtil _instance = new OSUtil();

	private OSType platform;

	private OSUtil() {
	}

	public static boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	public static boolean isMacOS() {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
	}

	public static boolean isMacOSX() {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
	}

	public static boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}

	public static boolean isOS2() {
		return OS.indexOf("os/2") >= 0;
	}

	public static boolean isSolaris() {
		return OS.indexOf("solaris") >= 0;
	}

	public static boolean isSunOS() {
		return OS.indexOf("sunos") >= 0;
	}

	public static boolean isMPEiX() {
		return OS.indexOf("mpe/ix") >= 0;
	}

	public static boolean isHPUX() {
		return OS.indexOf("hp-ux") >= 0;
	}

	public static boolean isAix() {
		return OS.indexOf("aix") >= 0;
	}

	public static boolean isOS390() {
		return OS.indexOf("os/390") >= 0;
	}

	public static boolean isFreeBSD() {
		return OS.indexOf("freebsd") >= 0;
	}

	public static boolean isIrix() {
		return OS.indexOf("irix") >= 0;
	}

	public static boolean isDigitalUnix() {
		return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
	}

	public static boolean isNetWare() {
		return OS.indexOf("netware") >= 0;
	}

	public static boolean isOSF1() {
		return OS.indexOf("osf1") >= 0;
	}

	public static boolean isOpenVMS() {
		return OS.indexOf("openvms") >= 0;
	}

	/**
	 * 获取操作系统名字
	 * 
	 * @return 操作系统名
	 */
	public static OSType getOSname() {
		if (isAix()) {
			_instance.platform = OSType.AIX;
		} else if (isDigitalUnix()) {
			_instance.platform = OSType.Digital_Unix;
		} else if (isFreeBSD()) {
			_instance.platform = OSType.FreeBSD;
		} else if (isHPUX()) {
			_instance.platform = OSType.HP_UX;
		} else if (isIrix()) {
			_instance.platform = OSType.Irix;
		} else if (isLinux()) {
			_instance.platform = OSType.Linux;
		} else if (isMacOS()) {
			_instance.platform = OSType.Mac_OS;
		} else if (isMacOSX()) {
			_instance.platform = OSType.Mac_OS_X;
		} else if (isMPEiX()) {
			_instance.platform = OSType.MPEiX;
		} else if (isNetWare()) {
			_instance.platform = OSType.NetWare_411;
		} else if (isOpenVMS()) {
			_instance.platform = OSType.OpenVMS;
		} else if (isOS2()) {
			_instance.platform = OSType.OS2;
		} else if (isOS390()) {
			_instance.platform = OSType.OS390;
		} else if (isOSF1()) {
			_instance.platform = OSType.OSF1;
		} else if (isSolaris()) {
			_instance.platform = OSType.Solaris;
		} else if (isSunOS()) {
			_instance.platform = OSType.SunOS;
		} else if (isWindows()) {
			_instance.platform = OSType.Windows;
		} else {
			_instance.platform = OSType.Others;
		}
		return _instance.platform;
	}
}

/**
 * @AUTO 系统类型枚举类
 * @FILE OSUtil.java
 * @DATE 2021年4月19日 下午3:26:40
 * @Author AIM
 * @Version
 */
enum OSType {

	Any("any"), //
	Linux("Linux"), //
	Mac_OS("Mac OS"), // 旧版苹果系统
	Mac_OS_X("Mac OS X"), // 新版苹果系统
	Windows("Windows"), // 微软系统
	OS2("OS/2"), // Operating System/2
	Solaris("Solaris"), //
	SunOS("SunOS"), //
	MPEiX("MPE/iX"), //
	HP_UX("HP-UX"), //
	AIX("AIX"), //
	OS390("OS/390"), //
	FreeBSD("FreeBSD"), //
	Irix("Irix"), //
	Digital_Unix("Digital Unix"), //
	NetWare_411("NetWare"), //
	OSF1("OSF1"), //
	OpenVMS("OpenVMS"), //
	Others("Others");//

	private OSType(String desc) {
		this.description = desc;
	}

	public String toString() {
		return description;
	}

	private String description;
}
