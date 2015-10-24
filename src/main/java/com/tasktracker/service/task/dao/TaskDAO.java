package com.tasktracker.service.task.dao;

import com.tasktracker.service.task.entity.Task;
import com.tasktracker.libraries.common.entity.TaskStatus;
import com.tasktracker.libraries.common.logging.LoggingCodes;
import com.tasktracker.libraries.common.logging.OperationLogger;
import com.tasktracker.service.task.config.AppConf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  
 * Created by jtolos on 14/01/2015.
 */
public class TaskDAO extends BaseDAO {

    public List<Task> listTask(String userId) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Task> taskList = null;
        
        try {
            taskList = new ArrayList<>();
            ps = con.prepareStatement(AppConf.dbConf.getProperty("sql.select.listTask"));
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                taskList.add(new Task(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_API_DB_LIST_TASK_EXCEPTION.getCode().replace("%1", e.getMessage()),true);
            operationLogger.operationFinished();
        } finally {
            super.closeAll(ps, resultSet);
        }
        return taskList;
    }

    public Task getTask(String taskId) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Task task = null;

        try {
            ps = con.prepareStatement(AppConf.dbConf.getProperty("sql.select.task"));
            ps.setString(1, taskId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                task = new Task(resultSet.getLong("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_API_DB_GET_TASK_EXCEPTION.getCode().replace("%1", taskId).replace("%2", e.getMessage()),true);
            operationLogger.operationFinished();
        } finally {
            super.closeAll(ps, resultSet);
        }
        return task;
    }

    public Long addTask(String name) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //insert the task
            ps = con.prepareStatement(AppConf.dbConf.getProperty("sql.insert.task"));
            ps.setString(1, name);
            ps.executeUpdate();

            //getting the generated id
            ps = con.prepareStatement(AppConf.dbConf.getProperty("sql.select.last.insert.id"));
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return resultSet.getLong("last_insert_id");
            }

        } catch (SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_API_DB_ADD_TASK_EXCEPTION.getCode().replace("%1", e.getMessage()),true);
            operationLogger.operationFinished();
        } finally {
            super.closeAll(ps, resultSet);
        }
        return null;
    }

    public void deleteTask(Long taskId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(AppConf.dbConf.getProperty("sql.delete.task"));
            ps.setLong(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_API_DB_DELETE_TASK_EXCEPTION.getCode().replace("%1",String.valueOf(taskId)).replace("%2", e.getMessage()), true);
            operationLogger.operationFinished();
        } finally {
            super.closeAll(ps, null);
        }
    }

    public void updateTask(Long userId, Long taskId, TaskStatus taskStatus) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(AppConf.dbConf.getProperty("sql.update.task"));
            ps.setLong(1, userId);
            ps.setLong(2, taskId);
            ps.setString(3, taskStatus.getStatus());
            ps.setLong(4, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_API_DB_UPDATE_TASK_EXCEPTION.getCode().replace("%1",String.valueOf(taskId)).replace("%2", e.getMessage()), true);
            operationLogger.operationFinished();
        } finally {
            super.closeAll(ps, null);
        }
    }
}
