package ifpr.pgua.eic.exemplodatas.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Funcionario;

public interface FuncionarioDAO {
    
    Resultado criar(Funcionario funcionario);

    Resultado listar();

    Resultado alterar();
}
