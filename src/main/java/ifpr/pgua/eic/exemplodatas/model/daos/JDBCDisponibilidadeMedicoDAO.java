package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.DisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCDisponibilidadeMedicoDAO implements DisponibilidadeMedicoDAO {

    private static final String INSERTHORARIOS = "INSERT INTO horarios_indisponiveis(id_medico, dia_semana, periodo) VALUES (?,?,?)";
    private static final String SELECTDIASHORARIOS = "SELECT FROM horarios_indisponiveis WHERE id_medico = ?";

    private FabricaConexoes fabrica;

    public JDBCDisponibilidadeMedicoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado<DisponibilidadeMedico> registrarHorarios(DisponibilidadeMedico horariosIndisponiveis) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTHORARIOS, Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, horariosIndisponiveis.getMedico().getId());
            pstm.setString(2, horariosIndisponiveis.getDiaDaSemana());
            pstm.setString(3, horariosIndisponiveis.getPeriodo());

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Registro atualizado", horariosIndisponiveis);
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<DisponibilidadeMedico>> verificarDiasAtendimento(int id_medico) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(SELECTDIASHORARIOS);

            pstm.setInt(1, id_medico);

            ResultSet rs = pstm.executeQuery();

            ArrayList<DisponibilidadeMedico> lista = new ArrayList<>();
            DisponibilidadeMedico disponibilidadeMedico;
            while (rs.next()) {
                int id = rs.getInt("id");
                String diaDaSemana = rs.getString("dia_semana");
                String periodo = rs.getString("periodo");

                disponibilidadeMedico = new DisponibilidadeMedico(id,id_medico, diaDaSemana, periodo);
                lista.add(disponibilidadeMedico);
            }

            return Resultado.sucesso("Consultas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}
