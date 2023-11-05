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
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCMedicoDAO implements MedicoDAO {

    private static final String INSERTSQL = "INSERT INTO medico (nome, cpf, telefone, email, dataNascimento, genero, isAtive, salario, especialidade, crm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String SELECTSQL = "SELECT * FROM medico";

    private static final String SELECTPORID = "SELECT * FROM medico WHERE id = ?";

    private static final String FILTRO = "SELECT * FROM medico WHERE nome LIKE ? || '%'";

    private static final String FILTROESPECIALIDADE = "SELECT * FROM medico WHERE especialidade LIKE ? || '%'";

    private static final String FILTROESPECIALIDADENOME = "SELECT * FROM medico WHERE especialidade = ? AND nome LIKE ? || '%'";

    private static final String BUSCARCPF = "SELECT * FROM medico WHERE cpf = (?)";




    private FabricaConexoes fabrica;

    public JDBCMedicoDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Medico medico) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);
            System.out.println("jdbc");

            java.sql.Date sqlDate = java.sql.Date.valueOf(medico.getDataNascimento());
            
            pstm.setString(1, medico.getNome());
            pstm.setString(2, medico.getCpf());
            pstm.setString(3, medico.getTelefone());
            pstm.setString(4, medico.getEmail());
            pstm.setDate(5, sqlDate);
            pstm.setString(6, medico.getGenero());
            pstm.setBoolean(7, medico.isAtive());
            pstm.setDouble(8, medico.getSalario());
            pstm.setString(9, medico.getEspecialidade());
            pstm.setString(10, medico.getCrm());
            

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Médico cadastrado", medico);
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

            ArrayList<Medico> lista = new ArrayList<>();
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
                double salario = rs.getDouble("salario");
                String especialidade = rs.getString("especialidade");
                String crm = rs.getString("crm");

                Medico medico = new Medico(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive,salario,especialidade,crm);

                lista.add(medico);
            }

            return Resultado.sucesso("Categorias listadas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<Medico> buscarPorId(int id) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(SELECTPORID);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            Medico medico = null;
            while(rs.next()){
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");
                double salario = rs.getDouble("salario");
                String especialidade = rs.getString("especialidade");
                String crm = rs.getString("crm");

                medico = new Medico(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive,salario,especialidade,crm);
            }

            return Resultado.sucesso("Contatos", medico);

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

            Medico medico = null;
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");
                double salario = rs.getDouble("salario");
                String especialidade = rs.getString("especialidade");
                String crm = rs.getString("crm");

                medico = new Medico(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive,salario,especialidade,crm);
            }

            return Resultado.sucesso("Contatos", medico);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Medico>> filtrarNome(String inicio) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(FILTRO);

            pstm.setString(1, inicio);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Medico> lista = new ArrayList<>();
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
                double salario = rs.getDouble("salario");
                String especialidade = rs.getString("especialidade");
                String crm = rs.getString("crm");

                Medico medico = new Medico(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive,salario,especialidade,crm);

                lista.add(medico);
            }

            return Resultado.sucesso("Contatos", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Medico>> filtrarEspecialidade(String especialidade) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(FILTROESPECIALIDADE);

            pstm.setString(1, especialidade);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Medico> lista = new ArrayList<>();
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
                double salario = rs.getDouble("salario");
                String crm = rs.getString("crm");

                Medico medico = new Medico(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive,salario,especialidade,crm);

                lista.add(medico);
            }

            return Resultado.sucesso("Contatos", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    @Override
    public Resultado<ArrayList<Medico>> filtrarEspecialidadeENome(String especialidade, String inicio) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(FILTROESPECIALIDADENOME);
    
            pstm.setString(1, especialidade);  
            pstm.setString(2, inicio);      
    
            ResultSet rs = pstm.executeQuery();

            ArrayList<Medico> lista = new ArrayList<>();
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
                double salario = rs.getDouble("salario");
                String crm = rs.getString("crm");

                Medico medico = new Medico(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive,salario,especialidade,crm);

                lista.add(medico);
            }

            return Resultado.sucesso("Contatos", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    
    @Override
    public Resultado alterar(Medico medico) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

}
