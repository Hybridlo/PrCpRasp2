import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main (String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC";
        Connection con =
                DriverManager.getConnection(url, "root", "5502");

        Football football = new Football(con);

        scenario1(football);
        scenario2(football);

        football.stop();
    }

    static void scenario1(Football football) throws Exception {
        football.showTeams();
        Thread.sleep(3000);

        football.addTeam(1, "SHACHTAR");
        Thread.sleep(3000);

        football.addTeam(5, "SPARTAK");
        Thread.sleep(3000);

        football.addTeam(6, "MADRID");
        Thread.sleep(3000);

        football.deleteTeam(2);
        Thread.sleep(3000);

        football.deleteTeam(7);
        Thread.sleep(3000);

        football.showTeams();
        Thread.sleep(3000);
    }

    static void scenario2(Football football) throws Exception {
        football.showPlayers();
        Thread.sleep(3000);

        football.addPlayer(1,  "EVGENIY", true, 400000, 6);
        Thread.sleep(3000);

        football.addPlayer(6,  "KERJAKOV", true, 1000000, 5);
        Thread.sleep(3000);

        football.addPlayer(7, "LUCAS", false, 3000000, 6);
        Thread.sleep(3000);

        football.deletePlayer(2);
        Thread.sleep(3000);

        football.deletePlayer(8);
        Thread.sleep(3000);

        football.showPlayers();
        Thread.sleep(3000);
    }
}
