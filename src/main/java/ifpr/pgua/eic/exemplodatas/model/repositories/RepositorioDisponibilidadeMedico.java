package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.DisponibilidadeMedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.DisponibilidadeMedico;

public class RepositorioDisponibilidadeMedico {

    private DisponibilidadeMedicoDAO dao;

    public RepositorioDisponibilidadeMedico(DisponibilidadeMedicoDAO horariosIndisponiveisDAO) {
        this.dao = horariosIndisponiveisDAO;
    }
 
    public Resultado<DisponibilidadeMedico> registrarHorarios(DisponibilidadeMedico horariosIndisponiveis){
        return dao.registrarHorarios(horariosIndisponiveis);
    }
    
    public Resultado<ArrayList<DisponibilidadeMedico>> verificarDiasAtendimento(int id){
        return dao.verificarDiasAtendimento(id);
    }

}
