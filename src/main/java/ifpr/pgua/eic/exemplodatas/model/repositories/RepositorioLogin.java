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

    public Resultado criarLogin(String cpf, String senha){
        return dao.criarLogin(cpf, senha);
    }

    public Resultado validarLoginPaciente(String cpf, String senha){
        return dao.validarLoginPaciente(cpf,senha);
    }

    public Resultado validarLoginMedico(String cpf, String senha){
        return dao.validarLoginMedico(cpf,senha);
    }

    public Resultado alterarSenha(String cpf, String senha){
        return dao.alterarSenha(cpf, senha);
    }

    public Resultado alterarCpf(String cpfAntigo, String cpfNovo){
        return dao.alterarCpf(cpfAntigo, cpfNovo);
    }
}
