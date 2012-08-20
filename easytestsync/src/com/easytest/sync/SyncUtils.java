/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.sync;

import com.easytest.model.vo.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.jasperreports.engine.*;

/**
 *
 * @author gilberto
 */
//TODO: [GILBERTO] Adicionar logs com o log4j
//TODO: [GILBERTO] Adicionar esta implementação a um agente java para que não 
//seja necessário alterar o código fonte da aplicação
public class SyncUtils {

    /**
     * Escreve o relatório em XML, possibilitando que um PDF seja gerado
     * posteriormente com base neste XML
     *
     * @return um array de bytes com o conteúdo do xml exportado
     */
    private byte[] exportDataReportXML(Connection conn, JasperReport report, Map<String, Object> parameters)
            throws JRException, IOException {
        ByteArrayOutputStream baos = null;
        try {
            JasperPrint jp = JasperFillManager.fillReport(report, parameters, conn);
            baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToXmlStream(jp, baos);
            return baos.toByteArray();
        } finally {
            baos.close();
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

    public void sendTestCase(Connection conn, String host, int port, JasperReport report, Map<String, Object> map, String reportName) throws IOException, JRException {
        byte[] bReportXml;
        //O mapa deve ser clonado porque o próprio jasper adiciona novoso parâmetros a 
        //este mapa, os quais não podem ser Serializados
        Map<String, Object> cloneMapParameters = cloneParametersMap(map);

        bReportXml = exportDataReportXML(conn, report, map);
        TestCase ct = new TestCase();
        ct.setName("CT: " + reportName);
        ct.setJasperReport(reportName);
        ct.setDescription("Test Case: " + reportName);
        ct.setStriptTest(bReportXml);
        ct.setStriptExecuted(bReportXml);
        ct.setParametersReport(cloneMapParameters);

        sendServer(host, port, ct);
    }

    private void sendServer(String host, int port, Object transfer) throws UnknownHostException, IOException {

        Socket socket = new Socket(host, port);

        // fazendo tranaferência de informações via socket

        // recebe dados do servidor
        // DataInputStream dis = new
        // DataInputStream(socket.getInputStream());

        // envia os dados para o servidor
        DataOutputStream dos = new DataOutputStream(
                socket.getOutputStream());

        /*
         * dos.writeInt(3); dos.writeUTF("Teste de envio de texto via socket");
         */
        //ObjetoTransferido transfer = new ObjetoTransferido();
        //transfer.setIdade(30);
        //transfer.setNome("Gilberto");

        ObjectOutputStream oos = new ObjectOutputStream(dos);
        oos.writeObject(transfer);
        dos.flush();
        // dos.close();
        socket.shutdownOutput();

    }
}
