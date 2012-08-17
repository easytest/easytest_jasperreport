/*
 * Esta classe gerencia os valores mantidos no componente JList do PrincipalJFrame
 */
package com.easytest.gui.listener;

import com.easytest.model.vo.TestCase;
import java.util.ArrayList;

/**
 * @author gilberto
 */
public class CasoTesteListModel extends javax.swing.AbstractListModel {

    private ArrayList<TestCase> testCaseList;

    public CasoTesteListModel(ArrayList<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }

    public CasoTesteListModel() {
        testCaseList = new ArrayList<TestCase>();
    }

    public int getSize() {
        return testCaseList == null ? 0 : testCaseList.size();
    }

    public Object getElementAt(int i) {

        return testCaseList == null ? null : testCaseList.get(i).getName();
    }

    public TestCase getObjectAt(int i) {

        return testCaseList == null ? null : testCaseList.get(i);
    }
}
