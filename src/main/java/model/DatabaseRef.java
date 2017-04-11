package main.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Arber on 4/10/2017.
 * This class's purpose is to: <DESCRIBE PURPOSE>
 */
public class DatabaseRef {
    public Connection conn;
    public PreparedStatement preparedStatement;
    public Statement stmt;
    public ResultSet rs;

    public DatabaseRef() {}

    public void connect() throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_12",
                "cs4400_12",
                "DjEn0kw9");
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
}
