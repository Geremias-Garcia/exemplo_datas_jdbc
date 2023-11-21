package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.AtendimentoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.PacienteDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Atendimento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;

public class RepositorioAtendimento {
    
    private AtendimentoDAO dao;
    private MedicoDAO medicoDAO;
    private PacienteDAO pacienteDAO;

    public RepositorioAtendimento(AtendimentoDAO dao, MedicoDAO medicoDAO, PacienteDAO pacienteDAO){
        this.dao = dao;
        this.medicoDAO = medicoDAO;
        this.pacienteDAO = pacienteDAO;
    }

    public Resultado registrar(Atendimento atendimento){
        return dao.registrar(atendimento);
    }

    public Resultado<ArrayList<Atendimento>> buscarAtendimentosPorId(int idPaciente){
        Resultado resultado = dao.buscarAtendimentosPorId(idPaciente); 
        
        ArrayList<Atendimento> atendimentos = new ArrayList<>(); 
        if(resultado.foiSucesso()){
            List<Atendimento> lista = (List<Atendimento>)resultado.comoSucesso().getObj();
         
            for(Atendimento atendimento:lista){
                Resultado r1 = medicoDAO.buscarPorId(atendimento.getId_medico());
                if(r1.foiErro()){
                    return r1;
                }
                Medico medico = (Medico)r1.comoSucesso().getObj();
                atendimento.setMedico(medico);

                Resultado r2 = pacienteDAO.buscarPorId(atendimento.getId_paciente());
                if(r1.foiErro()){
                    return r2;
                }

                Paciente paciente = (Paciente)r2.comoSucesso().getObj();
                atendimento.setPaciente(paciente);

                atendimentos.add(atendimento);
            }
        }   
        return Resultado.sucesso("ok", atendimentos);
    }

}
