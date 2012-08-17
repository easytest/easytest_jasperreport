/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.gui.listener;

import com.easytest.files.DataManager;
import com.easytest.files.exception.ReportTestException;
import com.easytest.gui.BancoDadosJFrame;
import com.easytest.model.dao.BancoDadosDao;
import com.easytest.model.vo.BancoDados;
import com.easytest.model.vo.Projeto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class BancoDadosActionListener implements ActionListener {

    private BancoDadosJFrame frame;
    private BancoDadosDao dao = new BancoDadosDao();
    public BancoDadosActionListener(BancoDadosJFrame frame) {
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {

        if ("gravar".equals(e.getActionCommand())) {

            try {
                Projeto p = DataManager.getInstance().getProjeto();
                if(p == null){
                    throw  new ReportTestException("Um projeto ainda não foi selecionado!");
                }

                BancoDados b = frame.getBancoDados();
                if (b.getIProjeto() == null) {
                    b.setIProjeto(p.getIProjeto());
                    dao.insert(b);
                } else {
                    dao.update(b);
                }

            } catch (ReportTestException er) {
                JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Dados gravados com sucesso!", frame.getTitle(), JOptionPane.INFORMATION_MESSAGE);

            //gera os dados da lista
            frame.initValues();

        }
    }
}
