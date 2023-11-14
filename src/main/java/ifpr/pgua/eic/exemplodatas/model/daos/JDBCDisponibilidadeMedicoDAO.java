package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.DisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCDisponibilidadeMedicoDAO implements DisponibilidadeMedicoDAO {

    private static final String INSERTHORARIOS = "INSERT INTO horarios_indisponiveis(id_medico, dia_semana, periodo) VALUES (?,?,?)";

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
    public Resultado<ArrayList<DisponibilidadeMedico>> verificarDiasAtendimento(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verificarDiasAtendimento'");
    }
    
}
