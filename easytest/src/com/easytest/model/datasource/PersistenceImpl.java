/*
 * Possui os dados necessários, bem como as validações, para obter uma conexão com o banco de dados
 */
package com.easytest.model.datasource;

import com.easytest.files.DataManager;
import com.easytest.files.exception.ReportTestException;
import com.easytest.model.dao.BancoDadosDao;
import com.easytest.model.vo.BancoDados;
import com.easytest.model.vo.Projeto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.management.AttributeNotFoundException;

public class PersistenceImpl {

    private Connection conn;
    private static PersistenceImpl persistence;

    private PersistenceImpl() {
        //singleton
    }

    public static PersistenceImpl getInstance() {
        if (persistence == null) {
            persistence = new PersistenceImpl();
        }
        return persistence;
    }

    /**Retorna uma conexão com o banco de dados*/
    public final Connection getConn() throws AttributeNotFoundException, ClassNotFoundException, SQLException {

        if (conn != null) {
            return conn;
        }

        //busca as configurações do banco de dados
        BancoDadosDao dao = new BancoDadosDao();
        BancoDados banco = null;
        try {
            Projeto p = DataManager.getInstance().getProjeto();
            banco = dao.getBancoDados(p.getIProjeto());
            if (banco == null) {
                throw new AttributeNotFoundException("Não existe um banco de dados configurado para este projeto!");
            }
        } catch (ReportTestException er) {
            throw new AttributeNotFoundException(er.getMessage());
        }

        //obtém uma conexão com o banco de dados
        String driverClass = banco.getDriverClass();
        String user = banco.getUser();
        String pass = banco.getPass();

        if (driverClass == null || user == null || pass == null || driverClass.trim().length() == 0 || user.trim().length() == 0 || pass.trim().length() == 0) {
            throw new AttributeNotFoundException("Existem parâmetros de conexão com o banco de dados que não foram defidos! Favor analisar as configurações do sistema!");
        }

        String urlConnection = banco.getConnectionUrl();
        Class.forName(driverClass);
        Properties prop = new Properties();
        prop.put("user", user);
        prop.put("password", pass);
        conn = DriverManager.getConnection(urlConnection, prop);
        return conn;

    }

    public final void close() throws SQLException {
        if (conn != null) {

            try {
                conn.close();
            } finally {
                conn = null;
            }
        }
    }
}
