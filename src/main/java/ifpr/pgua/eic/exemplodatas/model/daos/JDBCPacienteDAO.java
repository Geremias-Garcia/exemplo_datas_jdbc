package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCPacienteDAO implements PacienteDAO{

    private static final String INSERTSQL = "INSERT INTO paciente (nome, cpf, telefone, email, dataNascimento, genero, isAtive)\r\n" + //
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECTSQL = "SELECT * FROM paciente";
    private static final String SELECTPORID = "SELECT * FROM paciente WHERE id = ?";
    private static final String FILTRO = "SELECT * FROM paciente WHERE nome LIKE ? || '%'";
    private static final String BUSCARCPF = "SELECT * FROM paciente WHERE cpf = (?)";

    private FabricaConexoes fabrica;

    public JDBCPacienteDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Paciente paciente) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqlDate = java.sql.Date.valueOf(paciente.getDataNascimento());
            
            pstm.setString(1, paciente.getNome());
            pstm.setString(2, paciente.getCpf());
            pstm.setString(3, paciente.getTelefone());
            pstm.setString(4, paciente.getEmail());
            pstm.setDate(5, sqlDate);
            pstm.setString(6, paciente.getGenero());
            pstm.setBoolean(7, paciente.isAtive());
            

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Paciente cadastrada", paciente);
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {

        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(SELECTSQL);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Paciente> lista = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");

                Paciente paciente = new Paciente(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);

                lista.add(paciente);
            }

            return Resultado.sucesso("Categorias listadas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

    @Override
    public Resultado<Paciente> buscarPorId(int id) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(SELECTPORID);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            Paciente paciente = null;
            while(rs.next()){
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");
                

                paciente = new Paciente(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);
            }

            return Resultado.sucesso("Contatos", paciente);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }


    @Override
    public Resultado<ArrayList<Paciente>> filtrarNome(String inicio) {

        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(FILTRO);

            pstm.setString(1, inicio);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Paciente> lista = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");

                Paciente paciente = new Paciente(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);

                lista.add(paciente);
            }

            return Resultado.sucesso("Contatos", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

    @Override
    public Resultado buscarPorCpf(String cpf) {

        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(BUSCARCPF);

            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();

            Paciente paciente = null;
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");

                paciente = new Paciente(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);
            }

            return Resultado.sucesso("Contatos", paciente);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    @Override
    public Resultado alterar(Paciente paciente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

    
}
