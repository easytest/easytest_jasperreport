/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.model.dao;

import com.easytest.files.exception.ReportTestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.management.relation.RelationException;

/**
 *
 * @author gilberto
 */
public class GenericDao {

    protected void rollback(Connection conn) throws ReportTestException {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new ReportTestException(ex.getMessage());
            }
        }
    }

    protected void closeConn(Connection conn) throws ReportTestException {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new ReportTestException(ex.getMessage());
            }
        }
    }

    protected void closePS(PreparedStatement ps) throws ReportTestException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                throw new ReportTestException(ex.getMessage());
            }
        }
    }
    protected void closeRS(ResultSet rs) throws ReportTestException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                throw new ReportTestException(ex.getMessage());
            }
        }
    }



}
