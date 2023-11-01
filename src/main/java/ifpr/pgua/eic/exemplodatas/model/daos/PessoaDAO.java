package ifpr.pgua.eic.exemplodatas.model.daos;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;

public interface PessoaDAO {
    
    Resultado criar(Pessoa pessoa);

    Resultado listar();

    Resultado<Pessoa> buscarPorId(int id);

    Resultado<ArrayList<Pessoa>> filtrarNome(String inicio);

    Resultado buscarPorCpf(String cpf);

    Resultado alterar(Pessoa pessoa);

}
