package com.fit.util;

import java.io.File;
import java.util.Objects;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @AUTO JETTY工具类
 * @FILE JettyUtil.java
 * @DATE 2021年4月13日 下午7:29:44
 * @Author AIM
 * @Version
 */
public class JettyUtil {

	private static Logger logger = LoggerFactory.getLogger(JettyUtil.class);

	private static Server server;
	private static JettyBean jettyBean;

	static {
		jettyBean = new JettyBean();
		server = new Server(createThreadPool());
	}

	public static void createJettyServer(String port, String host, String tempDir, String webDir, String contextPath) {
		if (StringUtil.isNotBlank(port)) {
			jettyBean.setPort(port);
		} else {
			logger.info("未设置端口");
		}
		if (StringUtil.isNotBlank(host)) {
			jettyBean.setHost(host);
		} else {
			logger.info("未设置主机IP");
		}
		if (StringUtil.isNotBlank(tempDir)) {
			jettyBean.setTempDir(tempDir);
		} else {
			logger.info("未设置临时目录");
		}
		if (StringUtil.isNotBlank(webDir)) {
			jettyBean.setWebDir(webDir);
		} else {
			logger.info("未设置WAR包存放目录");
		}
		if (StringUtil.isNotBlank(contextPath)) {
			jettyBean.setContextPath(contextPath);
		} else {
			logger.info("未设置项目名称");
		}
	}

	public static Server getInstance() {
		server.addConnector(createConnector());
		server.setHandler(createHandlers());
		server.setStopAtShutdown(true);
		return server;
	}

	private static ThreadPool createThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(10);
		threadPool.setMaxThreads(100);
		return threadPool;
	}

	private static NetworkConnector createConnector() {
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(Integer.parseInt(jettyBean.getPort()));
		connector.setHost(jettyBean.getHost());
		return connector;
	}

	private static HandlerCollection createHandlers() {
		WebAppContext context = new WebAppContext();
		context.setContextPath(jettyBean.getContextPath());
		context.setWar(jettyBean.getWebDir());
		context.setTempDirectory(new File(jettyBean.getTempDir()));
		context.setClassLoader(Thread.currentThread().getContextClassLoader());
		context.setDescriptor(jettyBean.getWebXml());
		context.setResourceBase(jettyBean.getWebApp());

		GzipHandler gzipHandler = new GzipHandler();
		HandlerCollection handlerCollection = new HandlerCollection();
		handlerCollection.setHandlers(new Handler[] { context, gzipHandler });
		return handlerCollection;
	}

	public static void unzipSelf() {
		// 将jar自身解压
		String selfPath = FileUtil.getJarExecPath(JettyUtil.class);
		if (selfPath.endsWith(".jar")) {// 运行环境
			try {
				logger.info("正在将\n" + selfPath + "\n解压至\n" + jettyBean.getWebDir());
				JarUtils.unJar(selfPath, jettyBean.getWebDir());
			} catch (Exception e) {
				logger.error("解压web内容失败!", e);
			}
		}
		logger.info(selfPath);
	}
}

class JettyBean {
	private String port = "8080";
	private String host = "localhost";
	private String webDir = System.getProperty("user.dir");
	private String webXml = isStartupFromJar() ? webDir + "/WEB-INF/web.xml" : webDir
			+ "/src/main/webapp/WEB-INF/web.xml";
	private String tempDir = isStartupFromJar() ? webDir + "/tmp" : webDir + "/target/tmp";
	private String webApp = isStartupFromJar() ? webDir : webDir + "/src/main/webapp";
	private String contextPath = "/";

	public JettyBean() {
		super();
	}

	public JettyBean(String port, String host, String webDir, String webXml, String tempDir, String contextPath) {
		super();
		this.port = port;
		this.host = host;
		this.webDir = webDir;
		this.webXml = webXml;
		this.tempDir = tempDir;
		this.contextPath = contextPath;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getWebDir() {
		return webDir;
	}

	public void setWebDir(String webDir) {
		this.webDir = webDir;
	}

	public String getWebXml() {
		return webXml;
	}

	public void setWebXml(String webXml) {
		this.webXml = webXml;
	}

	public String getWebApp() {
		return webApp;
	}

	public void setWebApp(String webApp) {
		this.webApp = webApp;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public boolean isStartupFromJar() {
		String protocol = JettyBean.class.getResource("").getProtocol();
		if (Objects.equals(protocol, "jar")) {
			return true;
		} else if (Objects.equals(protocol, "file")) {
			return false;
		}
		return true;
	}
}
