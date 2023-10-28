package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.PessoaDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;

public class RepositorioPessoa {
    
    private PessoaDAO dao;

    public RepositorioPessoa(PessoaDAO dao){
        this.dao = dao;
    }

    public Resultado criar(Pessoa pessoa){

        return dao.criar(pessoa);
    }

    public Resultado listar(){
        return dao.listar();
    }

    public Resultado buscarPorCpf(String cpf){
        return dao.buscarPorCpf(cpf);
    }

    public Resultado<ArrayList<Pessoa>> filtrarNome(String inicio) {
        return dao.filtrarNome(inicio);
    }

}
