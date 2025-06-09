package esfe.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionManager {
    private static final String STR_CONECTION = "jdbc:sqlserver://GERBERGOMEZ:1433;" +
            "encrypt=true;" +
            "database=SecurityDB2025;" +
            "trustServerCertificate=true;" +
            "user=sa;" +
            "password=12345;";

    private Connection connection;

    private static ConectionManager instance;

    private ConectionManager(){
        this.connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }catch (ClassNotFoundException e){
            throw new RuntimeException("Error al cargar el driver JDBC de SQL Server", e);
        }
    }

    public synchronized Connection connect() throws SQLException{
        if(this.connection == null || this.connection.isClosed()){
            try {
                this.connection = DriverManager.getConnection(STR_CONECTION);
            }catch (SQLException exception){
                throw new SQLException("Error al conectar a la base de datos " + exception.getMessage(), exception);
            }
        }
        return this.connection;
    }

    public void disconnect() throws SQLException{
        if(this.connection != null){
            try{
                this.connection.close();
            }catch (SQLException exception){
                throw new SQLException("Error al cerrar la conexion" + exception.getMessage(), exception);
            } finally {
                this.connection = null;
            }
        }
    }

    public static synchronized ConectionManager getInstance(){
        if(instance == null){
            instance = new ConectionManager();
        }
        return instance;
    }
}
