package com.fit.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * @AUTO 操作文件工具类
 * @DATE 2018-11-13 下午4:12:36
 * @Author AIM
 * @version v2.0
 * @Company 天际友盟
 */
public class FileUtil {

	public final static String charset = "UTF-8";

	/**
	 * 当前目录路径
	 */
	public static String currentWorkDir = System.getProperty("user.dir") + File.separator;

	/**
	 * 根据类得到Jar执行路径
	 */
	public static String getJarExecPath(Class<?> clazz) {
		String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (OSUtil.getOSname().equals(OSType.Windows)) {
			return path.substring(1);
		}
		return path;
	}

	/**
	 * 判断指定的文件是否存在。
	 * 
	 * @param fileName
	 */
	public static boolean isFileExist(String fileName) {
		return new File(fileName).exists();
	}

	/**
	 * 创建指定的目录
	 * 
	 * @param filePath
	 *            指定路径
	 */
	public static boolean makeDirectory(String filePath) {
		return makeDirectory(new File(filePath));
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 注意：可能会在返回false的时候创建部分父目录。
	 * 
	 * @param file
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/**
	 * 返回文件的URL地址。
	 * 
	 * @param file
	 * 
	 * @throws MalformedURLException
	 */
	public static URL getURL(File file) throws MalformedURLException {
		String fileURL = "file:/" + file.getAbsolutePath();
		URL url = new URL(fileURL);
		return url;
	}

	/**
	 * 从文件路径得到文件名。
	 * 
	 * @param filePath
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 从文件名得到文件绝对路径。
	 * 
	 * @param fileName
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 
	 * @param filePath
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace("", "/");
	}

	/**
	 * 从文件名得到UNIX风格的文件绝对路径。
	 * 
	 * @param fileName
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXpath(file.getAbsolutePath());
	}

	/**
	 * 得到文件后缀名
	 */
	public static String getFileExt(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLastIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLastIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
	 * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
	 */
	public static String getPathPart(String fileName) {
		int point = getPathLastIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return "";
		} else if (point == length - 1) {
			int secondPoint = getPathLastIndex(fileName, point - 1);
			if (secondPoint == -1) {
				return "";
			} else {
				return fileName.substring(0, secondPoint);
			}
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 */
	public static int getPathLastIndex(String fileName) {
		int point = fileName.lastIndexOf("/");
		if (point == -1) {
			point = fileName.lastIndexOf("");
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            实例文件名
	 * @param fromIndex
	 *            搜索指定字符位置开始
	 */
	public static int getPathLastIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf("/", fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf("", fromIndex);
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 */
	public static int getPathIndex(String fileName) {
		int point = fileName.indexOf("/");
		if (point == -1) {
			point = fileName.indexOf("");
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 * @param fromIndex
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		int point = fileName.indexOf("/", fromIndex);
		if (point == -1) {
			point = fileName.indexOf("", fromIndex);
		}
		return point;
	}

	/**
	 * 将文件名中的类型部分去掉。
	 */
	public static String removeFileExt(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 * 
	 * @param pathName
	 *            匹配路径名
	 * @param fileName
	 *            文件名
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	/**
	 * 删除指定路径下的文件。
	 */
	public static void deleteFile(String path) throws IOException {
		File file = new File(path);
		if (file.isDirectory()) {
			deleteDir(file);
		} else if (!file.exists()) {// 判断文件是否存在
			throw new IOException("IOException -> BadInputException: file is not exist.");
		} else if (!file.delete()) {// 判断文件是否已删除
			throw new IOException("Cannot delete file. filename = " + path);
		} else {
			deleteDir(file);
		}
	}

	/**
	 * 删除文件夹及其下面的子文件夹
	 */
	private static void deleteDir(File dir) throws IOException {
		if (dir.isFile())
			throw new IOException("IOException -> BadInputException: not a directory.");
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile()) {
					file.delete();
				} else {
					deleteDir(file);
				}
			}
		}
		dir.delete();
	}

	/**
	 * 复制文件
	 * 
	 * @param src
	 *            读取源文件
	 * @param dst
	 *            输出目的文件
	 */
	public static void copy(File src, File dst) {
		int BUFFER_SIZE = 4096;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
		}
	}

	/**
	 * 复制文件，支持把源文件内容追加到目标文件末尾
	 * 
	 * @param src
	 *            读取源文件
	 * @param dst
	 *            输出目的文件
	 * @param append
	 *            默认为false,如果文件不存在,则创建文件，否则重新创建覆盖文件。true则追加
	 */
	public static void copy(File src, File dst, boolean append) {
		int BUFFER_SIZE = 4096;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst, append), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFileName
	 *            源文件完整路径及文件名
	 * @param destDirName
	 *            目的目录完整路径
	 * @return 文件移动成功返回true，否则返回false
	 */
	public static boolean moveFile(String srcFileName, String destDirName, boolean isRename) {
		File srcFile = new File(srcFileName);
		// 判断文件是否存在
		if (!srcFile.exists() || !srcFile.isFile())
			return false;

		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdirs();

		String newName = srcFile.getName();
		if (isRename) {
			newName = "new_" + newName;
		}

		return srcFile.renameTo(new File(destDirName + File.separator + newName));
	}

	/**
	 * 移动目录
	 * 
	 * @param srcDirName
	 *            源目录完整路径
	 * @param destDirName
	 *            目的目录完整路径
	 * @return 目录移动成功返回true，否则返回false
	 */
	public static boolean moveDirectory(String srcDirName, String destDirName) {
		File srcDir = new File(srcDirName);
		if (!srcDir.exists() || !srcDir.isDirectory())
			return false;

		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdirs();

		// 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹 注意移动文件夹时保持文件夹的树状结构
		File[] sourceFiles = srcDir.listFiles();
		for (File sourceFile : sourceFiles) {
			if (sourceFile.isFile()) {
				moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath(), false);
			} else if (sourceFile.isDirectory()) {
				moveDirectory(sourceFile.getAbsolutePath(),
						destDir.getAbsolutePath() + File.separator + sourceFile.getName());
			}
		}
		return srcDir.delete();
	}

	/**
	 * 字符流写入 (输出流中含有中文时使用字符流)
	 * 
	 * @param path
	 *            文件导出路径
	 * @param fileName
	 *            文件名称
	 * @param str
	 *            导出内容
	 */
	public static String byteOutStream(String path, String fileName, String str) {
		try {
			if (WebUtil.isBlank(fileName)) {
				fileName = "log.txt";
			}
			// 1:使用File类创建一个要操作的文件路径
			File file = new File(path + File.separator + fileName);
			if (!file.getParentFile().exists()) { // 如果文件的目录不存在
				file.getParentFile().mkdirs(); // 创建目录
			}
			// 2: 实例化FileOutputStream 对象
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			// 3: 准备好实现内容的将字符串变为字节数组输出
			pw.write(str.toCharArray());
			// 4: 资源操作的最后必须关闭
			pw.flush();
			pw.close();
			return file.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写入文件中
	 * 
	 * @param path
	 *            文件导出路径
	 * @param fileName
	 *            文件名称
	 * @param str
	 *            导出内容
	 */
	public static void objOutStream(String path, String fileName, String str) throws Exception {
		if (WebUtil.isBlank(fileName)) {
			fileName = "log.txt";
		}
		// 1:使用File类创建一个要操作的文件路径
		File file = new File(path + File.separator + fileName);
		if (!file.getParentFile().exists()) { // 如果文件的目录不存在
			file.getParentFile().mkdirs(); // 创建目录
		}
		// 2: 实例化写文件 对象
		FileWriter fw = new FileWriter(file, true);
		// 3: 准备好实现内容的将字符串变为字节数组输出
		PrintWriter output = new PrintWriter(fw);
		output.println(str);
		output.flush();
		// 4: 资源操作的最后必须关闭
		output.close();
		fw.close();
	}

	/**
	 * 指定文件写入新内容
	 * 
	 * @param path
	 *            文件路径
	 * @param sb
	 *            写入内容
	 */
	public static void fileWriter(String path, StringBuffer sb) {
		try {
			File file = new File(path);
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(file, true);
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件返回字符串
	 */
	public static String getFile2String(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(file);
			Scanner sc = new Scanner(fis, charset);
			while (true) {
				if (!sc.hasNextLine()) {
					break;
				}
				String line = sc.nextLine();
				sb.append(line);
			}
			sc.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
