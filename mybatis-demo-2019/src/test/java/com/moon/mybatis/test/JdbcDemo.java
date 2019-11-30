package com.moon.mybatis.test;

import com.moon.mybatis.entity.TUser;
import org.junit.Test;
import org.springframework.util.Assert;

// STEP 1. 导入sql相关的包
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC 使用Demo
 *
 * @author MoonZero
 * @version 1.0
 * @date 2019-11-30 11:39
 * @description
 */
public class JdbcDemo {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";

    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "123456";

    @Test
    public void QueryPreparedStatementDemo() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<TUser> users = new ArrayList<>();

        try {
            // STEP 2: 注册mysql的驱动
            Class.forName(JDBC_DRIVER);

            // STEP 3: 获得一个连接
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: 创建一个查询
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM t_user where userName= ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "moon");
            // 打印sql
            System.out.println(preparedStatement.toString());
            // 执行sql，获取操作结果
            ResultSet resultSet = preparedStatement.executeQuery();

            // STEP 5: 从resultSet中获取数据并转化成bean
            while (resultSet.next()) {
                System.out.println("------------------------------");
                // Retrieve by column name
                TUser user = new TUser();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setRealName(resultSet.getString("realName"));
                user.setSex(resultSet.getByte("sex"));
                user.setMobile(resultSet.getString("mobile"));
                user.setEmail(resultSet.getString("email"));
                user.setNote(resultSet.getString("note"));
                System.out.println(user.toString());

                users.add(user);
            }


        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            // STEP 6: 关闭连接
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                // nothing we can do
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // nothing we can do
                e.printStackTrace();
            }
        }

        System.out.println("-------------------------");
        System.out.println(String.format("there are %d users in the list!", users.size()));
    }

    @Test
    public void QueryStatementDemo() {
        Connection connection = null;
        Statement statement = null;
        List<TUser> users = new ArrayList<>();

        try {
            // STEP 2: 注册mysql的驱动
            Class.forName(JDBC_DRIVER);

            // STEP 3: 获得一个连接
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: 创建一个查询
            System.out.println("Creating statement...");
            statement = connection.createStatement();
            String userName = "moon";
            String sql = String.format("SELECT * FROM t_user where userName= '%s' ", userName);
            // 执行sql，获取操作结果
            ResultSet resultSet = statement.executeQuery(sql);
            // 打印sql
            System.out.println(statement.toString());

            // STEP 5: 从resultSet中获取数据并转化成bean
            while (resultSet.next()) {
                System.out.println("------------------------------");
                // Retrieve by column name
                TUser user = new TUser();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setRealName(resultSet.getString("realName"));
                user.setSex(resultSet.getByte("sex"));
                user.setMobile(resultSet.getString("mobile"));
                user.setEmail(resultSet.getString("email"));
                user.setNote(resultSet.getString("note"));
                System.out.println(user.toString());

                users.add(user);
            }


        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            // STEP 6: 关闭连接
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // nothing we can do
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // nothing we can do
                e.printStackTrace();
            }
        }

        System.out.println("-------------------------");
        System.out.println(String.format("there are %d users in the list!", users.size()));
    }

}
