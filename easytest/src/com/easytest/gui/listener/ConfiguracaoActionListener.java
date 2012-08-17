package com.easytest.gui.listener;

import com.easytest.files.exception.ReportTestException;
import com.easytest.gui.ConfiguracaoJFrame;
import com.easytest.gui.PrincipalJFrame;
import com.easytest.model.dao.ProjetoDao;
import com.easytest.model.vo.Projeto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ConfiguracaoActionListener implements ActionListener {

    private ConfiguracaoJFrame frame;
    private PrincipalJFrame framePrincipal;
    private ProjetoDao dao = new ProjetoDao();

    public ConfiguracaoActionListener(ConfiguracaoJFrame frame, PrincipalJFrame framePrincipal) {
        this.frame = frame;
        this.framePrincipal = framePrincipal;
    }

    public void actionPerformed(ActionEvent e) {

        if ("novo".equals(e.getActionCommand())) {
            //zera os dados do formulário
            frame.clearForm();

        } else if ("gravar".equals(e.getActionCommand())) {

            try {
                Projeto p = frame.getProjeto();
                if (p.getIProjeto() == null) {
                    dao.insert(p);
                } else {
                    dao.update(p);
                }

                //altera os outros projetos para desativados
                dao.updateAtivo(p);

            } catch (ReportTestException er) {
                JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Dados gravados com sucesso!", frame.getTitle(), JOptionPane.INFORMATION_MESSAGE);

            //gera os dados da lista
            frame.initValues();
            framePrincipal.initValues();
        } else if ("excluir".equals(e.getActionCommand())) {

            try {
                Projeto p = frame.getProjeto();
                if (p.getIProjeto() == null) {
                    throw  new ReportTestException("Selecione um projeto!");
                }
                dao.delete(p);

            } catch (ReportTestException er) {
                JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Dados excluídos com sucesso!", frame.getTitle(), JOptionPane.INFORMATION_MESSAGE);

            //gera os dados da lista
            frame.initValues();

        } else if ("selecionarArquivoReport".equals(e.getActionCommand())) {
            //Seleciona um diretório onde está armazenado os relatórios (fonte JasperReport)
            frame.getReportsJTextField().setText(selectDirectory("Arquivos JasperReport"));
        }
    }

    private String selectDirectory(String title) {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.showOpenDialog(frame);
        return chooser.getSelectedFile().getAbsolutePath();

    }
}
