package ifpr.pgua.eic.exemplodatas.model.daos;

import com.github.hugoperlin.results.Resultado;

public interface LoginDAO {
    
    Resultado validar(String cpf,String senha);

}
