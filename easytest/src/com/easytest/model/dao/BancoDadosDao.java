/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.easytest.model.dao;

import com.easytest.files.exception.ReportTestException;
import com.easytest.model.datasource.ConexaoHSQLDB;
import com.easytest.model.vo.BancoDados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gilberto
 */
public class BancoDadosDao extends GenericDao{


    public void insert(BancoDados p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();

            String sql = "insert into banco_dados (i_projeto, driver, url, usuario, senha) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getIProjeto());
            //ps.setInt(1, 1);
            ps.setString(2, p.getDriverClass());
            ps.setString(3, p.getConnectionUrl());
            ps.setString(4, p.getUser());
            ps.setString(5, p.getPass());
            ps.execute();

            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw new ReportTestException(e.getMessage());
        } finally {
            try {
                closePS(ps);
            } finally {
                closeConn(conn);
            }
        }
    }

    public void update(BancoDados p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "update banco_dados set driver = ?, url = ?, usuario = ?, senha = ? where i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getDriverClass());
            ps.setString(2, p.getConnectionUrl());
            ps.setString(3, p.getUser());
            ps.setString(4, p.getPass());
            ps.setInt(5, p.getIProjeto());
            ps.execute();

            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw new ReportTestException(e.getMessage());
        } finally {
            try {
                closePS(ps);
            } finally {
                closeConn(conn);
            }
        }
    }

    public BancoDados getBancoDados(Integer codigo) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "select driver, url, usuario, senha from banco_dados where i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                String driver = rs.getString(1);
                String url = rs.getString(2);
                String usuario = rs.getString(3);
                String senha = rs.getString(4);

                BancoDados b = new BancoDados(codigo, driver, url, usuario, senha);
                return b;
            }
        } catch (SQLException e) {
            rollback(conn);
            throw new ReportTestException(e.getMessage());
        } finally {

            try {
                closeRS(rs);
            } finally {
                try {
                    closePS(ps);
                } finally {
                    closeConn(conn);
                }
            }
        }
        return null;
    }

}
