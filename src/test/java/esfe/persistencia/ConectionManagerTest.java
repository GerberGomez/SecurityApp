package esfe.persistencia;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConectionManagerTest {

    ConnectionManager conectionManager;
    @BeforeEach
    void setUp() throws SQLException {
        conectionManager = ConnectionManager.getInstance();
    }

    @AfterEach
    void tearDown() throws SQLException{
        if(conectionManager != null){
            conectionManager.disconnect();
            conectionManager = null;
        }
    }

    @Test
    void connect() throws SQLException {
        Connection conn = conectionManager.connect();
        assertNotNull(conn, "La conexion no debe ser nula");
        assertFalse(conn.isClosed(), "La conexion debe estar abierta");
        if(conn != null){
            conn.close();
        }
    }
}