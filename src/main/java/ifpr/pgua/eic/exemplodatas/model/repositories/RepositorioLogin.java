package ifpr.pgua.eic.exemplodatas.model.repositories;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.LoginDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;

public class RepositorioLogin {
    
    private LoginDAO dao;

    public RepositorioLogin(LoginDAO dao){
        this.dao = dao;
    }

    public Resultado validar(String cpf, String senha){

        return dao.validar(cpf,senha);
    }
}