/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.model.dao;

import com.easytest.model.vo.TestCase;
import com.easytest.files.exception.ReportTestException;
import com.easytest.model.datasource.ConexaoHSQLDB;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CasoTesteDao extends GenericDao {

    public void insert(TestCase p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();

            //gera a próxima chave livre
            Integer codigo = freeId(conn, p.getIProject());

            String sql = "insert into caso_teste (i_projeto, i_caso_teste, nome, descricao, relatorio_jasper, mensagens, compare_error, script_test_data, script_execute_data, parametro) "
                    + "values(?,?,?,?,?,?,?,?,?,?) ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getIProject());
            ps.setInt(2, codigo);
            ps.setString(3, p.getName());
            ps.setString(4, p.getDescription());
            ps.setString(5, p.getJasperReport());
            ps.setString(6, p.getMensagens());
            ps.setString(7, p.getParametersReport().toString());
            if (p.isCompareError()) {
                ps.setString(7, "S");
            } else {
                ps.setString(7, "N");
            }

            ps.setBinaryStream(8, new ByteArrayInputStream(p.getStriptTest()), p.getStriptTest().length);

            if (p.getStriptExecuted() != null) {
                ps.setBinaryStream(9, new ByteArrayInputStream(p.getStriptExecuted()), p.getStriptExecuted().length);
            } else {
                ps.setNull(9, Types.BLOB);
            }

            //grava os parâmetros
            if (p.getParametersReport() != null) {
                byte[] pMap = mapToByteArray(p.getParametersReport());
                ps.setBinaryStream(10, new ByteArrayInputStream(pMap), pMap.length);
            } else {
                ps.setNull(10, Types.BLOB);
            }





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

    private byte[] mapToByteArray(Map<String, Object> dados) throws ReportTestException {

        //serializa e converte para bytes
        try {
            ObjectOutputStream out = null;
            ByteArrayOutputStream bro = new ByteArrayOutputStream();
            try {
                out = new ObjectOutputStream(bro);
                out.writeObject(dados);
                out.flush();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            byte[] bMap = bro.toByteArray();
            return bMap;
        } catch (Exception e) {
            throw new ReportTestException(e.getMessage());
        }
    }

    private Map<String, Object> byteArrayToMap(byte[] bMap) throws ReportTestException {
        ObjectInputStream ois = null;
        try {
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(bMap));
                Object o = ois.readObject();

                if (!(o instanceof Map)) {
                    throw new IOException("File not Object ");
                }
                Map<String, Object> config = (Map<String, Object>) o;
                return config;
            } finally {
                if (ois != null) {
                    ois.close();
                }
            }
        } catch (Exception e) {
            throw new ReportTestException(e.getMessage());

        }

    }

    public void update(TestCase p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();


            String sql = "update caso_teste  set nome = ?, descricao = ?, relatorio_jasper = ?, mensagens = ?, compare_error = ?, script_test_data = ?, script_execute_data = ?, parametro = ? where i_projeto = ? and i_caso_teste = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getJasperReport());
            ps.setString(4, p.getMensagens());
            if (p.isCompareError()) {
                ps.setString(5, "S");
            } else {
                ps.setString(5, "N");
            }

            ps.setBinaryStream(6, new ByteArrayInputStream(p.getStriptTest()), p.getStriptTest().length);

            if (p.getStriptExecuted() != null) {
                ps.setBinaryStream(7, new ByteArrayInputStream(p.getStriptExecuted()), p.getStriptExecuted().length);
            } else {
                ps.setNull(7, Types.BLOB);
            }

            //grava os parâmetros
            if (p.getParametersReport() != null) {
                byte[] pMap = mapToByteArray(p.getParametersReport());
                ps.setBinaryStream(8, new ByteArrayInputStream(pMap), pMap.length);
            } else {
                ps.setNull(8, Types.BLOB);
            }



            ps.setInt(9, p.getIProject());
            ps.setInt(10, p.getITestCase());



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

    public void delete(TestCase p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "delete from caso_teste where i_projeto = ? and i_caso_teste = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getIProject());
            ps.setInt(2, p.getITestCase());

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

    public List<TestCase> getAllCasoTeste(Integer codigo) throws ReportTestException {
        List<TestCase> result = new ArrayList<TestCase>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "select i_projeto, i_caso_teste, nome, descricao, relatorio_jasper, mensagens, compare_error, script_test_data, script_execute_data, parametro from caso_teste WHERE i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer iProjeto = rs.getInt(1);
                Integer iCasoTeste = rs.getInt(2);
                String nome = rs.getString(3);
                String descricao = rs.getString(4);
                String relatorioJasper = rs.getString(5);
                String mensagens = rs.getString(6);
                String compareError = rs.getString(7);
                byte[] scriptTest = blobToByteArray(rs.getBlob(8));
                byte[] scriptExecute = blobToByteArray(rs.getBlob(9));
                byte[] bMap = blobToByteArray(rs.getBlob(10));
                TestCase c = new TestCase(iProjeto, iCasoTeste, nome, descricao, scriptTest, scriptExecute, relatorioJasper, mensagens, byteArrayToMap(bMap), "S".equals(compareError));
                c.setParametersReport(byteArrayToMap(bMap));
                result.add(c);
            }
        } catch (SQLException e) {
            throw new ReportTestException(e.getMessage());
        } catch (IOException e) {
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
        return result;
    }

    private byte[] blobToByteArray(Blob blob) throws SQLException, IOException {
        if (blob != null) {
            InputStream is = blob.getBinaryStream();
            byte[] bList = new byte[is.available()];
            is.read(bList);

            if (bList.length == 0) {
                return null;
            }

            return bList;
        }
        return null;

    }

    private Integer freeId(Connection conn, Integer iProjeto) throws ReportTestException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer max = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "select max(i_caso_teste) from caso_teste where i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, iProjeto);
            rs = ps.executeQuery();
            if (rs.next()) {
                max = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new ReportTestException(e.getMessage());
        } finally {
            try {
                closeRS(rs);
            } finally {
                closePS(ps);
            }
        }

        return max == null ? 1 : ++max;

    }
}
