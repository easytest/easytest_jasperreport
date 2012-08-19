package com.easytest.gui.listener;

import com.easytest.model.vo.TestCase;
import com.easytest.files.DataManager;
import com.easytest.files.exception.ReportTestException;
import com.easytest.gui.BancoDadosJFrame;
import com.easytest.gui.ConfiguracaoJFrame;
import com.easytest.gui.EditorScriptJFrame;
import com.easytest.gui.PrincipalJFrame;
import com.easytest.model.dao.CasoTesteDao;
import com.easytest.model.datasource.PersistenceImpl;
import com.easytest.model.vo.KeyMap;
import com.easytest.model.vo.Projeto;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.AttributeNotFoundException;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBasePrintRectangle;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.convert.TextElementConverter;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PrincipalActionListener extends ActionListenerImpl {

    private PrincipalJFrame frame;
    private boolean isPlay;
    private boolean isRec;
    private ServerRec server;
    private ArrayList<KeyMap> elementsGenerate = new ArrayList<KeyMap>();
    private String mensageCompareReport = "";
    private CasoTesteDao dao = new CasoTesteDao();

    public PrincipalActionListener(PrincipalJFrame frame) {
        this.frame = frame;
    }

    private void excluir() {
        //atualiza a visão
        TestCase casoTeste = null;
        try {
            casoTeste = getSelectedCasoTesteJList();

        } catch (AttributeNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
            return;
        }

        //grava os dados
        try {
            dao.delete(casoTeste);
            openFileUpdateModel();
        } catch (ReportTestException er) {
            JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "Dados excluidos com sucesso!", frame.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void gravar() {
        //atualiza a visão
        TestCase casoTeste = null;
        try {
            casoTeste = getSelectedCasoTesteJList();
            frame.updateCasoTeste(casoTeste, true);

            //pega o model e seta novamente apenas para atualizar os dados visualmente
            JList list = frame.getCasosTesteJList();
            CasoTesteListModel model = (CasoTesteListModel) list.getModel();

            //atualiza a visão com as novas informações
            frame.getCasosTesteJList().setModel(model);

        } catch (AttributeNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
            return;
        }

        //grava os dados no banco de dados
        try {
            DataManager data = DataManager.getInstance();
            Projeto p = data.getProjeto();
            if (p == null) {
                throw new ReportTestException("Um projeto não foi selecionado!");
            }

            //insere, se não existir atualiza
            casoTeste.setIProject(p.getIProjeto());
            dao.update(casoTeste);

        } catch (ReportTestException er) {
            JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarScript() {
        //abre o Editor de Script
        //TODO: Verificar se é necessário abrir a janela em uma thread
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                TestCase casoTeste = null;
                try {
                    casoTeste = getSelectedCasoTesteJList();
                } catch (AttributeNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                    return;
                }

                EditorScriptJFrame f = new EditorScriptJFrame(casoTeste);
                centralizeFrame(f);
                f.setVisible(true);
            }
        });
    }

    private void play() {
        if (!isPlay) {
            isPlay = true;
            JList list = frame.getCasosTesteJList();
            new Thread(new PlayCasoTeste(list)).start();
        } else {
            JOptionPane.showMessageDialog(frame, "Os casos de teste já estão sendo executados!");
        }
    }

    private void rec() {

        //garante apenas que um projeto ativo está definifo
        try {
            DataManager.getInstance().getProjeto();
        } catch (ReportTestException e) {
            frame.killRecToggleButton();
            JOptionPane.showMessageDialog(frame, e.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!isRec) {
            isRec = true;

            //inicializa o servidor para gravar os dados dos casos de testes gerados pela aplicação analisada
            server = new ServerRec();
            new Thread(server).start();

        } else {

            //finaliza a execução do servidor antes de gravar os dados para que a porta não fique mais escutando, oou seja, recendo dados
            isRec = false;
            if (server != null) {
                server.shutdown();
                server = null;
            }
        }
    }

    private void openFileUpdateModel() throws ReportTestException {

        DataManager data = DataManager.getInstance();
        Projeto p = data.getProjeto();

        if (p != null) {
            JList list = frame.getCasosTesteJList();
            list.setModel(new CasoTesteListModel((ArrayList<TestCase>) dao.getAllCasoTeste(p.getIProjeto())));

            //atualiza o nome do projeto
            frame.getProjetoCorrenteJLabel().setText("Projeto: " + p.getNome());
        }
    }

    private void copiarCasoTeste() {
        TestCase casoTeste = null;
        try {
            casoTeste = getSelectedCasoTesteJList();
        } catch (AttributeNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
            return;
        }

        String nomeCasoTeste = JOptionPane.showInputDialog(frame, "Informe o nome do novo caso de teste:");

        if (nomeCasoTeste == null || nomeCasoTeste.trim().length() == 0) {
            JOptionPane.showMessageDialog(frame, "O nome do caso de teste é inválido!");
            return;
        }

        TestCase casoClone = null;
        try {
            casoClone = (TestCase) casoTeste.clone();
        } catch (CloneNotSupportedException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
            return;
        }

        casoClone.setName(nomeCasoTeste);

        try {
            dao.insert(casoClone);
            openFileUpdateModel();
        } catch (ReportTestException er) {
            JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {

        //botões
        if ("copiar".equals(e.getActionCommand())) {
            copiarCasoTeste();

        } else if ("gravar".equals(e.getActionCommand())) {
            //altera um caso de teste já gravado
            gravar();
        } else if ("excluir".equals(e.getActionCommand())) {
            excluir();


        } else if ("editarScript".equals(e.getActionCommand())) {
            editarScript();
        } else if ("resultadoEsperado".equals(e.getActionCommand())) {
            try {
                visualizarResultado(true);
            } catch (AttributeNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        } else if ("resultadoObtido".equals(e.getActionCommand())) {
            try {
                visualizarResultado(false);
            } catch (AttributeNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
            //barra de ferramentas
        } else if ("play".equals(e.getActionCommand())) {
            play();
        } else if ("rec".equals(e.getActionCommand())) {
            rec();

            //menus
        } else if ("novoProjeto".equals(e.getActionCommand())) {
            //abre a configuração do projeto
            //TODO: Verificar se é necessário abrir a janela em uma thread
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {

                    ConfiguracaoJFrame f = new ConfiguracaoJFrame(frame);
                    centralizeFrame(f);
                    f.setVisible(true);
                }
            });
        } else if ("fechar".equals(e.getActionCommand())) {
            //TODO: Questionar o usuário se ele realmente deseja sair do software
            System.exit(0);

        } else if ("bancoDados".equals(e.getActionCommand())) {
            //abre a configuração com o banco de dados
            //TODO: Verificar se é necessário abrir a janela em uma thread
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    BancoDadosJFrame fdb = new BancoDadosJFrame();
                    centralizeFrame(fdb);
                    fdb.setVisible(true);
                }
            });
            //chekBox
        } else if ("ocultarCasosTeste".equals(e.getActionCommand())) {

            if (frame.getOcultarCasosTesteSucessJCheckBox().isSelected()) {
                //atualiza o model do JList apenas com os dados dos CasosTeste que possuem erros
                try {

                    Projeto p = DataManager.getInstance().getProjeto();
                    ArrayList<TestCase> listCurrent = (ArrayList<TestCase>) dao.getAllCasoTeste(p.getIProjeto());

                    if (listCurrent != null) {
                        ArrayList<TestCase> listClone = new ArrayList<TestCase>();
                        for (TestCase c : listCurrent) {
                            //adiciona apenas o casos de teste que possuem erros a serem avaliados
                            if (c.isCompareError()) {
                                try {
                                    listClone.add((TestCase) c.clone());
                                } catch (CloneNotSupportedException ex) {
                                    //ignorado a exceção porque nunca irá ocorrer um erro, pois está tratado no CasoTeste
                                }

                            }
                        }
                        JList list = frame.getCasosTesteJList();
                        list.setModel(new CasoTesteListModel(listClone));

                    } else {
                        JOptionPane.showMessageDialog(frame, "Não existem Casos de Teste a serem analisados!", "Erro", JOptionPane.ERROR_MESSAGE);
                        frame.getOcultarCasosTesteSucessJCheckBox().setSelected(false);
                    }
                } catch (ReportTestException er) {
                    JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
                    frame.getOcultarCasosTesteSucessJCheckBox().setSelected(false);
                }

            } else {

                try {
                    openFileUpdateModel();
                } catch (ReportTestException er) {
                    JOptionPane.showMessageDialog(frame, er.getMessage(), frame.getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    protected JFrame getFrame() {
        return frame;
    }

    private TestCase getSelectedCasoTesteJList() throws AttributeNotFoundException {
        //busca os dados do arquivo a ser editado
        JList list = frame.getCasosTesteJList();
        int index = list.getSelectedIndex();

        if (index == -1) {
            throw new AttributeNotFoundException("Selecione um item no painel \"Casos de Teste\"!");
        }
        CasoTesteListModel model = (CasoTesteListModel) list.getModel();

        if (model == null) {
            throw new AttributeNotFoundException("Erro: Não foi definido o componente Model para o JList (\"Casos de Teste\")!");
        }

        TestCase casoTeste = (TestCase) model.getObjectAt(index);

        if (casoTeste == null) {
            throw new AttributeNotFoundException("O caso de teste selecionado não foi encontrado!");
        }
        return casoTeste;
    }

    /*Exibe o relatório com o JasperView. Resultados Esperado ou Obtidos*/
    private void visualizarResultado(boolean isResultadoEsperado) throws AttributeNotFoundException {

        //busca os dados do arquivo a ser editado
        TestCase casoTeste = getSelectedCasoTesteJList();

        byte[] bReport = null;

        if (isResultadoEsperado) {
            bReport = casoTeste.getStriptTest();
        } else {
            bReport = casoTeste.getStriptExecuted();
        }

        if (bReport == null) {
            throw new AttributeNotFoundException("Não existem informações do relatório a serem exibidas!");
        }

        InputStream is = null;
        try {
            is = new ByteArrayInputStream(bReport);

            JasperPrint jpCache = JRPrintXmlLoader.load(is);
            JasperViewer.viewReport(jpCache, false);

        } catch (JRException ex) {
            throw new AttributeNotFoundException("Problemas ao gerar o relatório: " + ex.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    throw new AttributeNotFoundException("Problemas ao fachar o Stream do relatório: " + ex.getMessage());
                }
            }
        }
    }

    private class PlayCasoTeste implements Runnable {

        private JList list;

        public PlayCasoTeste(JList list) {
            this.list = list;
        }

        public void run() {
            PersistenceImpl p = PersistenceImpl.getInstance();
            try {
                int size = list.getModel().getSize();

                //busca todos os dados para executar os testes
                for (int i = 0; i < size; i++) {
                    try {
                        list.setSelectedIndex(i);
                        //IMPLEMENTAR A COMPARAÇÃO AQUI
                        TestCase casoTeste = getSelectedCasoTesteJList();
                        if (casoTeste != null) {
                            try{
                                comparaReports(p, casoTeste);
                            }catch(ReportTestException e){
                                JOptionPane.showMessageDialog(frame, e.getMessage());
                                break;
                            }
                            
                        }

                        //grava os dados do caso de teste com erro
                        if (casoTeste.isCompareError()) {
                            try {
                                dao.update(casoTeste);
                            } catch (ReportTestException er) {
                                er.printStackTrace();
                                Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, er);
                            }
                        }

                    } catch (AttributeNotFoundException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                isPlay = false;
                frame.killPlayToggleButton();
            } finally {
                try {
                    p.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        }
    }

    private Map<String, Object> cloneParametersMap(Map<String, Object> map) {

        Map<String, Object> result = new HashMap<String, Object>();

        Iterator<String> key = map.keySet().iterator();

        while (key.hasNext()) {
            String k = key.next();
            result.put(k, map.get(k));
        }

        return result;
    }

    private void comparaReports(PersistenceImpl p, TestCase casoTeste) throws ReportTestException{
        //busca os dados da configuração
        DataManager data = DataManager.getInstance();
        try {
            Projeto projeto = data.getProjeto();

            if (projeto == null) {
                throw new ReportTestException("Não foi encontrado o projeto corrente");
            }

            //String pathJasperReports = config.getLocalReport() + "\\" + casoTeste.getRelatorioJasper() + ".jrxml";
            String pathJasperReports = projeto.getReport() + "\\" + casoTeste.getJasperReport() + ".jrxml";

            JasperReport report = JasperCompileManager.compileReport(pathJasperReports);

            //gera um clone do mapa, pois o JasperReport adiciona informações no mapa passado por parâmetro
            Map<String, Object> parametersJasper = cloneParametersMap(casoTeste.getParametersReport());

            JasperPrint jpGenerate = JasperFillManager.fillReport(report, parametersJasper, p.getConn());

            //compara os dados do relatório gerado e o relatório base gravado no caso de teste
            comparaDadosRel(jpGenerate, casoTeste);
            byte[] bReport = exportDataReportXml(jpGenerate);
            casoTeste.setStriptExecuted(bReport);


            if (mensageCompareReport != null && mensageCompareReport.trim().length() > 0) {
                casoTeste.setMensagens(mensageCompareReport);
                casoTeste.setCompareError(true);//indica que o caso de teste executado teve erros encontrados
                mensageCompareReport = "";
            }


            //TODO: Propagar exceções
        } catch (IOException ex) {
            Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (JRException ex) {
            Logger.getLogger(PrincipalActionListener.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private byte[] exportDataReportXml(JasperPrint jpGenerate) throws JRException, IOException {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToXmlStream(jpGenerate, bos);
            return bos.toByteArray();
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }

    private void comparaDadosRel(JasperPrint jpGenerate, TestCase casoTeste)
            throws JRException, IOException {

        //limpa a lista para que não fique em cache os valores das últimas execuções
        elementsGenerate.clear();

        // DADOS DE TESTE - GERADOS
        // JasperPrint jpGenerate = JasperFillManager.fillReport(report,
        // getParametersReport(), conn);
        List<JRPrintPage> jpGeneratePageList = jpGenerate.getPages();

        for (int page = 0; page < jpGeneratePageList.size(); page++) {
            // dados da página
            JRPrintPage pageGenerate = jpGeneratePageList.get(page);

            List<JRPrintElement> elementsGeneratePage = pageGenerate.getElements();

            for (JRPrintElement el : elementsGeneratePage) {
                if (el instanceof JRPrintText) {
                    elementsGenerate.add(new KeyMap(el.getKey(), page,
                            (JRPrintText) el, pageGenerate));
                }
            }
        }// end for


        byte[] bCurrent = casoTeste.getStriptTest();

        // DADOS DE TESTE - COMPARAÇÃO [armazenado em disco no report.xml]
        JasperPrint jpCache = JRPrintXmlLoader.load(new ByteArrayInputStream(bCurrent));

        List<JRPrintPage> jpCachePageList = jpCache.getPages();

        // ////////////////////////////////////////////////////////////
        // - COMPARA OS DADOS GERADOS COM AQUELES QUE ESTÃO EM CACHE,
        // VERIFICANDO AS DIFERENÇAS DE VALORES
        // - Os componentes que estão no relatório em cache que estão no gerado
        // são adicionados ao relatório gerado
        // ////////////////////////////////////////////////////////////

        for (int page = 0; page < jpCachePageList.size(); page++) {
            // dados da página
            JRPrintPage pageCache = jpCachePageList.get(page);
            JRPrintPage pageGenerate = jpGeneratePageList.get(page);

            List<JRPrintElement> elementsGeneratePage = pageCache.getElements();

            for (JRPrintElement el : elementsGeneratePage) {
                if (el instanceof JRPrintText) {
                    KeyMap key = new KeyMap(el.getKey(), page,
                            (JRPrintText) el, pageCache);
                    if (!elementsGenerate.contains(key)) {
                        addErrorReport(key, pageGenerate);
                    }
                }

            }
        }// end for

    }

    // adiciona a página a ser gerada o novo elemento
    private void addErrorReport(KeyMap map, JRPrintPage pageCache) {
        addErrorReport(map, pageCache, false);
    }

    private void addErrorReport(KeyMap map, JRPrintPage pageGenerate,
            boolean markFault) {

        JRPrintText text1 = (JRPrintText) map.getElement();

        String mensagem = "ID: " + text1.getKey() + " Valor: "
                + text1.getText();

        mensageCompareReport += mensagem + "\n";

        System.out.println(mensagem);

        // adiciona um retângulo
        JRBasePrintRectangle ret = new JRBasePrintRectangle(
                text1.getDefaultStyleProvider());
        ret.setForecolor(Color.RED);
        ret.setKey(text1.getKey() + "ret-generate");

        //
        ret.getLinePen().setLineWidth(0.5f);// espeçura da linha
        //ret.setMode((byte) 2);// aplica transparência ao retângulo
        ret.setMode(ModeEnum.TRANSPARENT);// aplica transparência ao retângulo
        ret.setY(text1.getY());
        ret.setX(text1.getX());
        ret.setWidth(text1.getWidth());
        ret.setHeight(text1.getHeight());
        ret.setBackcolor(Color.WHITE);
        // map.getPrintPage().addElement(ret);
        pageGenerate.addElement(ret);
        
        
        // adiciona um texto
        JRBasePrintText elNew = new JRBasePrintText(
                text1.getDefaultStyleProvider());

        
        
        elNew.setMode(ModeEnum.TRANSPARENT);
        elNew.setKey(text1.getKey() + "text-generate");
        elNew.setForecolor(Color.RED);
        
        //elNew.setBackcolor(Color.BLUE);
        elNew.setX(text1.getX());
        elNew.setY(text1.getY() - 7);//2

        // calcula o tamanho do componente texto para que não aja quebra de
        // linha e o final do texto seja reimpresso encima do início
        Double width = text1.getText().length() * 3.375;
        
        if (width.intValue() > text1.getWidth()) {
            System.out.println("PASSA_1");
            elNew.setWidth(width.intValue());
        } else {
            System.out.println("PASSA_2");
            elNew.setWidth(text1.getWidth());
        }

        elNew.setHeight(text1.getHeight());
        elNew.setFontSize(6);

        //elNew.setValue(text1.getText());
        //elNew.setTextAlignment(text1.getTextAlignment());//método substituido pelo getHorizontalAlignmentValue
        elNew.setHorizontalAlignment(text1.getHorizontalAlignmentValue());
        //elNew.setVerticalAlignment(text1.getVerticalAlignmentValue());
        System.out.println("INFORMA_TEX: " + text1.getText());
        elNew.setText(text1.getText());
        if (markFault) {
            elNew.setText("N/D");
        } else {
            elNew.setText(text1.getText());
        }

        TextElementConverter.measureTextElement(elNew);
        pageGenerate.addElement(elNew);
    }

    /*Esta classe implemenjta um servidor para controlar a criação de casos de testes a partir dos relatórios executado em qualquer aplicação*/
    public class ServerRec implements Runnable {

        private Socket socket = null;
        private ServerSocket serverSocket = null;

        public void shutdown() {
            try {
                if (socket != null) {
                    try {
                        socket.shutdownInput();
                        socket = null;
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                }
            } finally {
                if (serverSocket != null) {
                    try {
                        if (!serverSocket.isClosed()) {
                            serverSocket.close();
                        }

                        serverSocket = null;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
            System.out.println("A conexão do servidor foi finalizada!");
        }

        public void run() {

            try {
                serverSocket = new ServerSocket(9580, 1);


                while (isRec) {
                    System.out.println("O servidor esta aguardando uma conexão");

                    //TODO: Melhorar para não permitir que o socket fique aternamente
                    //esperando uma conexão quando o processo de gravação for interrompido
                    try {
                        socket = serverSocket.accept();
                    } catch (SocketException e) {
                        //conexão bortada
                        return;
                    }

                    System.out.println("Conectado...");
                    // recebe os dados enviados via sockets
                    DataInputStream dis = new DataInputStream(
                            socket.getInputStream());

                    ObjectInputStream ois = new ObjectInputStream(dis);

                    Object o = ois.readObject();

                    if (o != null && o instanceof TestCase) {
                        TestCase ot = (TestCase) o;
                        System.out.println("Nome.: " + ot.getName());
                        System.out.println("Descricao: " + ot.getDescription());
                        System.out.println("Data: " + ot.getStriptTest());

                        CasoTesteDao dao = new CasoTesteDao();
                        try {
                            Projeto projeto = DataManager.getInstance().getProjeto();
                            if (projeto != null) {
                                ot.setIProject(projeto.getIProjeto());
                            } else {
                                throw new ReportTestException("Não foi encontrado o projeto corretente para realizar a gravação dos casos de teste!");
                            }

                            dao.insert(ot);

                            //atualiza a camada de apresentação a cada caso de teste recebido
                            openFileUpdateModel();

                        } catch (ReportTestException ex) {
                            System.out.println("ERRO ao receber o objeto: " + ex.getMessage());
                        }

                    } else {
                        System.out.println("Objetos diferentes");
                    }

                    socket.shutdownInput();
                    socket = null;
                }


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
