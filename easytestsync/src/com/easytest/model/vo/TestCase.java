/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.model.vo;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author gilberto
 */
public class TestCase implements Serializable {

    private static final long serialVersionUID = -699349314891034417L;
    private Integer iProject;
    private Integer iTestCase;
    private String name;
    private String description;
    private byte[] striptTest;//resultado esperado
    private byte[] striptExecuted;//resultado gerado durante a execução do caso de teste
    private String jasperReport;
    private String mensagens;
    private Map<String, Object> parametersReport;
    private boolean compareError;
    
    public TestCase() {
    }
    
    public TestCase(Integer iProject, Integer iTestCase, String name, String description, byte[] striptTest, byte[] striptExecuted, String jasperReport, String mensagens, Map<String, Object> parametersReport, boolean compareError) {
        this.iProject = iProject;
        this.iTestCase = iTestCase;
        this.name = name;
        this.description = description;
        this.striptTest = striptTest;
        this.striptExecuted = striptExecuted;
        this.jasperReport = jasperReport;
        this.mensagens = mensagens;
        this.parametersReport = parametersReport;
        this.compareError = compareError;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        TestCase ct = new TestCase();
        ct.setIProject(iProject);
        ct.setITestCase(iTestCase);
        ct.setName(name);
        ct.setDescription(description);
        ct.setJasperReport(jasperReport);
        ct.setMensagens(mensagens);
        ct.setCompareError(compareError);
        ct.setStriptExecuted(striptExecuted);
        ct.setStriptTest(striptTest);
        return ct;
    }    
    
    public boolean isCompareError() {
        return compareError;
    }
    
    public void setCompareError(boolean compareError) {
        this.compareError = compareError;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getIProject() {
        return iProject;
    }
    
    public void setIProject(Integer iProject) {
        this.iProject = iProject;
    }
    
    public Integer getITestCase() {
        return iTestCase;
    }
    
    public void setITestCase(Integer iTestCase) {
        this.iTestCase = iTestCase;
    }
    
    public String getJasperReport() {
        return jasperReport;
    }
    
    public void setJasperReport(String jasperReport) {
        this.jasperReport = jasperReport;
    }
    
    public String getMensagens() {
        return mensagens;
    }
    
    public void setMensagens(String mensagens) {
        this.mensagens = mensagens;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Map<String, Object> getParametersReport() {
        return parametersReport;
    }
    
    public void setParametersReport(Map<String, Object> parametersReport) {
        this.parametersReport = parametersReport;
    }
    
    public byte[] getStriptExecuted() {
        return striptExecuted;
    }
    
    public void setStriptExecuted(byte[] striptExecuted) {
        this.striptExecuted = striptExecuted;
    }
    
    public byte[] getStriptTest() {
        return striptTest;
    }
    
    public void setStriptTest(byte[] striptTest) {
        this.striptTest = striptTest;
    }
}
