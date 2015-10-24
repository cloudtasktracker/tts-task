package com.tasktracker.service.task.starter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import com.tasktracker.service.task.config.AppConf;

public class TaskServiceStarter {

    final static Logger logger = Logger.getLogger("Starter");

    public static void main(String[] args) throws IOException {
        new AppConf();
        final String port = System.getenv("TTS_TASK_CONF").equals("wc")?System.getenv("TTS_TASK_PORT"):System.getenv("PORT");
        final String baseUri = "http://localhost:"+port+"/";
        final Map<String, String> initParams = new HashMap<>();
        initParams.put("com.sun.jersey.config.property.packages", "com.tasktracker.service.task.server");
        logger.info("----------- TTS-TASK: Grizzly Server starter at: "+baseUri);
        GrizzlyWebContainerFactory.create(baseUri, initParams);
    }
}
