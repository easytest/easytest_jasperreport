package com.easytest.gui.listener;

import com.easytest.model.vo.TestCase;
import com.easytest.files.exception.ReportTestException;
import com.easytest.gui.EditorParametrosJFrame;
import com.easytest.gui.EditorScriptJFrame;
import com.easytest.model.dao.CasoTesteDao;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EditorScriptActionListener extends ActionListenerImpl {

    private EditorScriptJFrame frame;
    private TestCase casoTeste;

    public EditorScriptActionListener(EditorScriptJFrame frame) {
        this.frame = frame;
    }

    public final void addCurrentCasoTeste(TestCase casoTeste) {
        this.casoTeste = casoTeste;
        if (casoTeste.getStriptTest() != null) {
            try {
                frame.getEditorScriptPane().setText(new String(casoTeste.getStriptTest(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao setar o encold.\n" + e.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    @Override
    protected JFrame getFrame() {
        return frame;
    }

    //TODO: Testar
    private void gravarScript() {
        if (casoTeste != null) {
            try {
                casoTeste.setStriptTest(frame.getEditorScriptPane().getText().getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao setar o encold.\n" + e.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
            }
        }

        //atualiza no banco o caso de teste alterado
        CasoTesteDao dao = new CasoTesteDao();
        try {
            dao.update(casoTeste);
        } catch (ReportTestException er) {
            JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if ("gravar".equals(e.getActionCommand())) {
            gravarScript();
        } else if ("fechar".equals(e.getActionCommand())) {
            //TODO: Verificar se os dados devem ser gravados
            frame.dispose();
        } else if ("editarParametros".equals(e.getActionCommand())) {
            //TODO: Criar um método único para abrir todas as instâncias de JFrame existentes no projeto
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    EditorParametrosJFrame f = new EditorParametrosJFrame(casoTeste);
                    //centraliza o frame
                    centralizeFrame(f);
                    f.setVisible(true);
                }
            });

        }
    }
}
