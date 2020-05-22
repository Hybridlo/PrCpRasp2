import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main (String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC";
        Connection con =
                DriverManager.getConnection(url, "root", "5502");

        Football football = new Football(con);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("Football", football);
        System.out.println("Server started!");

        //football.stop();
    }
}
