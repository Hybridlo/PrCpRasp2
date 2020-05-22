import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Football {
    private Connection con;
    private Statement stmt;

    Football(Connection con) throws SQLException {
        this.con = con;
        this.stmt = con.createStatement();
    }

    public void stop() throws SQLException {
        con.close();
    }

    public List<Team> getTeams(PrintWriter out) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT ID_T, NAME FROM TEAMS";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                int id = rs.getInt("ID_T");
                String name = rs.getString("NAME");
                Team team = new Team();
                team.code = id;
                team.name = name;
                teams.add(team);
            }
            rs.close();

            for (Team team : teams) {
                team.players = getTeamPlayers(team.code);
            }
        } catch (SQLException e) {
            out.println(e.getMessage());
        }
        return teams;
    }

    public List<Player> getTeamPlayers(int teamCode) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM PLAYERS WHERE ID_T = "+teamCode;
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                int id = rs.getInt("ID_P");
                String name = rs.getString("NAME");
                boolean isCaptain = rs.getInt("ISCAPTAIN") == 1;
                int salary = rs.getInt("SALARY");
                Player player = new Player();
                player.code = id;
                player.name = name;
                player.isCaptain = isCaptain;
                player.salary = salary;
                players.add(player);
            }
            rs.close();
        } catch (SQLException ignore) { }
        return players;
    }
}
