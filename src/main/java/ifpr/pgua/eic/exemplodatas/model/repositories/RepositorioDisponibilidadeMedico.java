package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.DisponibilidadeMedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.DisponibilidadeMedico;

public class RepositorioDisponibilidadeMedico {

    private DisponibilidadeMedicoDAO DAO;

    public RepositorioDisponibilidadeMedico(DisponibilidadeMedicoDAO horariosIndisponiveisDAO) {
        this.DAO = horariosIndisponiveisDAO;
    }
 
    public Resultado<DisponibilidadeMedico> registrarHorarios(DisponibilidadeMedico horariosIndisponiveis){
        return DAO.registrarHorarios(horariosIndisponiveis);
    } 

}
