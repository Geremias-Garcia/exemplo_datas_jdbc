package ifpr.pgua.eic.exemplodatas.model.daos;

import com.github.hugoperlin.results.Resultado;

public interface LoginDAO {
    
    Resultado validarLoginPaciente(String cpf,String senha);

    Resultado validarLoginMedico(String cpf,String senha);
}
