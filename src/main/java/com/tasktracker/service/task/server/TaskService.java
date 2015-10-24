package com.tasktracker.service.task.server;

import com.tasktracker.libraries.common.entity.TaskStatus;
import com.tasktracker.libraries.common.logging.LoggingCodes;
import com.tasktracker.libraries.common.logging.OperationLogger;
import com.tasktracker.libraries.common.utils.JSONUtils;
import com.tasktracker.service.task.client.TrackServiceClient;
import com.tasktracker.service.task.dao.TaskDAO;

import javax.ws.rs.*;

/**
 *
 * Created by joan on 4/7/15.
 */
@Path("/service/task")
public class TaskService {

    private TaskDAO taskDAO;
    private TrackServiceClient trackServiceClient;
    
    public TaskService(){
        this.taskDAO = new TaskDAO();
        this.trackServiceClient = new TrackServiceClient();
    }

    @GET
    @Path("/list/{userId: .*}")
    @Produces("application/json")
    public String listTask(@PathParam("userId")String userId) {
        OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_LIST.getCode().replace("%1", userId),false);
        String response = JSONUtils.objectToJson(this.taskDAO.listTask(userId));
        operationLogger.operationFinished();
        return response;
    }

    @GET
    @Path("/get/{taskId: .*}")
    @Produces("application/json")
    public String getTask(@PathParam("taskId")String taskId) {
        OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_GET.getCode(),false);
        String response = JSONUtils.objectToJson(this.taskDAO.getTask(taskId));
        operationLogger.operationFinished();
        return response;
    }

    @POST
    @Path("/add/{name}")
    @Produces("application/json")
    public String addTask(@PathParam("name") String name) {
        OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_ADD.getCode(),false);
        Long taskId = this.taskDAO.addTask(name);
        this.trackServiceClient.addTrack(taskId);
        operationLogger.operationFinished();
        return JSONUtils.objectToJson("New task added: "+name);
    }

    @DELETE
    @Path("/delete/{taskId}")
    @Produces("application/json")
    public String deleteTask(@PathParam("taskId") Long taskId) {
        OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_DELETE.getCode(),false);
        this.taskDAO.deleteTask(taskId);
        operationLogger.operationFinished();
        return JSONUtils.objectToJson("Task deleted: "+taskId);
    }

    @PUT
    @Path("/do/{userId}/{taskId}")
    @Produces("application/json")
    public String updateTask(@PathParam("userId") Long userId, @PathParam("taskId") Long taskId) {
        OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_UPDATE.getCode(),false);
        this.trackServiceClient.putTrack(taskId, userId, TaskStatus.DONE.getStatus());
        operationLogger.operationFinished();
        return JSONUtils.objectToJson("Task updated: "+taskId);
    }
}
