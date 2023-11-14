package ifpr.pgua.eic.exemplodatas.model.daos;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.DisponibilidadeMedico;

public interface DisponibilidadeMedicoDAO {
    
    Resultado<DisponibilidadeMedico> registrarHorarios(DisponibilidadeMedico horariosIndisponiveis);  
    
    Resultado<ArrayList<DisponibilidadeMedico>> verificarDiasAtendimento(int id);

}
