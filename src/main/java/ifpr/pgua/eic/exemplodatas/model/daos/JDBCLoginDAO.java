package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;

public class JDBCLoginDAO implements LoginDAO{
    private static final String VALIDARSQL = "SELECT * FROM loginPaciente WHERE cpf = (?)";

    private FabricaConexoes fabrica;

    public JDBCLoginDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado validar(String cpf, String senha) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(VALIDARSQL);

            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();

            String cpfLogin = null;
            String senhaLogin = null;
            while(rs.next()){
                cpfLogin = rs.getString("cpf");
                senhaLogin = rs.getString("senha");
            }

            if(senha.equals(senhaLogin) && cpf.equals(cpfLogin)){
                return Resultado.sucesso("Acesso", senha);
            }
            return Resultado.erro("Senha Incorreta");

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}