package com.easytest.model.datasource;

import com.easytest.files.exception.ReportTestException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 Mantém as configurações necessárias para acesso ao banco de dados do HSQL_DB
 */
public class ConexaoHSQLDB {

    public static Connection getConnection() throws ReportTestException{
        Connection conn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/easytest", "sa", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw  new ReportTestException("Erro ao conectar com o banco de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw  new ReportTestException("Não foi encontrado o driver do HSQLDB");
        }

        return conn;
    }
}
