package com.easytest.gui.listener;

import com.easytest.gui.EditorParametrosJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class EditorParametrosListener implements ActionListener {

    private EditorParametrosJFrame frame;

    public EditorParametrosListener(EditorParametrosJFrame frame) {
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {

        if ("adicionar".equals(e.getActionCommand())) {
            frame.getTableModelParametros().addRow(new Object[]{"TESTE", 230});
        } else if ("remover".equals(e.getActionCommand())) {
            removerElement();

        } else if ("gravar".equals(e.getActionCommand())) {
            //lê todos os dados mantidos na tabela
            int rowCount =  frame.getParametrosJTable().getRowCount();

            //apresenta os dados mantidos na tabela
            TableModel model = frame.getParametrosJTable().getModel();
            for(int i = 0; i < rowCount; i++){
                //TODO: Implementar
                System.out.println("PAR: " + model.getValueAt(i, 0) + " VA: " + model.getValueAt(i, 1));
            }
        }
    }

    private void removerElement() {
        int selectRow = frame.getParametrosJTable().getSelectedRow();

        if (selectRow == -1) {
            JOptionPane.showMessageDialog(frame, "Para excluir um parâmetro da lista, você deve selecionar um dos elementos!");
            return;
        }

        frame.getTableModelParametros().removeRow(selectRow);

    }
}
