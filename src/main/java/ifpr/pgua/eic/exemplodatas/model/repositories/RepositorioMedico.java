package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.PessoaDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;

public class RepositorioMedico {

    private MedicoDAO dao;

    public RepositorioMedico(MedicoDAO dao){
        this.dao = dao;
    }

    public Resultado criar(Medico medico){
        System.out.println(medico+"REPOSITORIO");
        return dao.criar(medico);
    }

    public Resultado listar(){
        return dao.listar();
    }

    public Resultado<ArrayList<Medico>> filtrarNome(String inicio) {
        return dao.filtrarNome(inicio);
    }

    public Resultado<ArrayList<Medico>> filtrarEspecialidade(String especialidade) {
        return dao.filtrarEspecialidade(especialidade);
    }

    
}
