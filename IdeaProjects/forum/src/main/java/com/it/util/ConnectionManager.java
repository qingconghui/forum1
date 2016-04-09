package com.it.util;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static BasicDataSource dataSource = new BasicDataSource();

	static {

        Properties properties = new Properties();
        try {
            properties.load(ConnectionManager.class.getClassLoader().getResourceAsStream("config.properties"));

            dataSource.setDriverClassName(properties.getProperty("jdbc.driver"));
            dataSource.setUrl(properties.getProperty("jdbc.url"));
            dataSource.setUsername(properties.getProperty("jdbc.username"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));

            dataSource.setInitialSize(5);
            dataSource.setMaxIdle(20);
            dataSource.setMinIdle(5);
            dataSource.setMaxWaitMillis(5000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException(e,"读取config.properties文件异常");
        }
	}

    public static DataSource getDataSource() {
        return dataSource;
    }
	
	public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e,"获取数据库链接异常");
        }
    }
	

}
