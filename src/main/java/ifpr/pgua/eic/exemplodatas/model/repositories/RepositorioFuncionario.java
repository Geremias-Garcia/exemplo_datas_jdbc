package ifpr.pgua.eic.exemplodatas.model.repositories;

import ifpr.pgua.eic.exemplodatas.model.daos.FuncionarioDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Funcionario;

public class RepositorioFuncionario {

    private FuncionarioDAO dao;

    public RepositorioFuncionario(FuncionarioDAO dao){
        this.dao = dao;
    }
    
}
