package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCAgendamentoDAO implements AgendamentoDAO{

    private static final String INSERTSQL = "INSERT INTO agendamento (id_paciente, id_medico, data, hora, status)\r\n" + //
            "VALUES (?, ?, ?, ?, ?);";
    private static final String BUSCARIDPACIENTE = "SELECT * FROM agendamento WHERE id_paciente = ?";
    private static final String BUSCARHORARIOSDISPONIVEIS = "SELECT hora FROM agendamento WHERE id_medico = ? AND data = ?";
    private static final String TOTALCONSULTASAGUARDANDO = "SELECT COUNT(*) AS count FROM agendamento WHERE status = 'Aguardando'";
    private static final String CONSULTASAGUARDANDO = "SELECT * FROM agendamento WHERE status = 'Aguardando'";
    private static final String UPDATE_STATUS = "UPDATE agendamento SET status = ? WHERE id = ?";

    FabricaConexoes fabrica;

    public JDBCAgendamentoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado agendar(Agendamento agendamento) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqlDate = java.sql.Date.valueOf(agendamento.getData());
            
            pstm.setInt(1, agendamento.getPessoa().getId());
            pstm.setInt(2, agendamento.getMedico().getId());
            pstm.setDate(3, sqlDate);
            pstm.setString(4, agendamento.getHora());
            pstm.setString(5, agendamento.getStatus());

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

    @Override
    public Resultado<ArrayList<Agendamento>> buscarIdPaciente(int id) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(BUSCARIDPACIENTE);

            pstm.setInt(1, id);
            System.out.println(id);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Agendamento> lista = new ArrayList<>();
            while(rs.next()){
                int idAgendamento = rs.getInt("id");
                int idMedico = rs.getInt("id_medico");
                java.sql.Date sqlDate = rs.getDate("data");
                LocalDate data = sqlDate.toLocalDate();
                String hora = rs.getString("hora");
                String status = rs.getString("status");

                Agendamento agendamento = new Agendamento(idAgendamento, id, idMedico, data, hora, status);
                lista.add(agendamento);
            }

            return Resultado.sucesso("Consultas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<String>> verificarDisponibilidadeHorario(int medicoId, LocalDate data) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(BUSCARHORARIOSDISPONIVEIS);

            pstm.setInt(1, medicoId);
            java.sql.Date sqlDate = java.sql.Date.valueOf(data);
            pstm.setDate(2, sqlDate);

            ResultSet rs = pstm.executeQuery();

            ArrayList<String> lista = new ArrayList<>();
            while (rs.next()) {
                String hora = rs.getString("hora");
                lista.add(hora);
            }

            return Resultado.sucesso("Consultas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<Integer> verificarNumeroConsultasEmAguardo() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(TOTALCONSULTASAGUARDANDO);

            ResultSet rs = pstm.executeQuery();
            int count = 0;

            if (rs.next()) {
                count = rs.getInt("count");
                return Resultado.sucesso("Ok", count);
            } else {
                return Resultado.erro("Nenhum resultado encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Resultado.erro("Erro ao verificar o número de consultas em aguardo");
        }
    }

    @Override
    public Resultado<ArrayList<Agendamento>> consultasAguardandoConfirmação() {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(CONSULTASAGUARDANDO);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Agendamento> lista = new ArrayList<>();
            while(rs.next()){
                int idAgendamento = rs.getInt("id");
                int idPaciente = rs.getInt("id_paciente");
                int idMedico = rs.getInt("id_medico");
                java.sql.Date sqlDate = rs.getDate("data");
                LocalDate data = sqlDate.toLocalDate();
                String hora = rs.getString("hora");
                String status = rs.getString("status");

                Agendamento agendamento = new Agendamento(idAgendamento, idPaciente, idMedico, data, hora, status);
                lista.add(agendamento);
            }

            return Resultado.sucesso("Consultas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado alterarStatusConsulta(int id, String novoStatus) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(UPDATE_STATUS);

            pstm.setString(1, novoStatus);
            pstm.setInt(2, id);

            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated > 0) {
                return Resultado.sucesso("Status atualizado com sucesso.", null);
            } else {
                return Resultado.erro("Nenhum agendamento encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            return Resultado.erro("Erro ao atualizar o status do agendamento: " + e.getMessage());
        }
    }

        
}
