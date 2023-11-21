package ifpr.pgua.eic.exemplodatas.model.daos;

import com.github.hugoperlin.results.Resultado;

public interface LoginDAO {

    Resultado criarLoginPaciente(String cpf,String senha);

    Resultado criarLoginMedico(String cpf,String senha);
    
    Resultado validarLoginPaciente(String cpf,String senha);

    Resultado validarLoginMedico(String cpf,String senha);

    Resultado alterarSenha(String cpf, String senha);

    Resultado alterarCpf(String cpfAntigo, String cpfNovo);
}
