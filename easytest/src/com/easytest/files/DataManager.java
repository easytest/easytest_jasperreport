/*
 * Esta classe mantém as configurações do projeto que encontra-se aberto
 */
package com.easytest.files;

import com.easytest.files.exception.ReportTestException;
import com.easytest.model.vo.Projeto;

public class DataManager {

    private static DataManager manager;
    private Projeto projeto;

    private DataManager() {
        //singleton
    }

    public Projeto getProjeto() throws ReportTestException{
        if(projeto == null){
            throw  new ReportTestException("Um projeto deve ser definido como ativo!");
        }

        return projeto;
    }

    public void setProjeto(Projeto p) {
        this.projeto = p;
    }

    public synchronized static DataManager getInstance() {
        if (manager == null) {
            manager = new DataManager();
        }
        return manager;
    }
}
