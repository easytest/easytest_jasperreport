package com.easytest.model.dao;

import com.easytest.files.exception.ReportTestException;
import com.easytest.model.datasource.ConexaoHSQLDB;
import com.easytest.model.vo.Projeto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDao extends GenericDao {

    public void insert(Projeto p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();

            //gera a próxima chave livre
            Integer codigo = freeId(conn);
            
            p.setIProjeto(codigo);
            
            String sql = "insert into projeto (i_projeto, nome, descricao, report, ativo) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.setString(2, p.getNome());
            ps.setString(3, p.getDescricao());
            ps.setString(4, p.getReport());
            ps.setString(5, p.getAtivo());
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

    public void update(Projeto p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "update projeto set nome = ?, descricao = ?, report = ?, ativo = ? where i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setString(3, p.getReport());
            ps.setString(4, p.getAtivo());
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

    public void updateAtivo(Projeto p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "update projeto set ativo = ? where i_projeto <> ?";
            ps = conn.prepareStatement(sql);
            ps.setNull(1, Types.VARCHAR);
            ps.setInt(2, p.getIProjeto());
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



    public void delete(Projeto p) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "delete from projeto where i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getIProjeto());
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

    public List<Projeto> getAllProjeto() throws ReportTestException {
        List<Projeto> result = new ArrayList<Projeto>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "select i_projeto, nome, descricao, report, ativo from projeto order by i_projeto";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer iProjeto = rs.getInt(1);
                String nome = rs.getString(2);
                String descricao = rs.getString(3);
                String report = rs.getString(4);
                String ativo = rs.getString(5);
                result.add(new Projeto(iProjeto, nome, descricao, report, ativo));
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
        return result;
    }

    private Integer freeId(Connection conn) throws ReportTestException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer max = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "select max(i_projeto) from projeto";
            ps = conn.prepareStatement(sql);
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

    public Projeto getProjeto(Integer codigo) throws ReportTestException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexaoHSQLDB.getConnection();
            String sql = "select nome, descricao, report, ativo from projeto where i_projeto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                String nome = rs.getString(1);
                String descricao = rs.getString(2);
                String report = rs.getString(3);
                String ativo = rs.getString(4);

                Projeto p = new Projeto(codigo, nome, descricao, report, ativo);
                return p;
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
