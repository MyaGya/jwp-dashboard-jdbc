package com.techcourse.dao;


import com.techcourse.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

public abstract class InsertJdbcTemplate {

    private final DataSource dataSource;
    private final String query;

    public InsertJdbcTemplate(DataSource dataSource, String query) {
        this.dataSource = dataSource;
        this.query = query;
    }

    public void insert(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {

            conn = createConnection();
            pstmt = createPstmt(query, conn);
            setValuesForInsert(user, pstmt);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ignored) {
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    private PreparedStatement createPstmt(String sql, Connection conn) throws SQLException {
        return conn.prepareStatement(sql);
    }

    private Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }


    private void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {

        pstmt.setString(1, user.getAccount());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getEmail());
        pstmt.executeUpdate();

    }
}
