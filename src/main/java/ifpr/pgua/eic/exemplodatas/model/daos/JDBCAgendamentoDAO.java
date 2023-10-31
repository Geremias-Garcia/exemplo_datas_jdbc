package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCAgendamentoDAO implements AgendamentoDAO{

    private static final String INSERTSQL = "INSERT INTO agendamento (id_paciente, id_medico, data, hora, status)\r\n" + //
            "VALUES (?, ?, ?, ?, ?);";

    FabricaConexoes fabrica;

    public JDBCAgendamentoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado agendar(Agendamento agendamento) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqlDate = java.sql.Date.valueOf(agendamento.getData());
            
            pstm.setInt(1, agendamento.getIdPaciente());
            pstm.setInt(2, agendamento.getIdMedico());
            pstm.setDate(3, sqlDate);
            pstm.setString(4, agendamento.getHora());
            pstm.setString(5, "teste");

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Pessoa cadastrada", agendamento);
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}
