package com.tasktracker.service.task.client;

import com.tasktracker.libraries.common.client.RestClient;
import com.tasktracker.libraries.common.logging.LoggingCodes;
import com.tasktracker.libraries.common.logging.OperationLogger;
import com.tasktracker.libraries.common.utils.StringUtils;
import com.tasktracker.service.task.config.AppConf;
import com.tasktracker.service.task.operation.TrackOperation;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by joan on 5/7/15.
 */
public class TrackServiceClient extends RestClient {

    public String addTrack(Long taskId){
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(taskId));

        return super.doPost(this.createTrackOperationUri(TrackOperation.ADD_TRACK, params));
    }

    public String putTrack(Long taskId, Long userId, String status){
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(taskId));
        params.add(String.valueOf(userId));
        params.add(String.valueOf(status));

        return super.doPost(this.createTrackOperationUri(TrackOperation.PUT_TRACK, params));
    }

    private String createTrackOperationUri(TrackOperation operation, List<String> params){
        String host = System.getenv("TTS_TASK_CONF").equals("wc")? AppConf.trackServiceConf.getProperty("service.track.host")+":":AppConf.trackServiceConf.getProperty("service.track.host");
        String port = System.getenv("TTS_TASK_CONF").equals("wc")?AppConf.trackServiceConf.getProperty("service.track.port"):"";
        String endpoint = AppConf.trackServiceConf.getProperty("service.track.endpoint");

        try{
            switch(operation){
                case ADD_TRACK:
                    return URIUtil.encodeQuery(host + port + endpoint + TrackOperation.ADD_TRACK.getOperation() + StringUtils.createPathParams(params));
                case PUT_TRACK:
                    return URIUtil.encodeQuery(host + port + endpoint + TrackOperation.PUT_TRACK.getOperation() + StringUtils.createPathParams(params));
                default:
                    return "";
            }
        } catch (URIException e){
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_CREATE_URI_EXCEPTION.getCode().replace("%1", e.getMessage()),true);
            operationLogger.operationFinished();
        }
        return "";
    }
}
