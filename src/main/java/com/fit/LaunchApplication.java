package com.fit;

import com.fit.util.JettyUtil;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @AUTO 启动入口
 * @Author AIM
 * @DATE 2021/6/11
 */
public class LaunchApplication {

    private static final Logger logger = LoggerFactory.getLogger(LaunchApplication.class);

    private final static String PORT_HOST = "HOST";
    private final static String PORT_ENV = "PORT";
    private static Server server;

    public static void main(String[] args) throws Exception {
        String port = getParam(PORT_ENV, "8080");
        String ip = getParam(PORT_HOST, "127.0.0.1");
        if (args.length != 0) {
            String tmp = getParam("tmp", "");
            String warDir = getParam(PORT_ENV, "");
            String context = getParam(PORT_ENV, "/");
            JettyUtil.createJettyServer(port, ip, tmp, warDir, context);
        }

        JettyUtil.unzipSelf();
        server = JettyUtil.getInstance();
        server.start();
        logger.info("\n---------------------------------------------------------\n" +
                "Application SM-Jetty is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External:\thttp://" + ip + ":" + port + "/" +
                "\n-----------------页面请部署 ---- web ----------------------");
        server.join();
    }

    private static String getParam(String param, String defVal) {
        Optional<String> envPort = Optional.ofNullable(System.getenv(param));
        return envPort.map(String::valueOf).orElse(defVal);
    }
}
