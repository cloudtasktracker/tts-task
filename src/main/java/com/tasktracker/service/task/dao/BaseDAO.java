package com.tasktracker.service.task.dao;

import com.tasktracker.libraries.common.logging.LoggingCodes;
import com.tasktracker.libraries.common.logging.OperationLogger;
import com.tasktracker.libraries.common.security.Crypter;
import com.tasktracker.service.task.config.AppConf;
import java.sql.*;

/**
 *  
 * Created by jtolos on 14/01/2015.
 */
public class BaseDAO {
    
    public Connection con = null;
    
    public BaseDAO() {
        try {
            if(this.con == null) {
                Class.forName(AppConf.dbConf.getProperty("db.driver")).newInstance();
                this.con = DriverManager.getConnection(
                                                        AppConf.dbConf.getProperty("db.url") + AppConf.dbConf.getProperty("db.schema"),
                                                        AppConf.dbConf.getProperty("db.user"),
                                                        Crypter.decrypt(AppConf.dbConf.getProperty("db.password"))
                                                        );
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_DB_OPENING_CONN_EXCEPTION.getCode().replace("%1", e.getStackTrace().toString()),true);
            e.printStackTrace();
            operationLogger.operationFinished();
        }
    }

    /**
     *
     * @param preparedStatement
     * @param resultSet
     */
    public void closeAll(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if(preparedStatement!=null){
                preparedStatement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        } catch (SQLException e) {
            OperationLogger operationLogger = new OperationLogger(LoggingCodes.TTS_TASK_DB_CLOSING_CONN_EXCEPTION.getCode().replace("%1", e.getMessage()),true);
            operationLogger.operationFinished();
        }
    }
}
