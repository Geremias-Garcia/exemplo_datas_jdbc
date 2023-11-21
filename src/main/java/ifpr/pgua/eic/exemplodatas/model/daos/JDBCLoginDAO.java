package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCLoginDAO implements LoginDAO{
    private static final String CRIARLOGIN = "INSERT INTO loginPaciente(cpf,senha)  VALUES (?,?)";
    private static final String CRIARLOGINMEDICO = "INSERT INTO loginMedico(cpf,senha)  VALUES (?,?)";
    private static final String VALIDARPACIENTE = "SELECT * FROM loginPaciente WHERE cpf = (?)";
    private static final String VALIDARMEDICO = "SELECT * FROM loginMedico WHERE cpf = (?)";
    private static final String ALTERARSENHA = "UPDATE loginPaciente SET senha = ? WHERE cpf = ?";
    private static final String ALTERARCPF = "UPDATE loginPaciente SET cpf = ? WHERE cpf = ?";

    private FabricaConexoes fabrica;

    public JDBCLoginDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criarLoginPaciente(String cpf, String senha) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(CRIARLOGIN, Statement.RETURN_GENERATED_KEYS);
            
            pstm.setString(1, cpf);
            pstm.setString(2, senha);

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Volte a tela anterior para realizar o login", id);
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado criarLoginMedico(String cpf, String senha) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(CRIARLOGINMEDICO, Statement.RETURN_GENERATED_KEYS);
            
            pstm.setString(1, cpf);
            pstm.setString(2, senha);

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Volte a tela anterior para realizar o login", id);
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado validarLoginPaciente(String cpf, String senha) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(VALIDARPACIENTE);

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

    @Override
    public Resultado validarLoginMedico(String cpf, String senha) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(VALIDARMEDICO);

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

    @Override
    public Resultado alterarSenha(String cpf, String senha) {
        try (Connection con = fabrica.getConnection()) {
                PreparedStatement pstm = con.prepareStatement(ALTERARSENHA, Statement.RETURN_GENERATED_KEYS);
                
                pstm.setString(1, senha);
                pstm.setString(2, cpf);

                int ret = pstm.executeUpdate();

                if(ret == 1){
                    int id = DBUtils.getLastId(pstm);

                    return Resultado.sucesso("Senha alterada", id);
                }
                return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
                return Resultado.erro(e.getMessage());
        }    
    }

    @Override
    public Resultado alterarCpf(String cpfAntigo, String cpfNovo) {
        try (Connection con = fabrica.getConnection()) {
                PreparedStatement pstm = con.prepareStatement(ALTERARCPF, Statement.RETURN_GENERATED_KEYS);
                
                pstm.setString(1, cpfNovo);
                pstm.setString(2, cpfAntigo);

                int ret = pstm.executeUpdate();

                if(ret == 1){
                    int id = DBUtils.getLastId(pstm);

                    return Resultado.sucesso("Seu novo CPF foi alterado para o login", id);
                }
                return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
                return Resultado.erro(e.getMessage());
        }
    }
}
