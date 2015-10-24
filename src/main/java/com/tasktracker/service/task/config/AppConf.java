package com.tasktracker.service.task.config;

import com.tasktracker.libraries.common.logging.LoggingCodes;
import com.tasktracker.libraries.common.logging.OperationLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * Created by u6019943 on 08/07/2015.
 */
public class AppConf {

    public static Properties dbConf;
    public static Properties trackServiceConf;
    
    public AppConf(){
        dbConf = new Properties();
        trackServiceConf = new Properties();
        try {
            InputStream streamDbProperties = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf"+ File.separator+System.getenv("TTS_TASK_CONF")+ File.separator+"db.properties");
            dbConf.load(streamDbProperties);

            InputStream streamConfProperties = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf"+ File.separator+System.getenv("TTS_TASK_CONF")+ File.separator+"tts.track.properties");
            trackServiceConf.load(streamConfProperties);
        } catch (Exception e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TRACK_LOADING_CONFIG.getCode().replace("%1", e.getMessage()),true);
            operationLogger.operationFinished();
        }
    }
}
