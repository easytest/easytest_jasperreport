/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.model.vo;

/**
 *
 * @author gilberto
 */
//ok - revisado
public class Projeto {

    private Integer iProjeto;
    private String nome;
    private String descricao;
    private String report;
    private String ativo;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Projeto() {
    }

    public Projeto(Integer iProjeto, String nome, String descricao, String report, String ativo) {
        this.iProjeto = iProjeto;
        this.nome = nome;
        this.descricao = descricao;
        this.report = report;
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String desccricao) {
        this.descricao = desccricao;
    }

    public Integer getIProjeto() {
        return iProjeto;
    }

    public void setIProjeto(Integer iProjeto) {
        this.iProjeto = iProjeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
