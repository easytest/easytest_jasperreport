/*
 * Mantém as configurações do banco de dados
 */
package com.easytest.model.vo;

import java.io.Serializable;

public class BancoDados implements Serializable {
    private Integer iProjeto;

    public Integer getIProjeto() {
        return iProjeto;
    }

    public void setIProjeto(Integer iProjeto) {
        this.iProjeto = iProjeto;
    }
    private String driverClass;
    private String connectionUrl;
    private String user;
    private String pass;

    public BancoDados() {
    }

    public BancoDados(Integer iProjeto, String driverClass, String connectionUrl, String user, String pass) {
        this.iProjeto = iProjeto;
        this.driverClass = driverClass;
        this.connectionUrl = connectionUrl;
        this.user = user;
        this.pass = pass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
