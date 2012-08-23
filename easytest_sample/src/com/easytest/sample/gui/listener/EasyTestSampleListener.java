package com.easytest.sample.gui.listener;

import com.easytest.sync.SyncUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import javax.sound.midi.SysexMessage;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author gilberto
 */
public class EasyTestSampleListener implements ActionListener {

	private static final String DRIVER_CLASS = "org.hsqldb.jdbcDriver";
	private static final String CONNECTION_URL = "jdbc:hsqldb:hsql://localhost:9002/sampleJasperReport";
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("viewReport".equals(e.getActionCommand())) {
            try {
                viewReport("FirstJasper");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    

    private void viewReport(String reportName) throws JRException, SQLException, ClassNotFoundException, FileNotFoundException {
        
        JFileChooser f = new JFileChooser(new File("."));
        
        f.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if(!f.isDirectory() && !f.getName().endsWith(".jrxml")){
                    return false;
                }
                return true;
            }

            @Override
            public String getDescription() {
                return ".jrxml";
            }
        });
        f.showOpenDialog(null);
        
        File file = f.getSelectedFile();
        
        if(file == null){
            throw new FileNotFoundException("Selecione um arquivo");
        }
        
        JasperReport report = JasperCompileManager.compileReport(new FileInputStream(file));
        
        
        Connection conn = null;
        try{
            conn = getConnection();
            
            sendTestCase(conn, report, reportName);
            
            //JasperPrint jp = JasperFillManager.fillReport(new FileInputStream(new File("V:\\githug\\easytest_jasperreport\\trunk\\easytest_sample\\report\\FirstJasper.jasper")),new HashMap(),conn);
            JasperPrint jp = JasperFillManager.fillReport(report,new HashMap(),conn);
            JasperViewer.viewReport(jp);
            
        }finally{
            if(conn != null){
                conn.close();
            }
        }
        
    }
    
    
    
    private void sendTestCase(Connection conn, JasperReport report, String reportName){
			SyncUtils utils = new SyncUtils();
			//utils.sendTestCase(conn, report, map, reportName);
			try{
				utils.sendTestCase(conn, "localhost", 9580, report, new HashMap<String, Object>(), reportName);	
			}catch (Exception e) {
				e.printStackTrace();
			}
    
    }
    
	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName(DRIVER_CLASS);
		Properties prop = new Properties();
		prop.put("user", "SA");
		prop.put("password", "");
		return DriverManager.getConnection(CONNECTION_URL, prop);
	}
    
    
}
