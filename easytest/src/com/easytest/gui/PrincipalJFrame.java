/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PrincipalJFrame.java
 *
 * Created on 28/01/2012, 16:03:38
 */
package com.easytest.gui;

import com.easytest.model.vo.TestCase;
import com.easytest.files.DataManager;
import com.easytest.files.exception.ReportTestException;
import com.easytest.gui.listener.CasoTesteListModel;
import com.easytest.gui.listener.CasoTesteListMouseListener;
import com.easytest.gui.listener.PrincipalActionListener;
import com.easytest.model.dao.CasoTesteDao;
import com.easytest.model.dao.ProjetoDao;
import com.easytest.model.vo.Projeto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author gilberto
 */
public class PrincipalJFrame extends javax.swing.JFrame {

    /** Creates new form PrincipalJFrame */
    public PrincipalJFrame() {
        initComponents();
        initValues();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        principalToolBar = new javax.swing.JToolBar();
        playToggleButton = new javax.swing.JToggleButton();
        recToggleButton = new javax.swing.JToggleButton();
        fonteDadosJPanel = new javax.swing.JPanel();
        casosTesteJPanel1 = new javax.swing.JPanel();
        casosTesteJLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        casosTesteJList = new javax.swing.JList();
        ocultarCasosTesteSucessJCheckBox = new javax.swing.JCheckBox();
        detalhesCasosTesteJPanel = new javax.swing.JPanel();
        informacoesCasosTesteJLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        nomeCasoTesteJLabel = new javax.swing.JLabel();
        descricaoCasoTesteJLabel = new javax.swing.JLabel();
        nomeRelatorioJasperReporJLabel = new javax.swing.JLabel();
        nomeCasoTesteJTextField = new javax.swing.JTextField();
        nomeRelatorioJasperReporJTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descricaoCasoTesteJTextPane = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mensagemJTextPane = new javax.swing.JTextPane();
        detalhesCasosTesteButtonsJPanel = new javax.swing.JPanel();
        gravarJButton = new javax.swing.JButton();
        editarScriptJButton = new javax.swing.JButton();
        resultadoEsperadoJButton = new javax.swing.JButton();
        resultadoObtidoJButton = new javax.swing.JButton();
        copiarJButton = new javax.swing.JButton();
        excluirJButton = new javax.swing.JButton();
        projetoCorrenteJLabel = new javax.swing.JLabel();
        principalMenuBar = new javax.swing.JMenuBar();
        arquivoJMenu = new javax.swing.JMenu();
        novoProjetoJMenuItem = new javax.swing.JMenuItem();
        fecharJMenuItem = new javax.swing.JMenuItem();
        configuracoesJMenu = new javax.swing.JMenu();
        bancoDadosJMenuItem = new javax.swing.JMenuItem();
        ajudaJMenu = new javax.swing.JMenu();
        sobreJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JasperReport Test");
        setResizable(false);

        principalToolBar.setRollover(true);

        playToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_green_play.png"))); // NOI18N
        playToggleButton.setToolTipText("Executar Casos de Teste");
        playToggleButton.setFocusable(false);
        playToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_grey_play.png"))); // NOI18N
        playToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playToggleButton.setActionCommand("play");
        playToggleButton.addActionListener(listener);
        principalToolBar.add(playToggleButton);

        recToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_blue_rec.png"))); // NOI18N
        recToggleButton.setToolTipText("Gravar Script de Teste");
        recToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_grey_rec.png"))); // NOI18N
        recToggleButton.setActionCommand("rec");
        recToggleButton.addActionListener(listener);
        principalToolBar.add(recToggleButton);

        casosTesteJPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        casosTesteJLabel.setText("Casos de Teste");

        casosTesteJList.setModel(getCasosTesteListModel());
        casosTesteJList.addMouseListener(mouselistener);
        casosTesteJList.setName("casoTesteJList");
        jScrollPane1.setViewportView(casosTesteJList);

        ocultarCasosTesteSucessJCheckBox.setText("Ocultar casos de teste executados com sucesso");
        ocultarCasosTesteSucessJCheckBox.setActionCommand("ocultarCasosTeste");
        ocultarCasosTesteSucessJCheckBox.addActionListener(listener);

        javax.swing.GroupLayout casosTesteJPanel1Layout = new javax.swing.GroupLayout(casosTesteJPanel1);
        casosTesteJPanel1.setLayout(casosTesteJPanel1Layout);
        casosTesteJPanel1Layout.setHorizontalGroup(
            casosTesteJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(casosTesteJPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(casosTesteJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGroup(casosTesteJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator1)
                        .addComponent(casosTesteJLabel, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ocultarCasosTesteSucessJCheckBox, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap())
        );
        casosTesteJPanel1Layout.setVerticalGroup(
            casosTesteJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(casosTesteJPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(casosTesteJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ocultarCasosTesteSucessJCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        detalhesCasosTesteJPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        informacoesCasosTesteJLabel.setText("Detalhes do Caso de Teste");

        nomeCasoTesteJLabel.setText("Nome:");

        descricaoCasoTesteJLabel.setText("Descrição:");

        nomeRelatorioJasperReporJLabel.setText("Relatório Jasper:");

        jScrollPane2.setViewportView(descricaoCasoTesteJTextPane);

        jLabel1.setText("Mensagens:");

        jScrollPane3.setViewportView(mensagemJTextPane);

        gravarJButton.setText("Gravar");
        gravarJButton.setActionCommand("gravar");
        gravarJButton.addActionListener(listener);

        editarScriptJButton.setText("Editar Script");
        editarScriptJButton.setActionCommand("editarScript");
        editarScriptJButton.addActionListener(listener);

        resultadoEsperadoJButton.setText("Resultado Esperado");
        resultadoEsperadoJButton.setActionCommand("resultadoEsperado");
        resultadoEsperadoJButton.addActionListener(listener);

        resultadoObtidoJButton.setText("Resultado Obtido");
        resultadoObtidoJButton.setActionCommand("resultadoObtido");
        resultadoObtidoJButton.addActionListener(listener);

        copiarJButton.setText("Copiar");
        copiarJButton.setActionCommand("copiar");
        copiarJButton.addActionListener(listener);

        excluirJButton.setText("Excluir");
        excluirJButton.setActionCommand("excluir");
        excluirJButton.addActionListener(listener);

        javax.swing.GroupLayout detalhesCasosTesteButtonsJPanelLayout = new javax.swing.GroupLayout(detalhesCasosTesteButtonsJPanel);
        detalhesCasosTesteButtonsJPanel.setLayout(detalhesCasosTesteButtonsJPanelLayout);
        detalhesCasosTesteButtonsJPanelLayout.setHorizontalGroup(
            detalhesCasosTesteButtonsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalhesCasosTesteButtonsJPanelLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(copiarJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gravarJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(excluirJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editarScriptJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultadoEsperadoJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultadoObtidoJButton)
                .addContainerGap())
        );
        detalhesCasosTesteButtonsJPanelLayout.setVerticalGroup(
            detalhesCasosTesteButtonsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalhesCasosTesteButtonsJPanelLayout.createSequentialGroup()
                .addGroup(detalhesCasosTesteButtonsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultadoObtidoJButton)
                    .addComponent(resultadoEsperadoJButton)
                    .addComponent(editarScriptJButton)
                    .addComponent(excluirJButton)
                    .addComponent(gravarJButton)
                    .addComponent(copiarJButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detalhesCasosTesteJPanelLayout = new javax.swing.GroupLayout(detalhesCasosTesteJPanel);
        detalhesCasosTesteJPanel.setLayout(detalhesCasosTesteJPanelLayout);
        detalhesCasosTesteJPanelLayout.setHorizontalGroup(
            detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                        .addComponent(informacoesCasosTesteJLabel)
                        .addGap(434, 434, 434))
                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                        .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(nomeRelatorioJasperReporJLabel)
                                    .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                                            .addGap(42, 42, 42)
                                            .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(descricaoCasoTesteJLabel)
                                                .addComponent(nomeCasoTesteJLabel)))
                                        .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                                            .addGap(34, 34, 34)
                                            .addComponent(jLabel1))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                                        .addComponent(nomeCasoTesteJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                    .addComponent(nomeRelatorioJasperReporJTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalhesCasosTesteJPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(detalhesCasosTesteButtonsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        detalhesCasosTesteJPanelLayout.setVerticalGroup(
            detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informacoesCasosTesteJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeCasoTesteJLabel)
                    .addComponent(nomeCasoTesteJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(descricaoCasoTesteJLabel))
                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeRelatorioJasperReporJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomeRelatorioJasperReporJLabel))
                .addGroup(detalhesCasosTesteJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addGroup(detalhesCasosTesteJPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detalhesCasosTesteButtonsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout fonteDadosJPanelLayout = new javax.swing.GroupLayout(fonteDadosJPanel);
        fonteDadosJPanel.setLayout(fonteDadosJPanelLayout);
        fonteDadosJPanelLayout.setHorizontalGroup(
            fonteDadosJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fonteDadosJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(casosTesteJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detalhesCasosTesteJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        fonteDadosJPanelLayout.setVerticalGroup(
            fonteDadosJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fonteDadosJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fonteDadosJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(detalhesCasosTesteJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(casosTesteJPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        projetoCorrenteJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        projetoCorrenteJLabel.setText("Projeto: Não informado");

        arquivoJMenu.setText("Arquivo");
        arquivoJMenu.setActionCommand("arquivo");

        novoProjetoJMenuItem.setText("Projeto");
        novoProjetoJMenuItem.setActionCommand("novoProjeto");
        novoProjetoJMenuItem.addActionListener(listener);
        arquivoJMenu.add(novoProjetoJMenuItem);

        fecharJMenuItem.setText("Fechar");
        fecharJMenuItem.setActionCommand("fechar");
        fecharJMenuItem.addActionListener(listener);
        arquivoJMenu.add(fecharJMenuItem);

        principalMenuBar.add(arquivoJMenu);

        configuracoesJMenu.setText("Configurações");

        bancoDadosJMenuItem.setText("Banco de dados");
        bancoDadosJMenuItem.setActionCommand("bancoDados");
        bancoDadosJMenuItem.addActionListener(listener);
        configuracoesJMenu.add(bancoDadosJMenuItem);

        principalMenuBar.add(configuracoesJMenu);

        ajudaJMenu.setText("Ajuda");
        ajudaJMenu.setActionCommand("ajuda");

        sobreJMenuItem.setText("Sobre");
        ajudaJMenu.add(sobreJMenuItem);

        principalMenuBar.add(ajudaJMenu);

        setJMenuBar(principalMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(principalToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(projetoCorrenteJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fonteDadosJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(principalToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(projetoCorrenteJLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fonteDadosJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PrincipalJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ajudaJMenu;
    private javax.swing.JMenu arquivoJMenu;
    private javax.swing.JMenuItem bancoDadosJMenuItem;
    private javax.swing.JLabel casosTesteJLabel;
    private javax.swing.JList casosTesteJList;
    private javax.swing.JPanel casosTesteJPanel1;
    private javax.swing.JMenu configuracoesJMenu;
    private javax.swing.JButton copiarJButton;
    private javax.swing.JLabel descricaoCasoTesteJLabel;
    private javax.swing.JTextPane descricaoCasoTesteJTextPane;
    private javax.swing.JPanel detalhesCasosTesteButtonsJPanel;
    private javax.swing.JPanel detalhesCasosTesteJPanel;
    private javax.swing.JButton editarScriptJButton;
    private javax.swing.JButton excluirJButton;
    private javax.swing.JMenuItem fecharJMenuItem;
    private javax.swing.JPanel fonteDadosJPanel;
    private javax.swing.JButton gravarJButton;
    private javax.swing.JLabel informacoesCasosTesteJLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextPane mensagemJTextPane;
    private javax.swing.JLabel nomeCasoTesteJLabel;
    private javax.swing.JTextField nomeCasoTesteJTextField;
    private javax.swing.JLabel nomeRelatorioJasperReporJLabel;
    private javax.swing.JTextField nomeRelatorioJasperReporJTextField;
    private javax.swing.JMenuItem novoProjetoJMenuItem;
    private javax.swing.JCheckBox ocultarCasosTesteSucessJCheckBox;
    private javax.swing.JToggleButton playToggleButton;
    private javax.swing.JMenuBar principalMenuBar;
    private javax.swing.JToolBar principalToolBar;
    private javax.swing.JLabel projetoCorrenteJLabel;
    private javax.swing.JToggleButton recToggleButton;
    private javax.swing.JButton resultadoEsperadoJButton;
    private javax.swing.JButton resultadoObtidoJButton;
    private javax.swing.JMenuItem sobreJMenuItem;
    // End of variables declaration//GEN-END:variables
    //código implementado manualmente
    private PrincipalActionListener listener = new PrincipalActionListener(this);
    private CasoTesteListMouseListener mouselistener = new CasoTesteListMouseListener(this);
    private ListModel casosTesteListModel;

    public javax.swing.JList getCasosTesteJList() {
        return casosTesteJList;
    }

    public void setCasosTestelistModel(ListModel casosTesteListModel) {
        this.casosTesteListModel = casosTesteListModel;
    }

    public ListModel getCasosTesteListModel() {
        if (casosTesteListModel == null) {
            casosTesteListModel = new CasoTesteListModel();
        }
        return casosTesteListModel;
    }

    public JLabel getProjetoCorrenteJLabel() {
        return projetoCorrenteJLabel;
    }

    public JCheckBox getOcultarCasosTesteSucessJCheckBox() {
        return ocultarCasosTesteSucessJCheckBox;
    }

    public final void updateCasoTeste(TestCase testCase) {
        updateCasoTeste(testCase, false);
    }

    //inicia as configurações do projeto
    public final void initValues() {

        try {
            Projeto currentProjeto = null;
            ProjetoDao daoProjeto = new ProjetoDao();
            List<Projeto> pList = daoProjeto.getAllProjeto();
            for (Projeto p : pList) {
                if ("S".equals(p.getAtivo())) {
                    currentProjeto = p;
                    break;
                }
            }

            if (currentProjeto == null) {
                return;
            }

            DataManager.getInstance().setProjeto(currentProjeto);

            CasoTesteDao daoCasoTeste = new CasoTesteDao();

            JList list = getCasosTesteJList();
            //list.setModel(new CasoTesteListModel(config.getCasoTesteList()));
            list.setModel(new CasoTesteListModel((ArrayList<TestCase>) daoCasoTeste.getAllCasoTeste(currentProjeto.getIProjeto())));

            //atualiza o nome do projeto
            getProjetoCorrenteJLabel().setText("Projeto: " + currentProjeto.getNome());
        } catch (ReportTestException er) {
            JOptionPane.showMessageDialog(this, er.getMessage(), this.getTitle(), JOptionPane.ERROR_MESSAGE);
        }

    }

    /**Atualizado a janela com as informações do caso de teste*/
    public final void updateCasoTeste(TestCase testCase, boolean save) {
        if (testCase == null) {
            return;
        }

        if (save) {
            //busca os dados da visão e atualiza o componente
            testCase.setName(nomeCasoTesteJTextField.getText());
            testCase.setDescription(descricaoCasoTesteJTextPane.getText());
            testCase.setJasperReport(nomeRelatorioJasperReporJTextField.getText());
            testCase.setMensagens(mensagemJTextPane.getText());

        } else {
            //atualiza a visão
            nomeCasoTesteJTextField.setText(testCase.getName());
            descricaoCasoTesteJTextPane.setText(testCase.getDescription());
            nomeRelatorioJasperReporJTextField.setText(testCase.getJasperReport());
            mensagemJTextPane.setText(testCase.getMensagens());
        }
    }

    public void killPlayToggleButton() {
        playToggleButton.setSelected(false);

    }

    public void killRecToggleButton() {
        recToggleButton.setSelected(false);

    }
}
