package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Atendimento;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCAtendimentoDAO implements AtendimentoDAO {

    
    private static final String INSERTSQL = "INSERT INTO atendimento (id_paciente, id_medico, data, hora, descricao)\r\n" + //
            "VALUES (?, ?, ?, ?, ?);";
    private static final String BUSCARIDPACIENTE = "SELECT * FROM atendimento WHERE id_paciente = ?";

    private FabricaConexoes fabrica;

    public JDBCAtendimentoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado registrar(Atendimento atendimento) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqlDate = java.sql.Date.valueOf(atendimento.getData());
            java.sql.Time hora = java.sql.Time.valueOf(atendimento.getHora());
            
            pstm.setInt(1, atendimento.getPaciente().getId());
            pstm.setInt(2, atendimento.getMedico().getId());
            pstm.setDate(3, sqlDate);
            pstm.setTime(4, hora);
            pstm.setString(5, atendimento.getDescricao());

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Agendamento concluído", atendimento);
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Atendimento>> buscarAtendimentosPorId(int idPaciente) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(BUSCARIDPACIENTE);

            pstm.setInt(1, idPaciente);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Atendimento> lista = new ArrayList<>();
            while(rs.next()){
                int idAtendimento = rs.getInt("id");
                int idMedico = rs.getInt("id_medico");
                java.sql.Date sqlDate = rs.getDate("data");
                LocalDate data = sqlDate.toLocalDate();
                java.sql.Time sqlTime = rs.getTime("hora");
                LocalTime hora = sqlTime.toLocalTime();
                String descricao = rs.getString("descricao");

                Atendimento atendimento = new Atendimento(idAtendimento, idPaciente, idMedico, descricao, data, hora);
                lista.add(atendimento);
            }

            return Resultado.sucesso("Consultas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}
