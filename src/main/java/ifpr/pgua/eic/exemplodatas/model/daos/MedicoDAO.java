package ifpr.pgua.eic.exemplodatas.model.daos;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;

public interface MedicoDAO {
    
    Resultado criar(Medico medico);

    Resultado listar();

    Resultado<Medico> buscarPorId(int id);

    Resultado buscarPorCpf(String cpf);

    Resultado<ArrayList<Medico>> filtrarNome(String inicio);

    Resultado<ArrayList<Medico>> filtrarEspecialidade(String especialidade);

    Resultado<ArrayList<Medico>> filtrarEspecialidadeENome(String especialidade, String inicio);

    Resultado alterar(Medico medico);

}
