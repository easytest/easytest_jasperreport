package com.easytest.gui.listener;

import com.easytest.model.vo.TestCase;
import com.easytest.gui.PrincipalJFrame;
import java.awt.event.MouseEvent;
import javax.swing.JList;

public class CasoTesteListMouseListener extends AbstractMouseListener {

    private PrincipalJFrame frame;

    public CasoTesteListMouseListener(PrincipalJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if ("casoTesteJList".equals(e.getComponent().getName())) {
            JList list = (JList) e.getComponent();
            int index = list.getSelectedIndex();

            if (index == -1) {
                return;
            }
            CasoTesteListModel model = (CasoTesteListModel)list.getModel();
            TestCase casoTeste = (TestCase) model.getObjectAt(index);
            frame.updateCasoTeste(casoTeste);
        }
    }
}
