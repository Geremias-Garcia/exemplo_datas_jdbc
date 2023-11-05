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

public class JDBCPessoaDAO implements PessoaDAO{

    private static final String INSERTSQL = "INSERT INTO pessoa (nome, cpf, telefone, email, dataNascimento, genero, isAtive)\r\n" + //
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECTSQL = "SELECT * FROM pessoa";
    private static final String SELECTPORID = "SELECT * FROM pessoa WHERE id = ?";
    private static final String FILTRO = "SELECT * FROM pessoa WHERE nome LIKE ? || '%'";
    private static final String BUSCARCPF = "SELECT * FROM pessoa WHERE cpf = (?)";

    private FabricaConexoes fabrica;

    public JDBCPessoaDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar(Pessoa pessoa) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(INSERTSQL, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqlDate = java.sql.Date.valueOf(pessoa.getDataNascimento());
            
            pstm.setString(1, pessoa.getNome());
            pstm.setString(2, pessoa.getCpf());
            pstm.setString(3, pessoa.getTelefone());
            pstm.setString(4, pessoa.getEmail());
            pstm.setDate(5, sqlDate);
            pstm.setString(6, pessoa.getGenero());
            pstm.setBoolean(7, pessoa.isAtive());
            

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int id = DBUtils.getLastId(pstm);

                return Resultado.sucesso("Pessoa cadastrada", pessoa);
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

            ArrayList<Pessoa> lista = new ArrayList<>();
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

                Pessoa pessoa = new Pessoa(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);

                lista.add(pessoa);
            }

            return Resultado.sucesso("Categorias listadas", lista);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }

    }

    @Override
    public Resultado<Pessoa> buscarPorId(int id) {
        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(SELECTPORID);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            Pessoa pessoa = null;
            while(rs.next()){
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");
                

                pessoa = new Pessoa(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);
            }

            return Resultado.sucesso("Contatos", pessoa);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }


    @Override
    public Resultado<ArrayList<Pessoa>> filtrarNome(String inicio) {

        try (Connection con = fabrica.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(FILTRO);

            pstm.setString(1, inicio);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Pessoa> lista = new ArrayList<>();
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

                Pessoa pessoa = new Pessoa(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);

                lista.add(pessoa);
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

            Pessoa pessoa = null;
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                java.sql.Date sqlDate = rs.getDate("dataNascimento");
                LocalDate dataNascimento = sqlDate.toLocalDate();

                String genero = rs.getString("genero");
                boolean isAtive = rs.getBoolean("isAtive");

                pessoa = new Pessoa(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);
            }

            return Resultado.sucesso("Contatos", pessoa);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    @Override
    public Resultado alterar(Pessoa pessoa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

    
}
