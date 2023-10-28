package ifpr.pgua.eic.exemplodatas.model.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Exemplo;
import ifpr.pgua.eic.exemplodatas.utils.DBUtils;

public class JDBCExemploDAO implements ExemploDAO{
    private FabricaConexoes fabrica;

    


    public JDBCExemploDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado criar() {
        
        try(Connection con=fabrica.getConnection()){
            char[] letters = new char[3];
            int[] numbers = new int[4];
            String[] carModels = {
                "FUSCA", "CORSA", "KWID", "KOMBI", "FOCUS",
                "FIESTA", "GOL", "MONZA", "PAMPA", "F1000"
            };

            Random random = new Random();

            for (int j = 0; j < 100000; j++) {
                for (int i = 0; i < 3; i++) {
                    letters[i] = (char) ('A' + random.nextInt(26));
                }

                for (int i = 0; i < 4; i++) {
                    numbers[i] = random.nextInt(10);
                }

                String sql = "INSERT INTO carro_modelo (placa, modelo) VALUES (?, ?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, new String(letters) + numbers[0] + numbers[1] + numbers[2] + numbers[3]);
                statement.setString(2, carModels[numbers[0]]);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Registro inserido com sucesso.");
                } else {
                    System.out.println("Erro na inserção.");
                }
            }

            return Resultado.erro("Vixe...");

            
        }catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }

    
    }
    
}
