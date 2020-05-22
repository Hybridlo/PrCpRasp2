import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main (String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC";
        Connection con =
                DriverManager.getConnection(url, "root", "5502");

        Football football = new Football(con);

        Server server = new Server(football);
        server.start(12345);

        football.stop();
    }
}
