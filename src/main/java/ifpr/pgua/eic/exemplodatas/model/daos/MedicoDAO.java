package ifpr.pgua.eic.exemplodatas.model.daos;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;

public interface MedicoDAO {
    
    Resultado criar(Medico medico);

    Resultado listar();

    Resultado<ArrayList<Medico>> filtrarNome(String inicio);

    Resultado<ArrayList<Medico>> filtrarEspecialidade(String especialidade);

    Resultado alterar(Medico medico);

}