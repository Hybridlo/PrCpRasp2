import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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

    public boolean addTeam(int code, String name) {
        String sql = "INSERT INTO TEAMS (ID_T, NAME)" +
                "VALUES ("+code+", '"+name+"')";
        try
        {
            stmt.executeUpdate(sql);
            System.out.println("Team "+name+
                    " added");
            return true;
        } catch (SQLException e)
        {
            System.out.println("Error: team  "+name+
                    " was not added");
            System.out.println(" >> "+e.getMessage());
            return false;
        }

    }

    public boolean updateTeam(int code, String name) {
        String sql = "UPDATE TEAMS " +
                "SET ";
        boolean previous = false;
        if (name != null) {
            sql += "NAME = '"+name+"'";
            previous = true;
        }

        if (!previous) {
            System.out.println("Nothing to update");
            return false;
        }

        sql += " WHERE ID_T = " + code;

        try
        {
            stmt.executeUpdate(sql);
            System.out.println("Team with id "+code+
                    " updated");
            return true;
        } catch (SQLException e)
        {
            System.out.println("Error: team  "+name+
                    " was not updated");
            System.out.println(" >> "+e.getMessage());
            return false;
        }
    }

    public Team getTeam(int code) {
        String sql = "SELECT *" +
                "FROM TEAMS T1" +
                "WHERE T1.ID_T = " + code;

        Team team = new Team();
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                int id = rs.getInt("ID_T");
                team.code = id;
                String name = rs.getString("NAME");
                team.name = name;
                System.out.println(" >> "+ id + " - " + name);
            } else {
                System.out.println("Team with id " + code + " not found");
                return null;
            }

        } catch (SQLException e)
        {
            System.out.println("Error while looking for a team");
            System.out.println(" >> "+e.getMessage());
            return null;
        }

        return team;
    }

    public boolean deleteTeam(int code) {
        String sql1 = "DELETE FROM PLAYERS WHERE ID_T = "+code;

        String sql = "DELETE FROM TEAMS WHERE ID_T = "+code;
        try
        {
            int c1 = stmt.executeUpdate(sql1);  //delete players of the team
            int c = stmt.executeUpdate(sql);
            if (c>0)
            {
                System.out.println("Team with id "
                        + code +" deleted successfully");
                return true;
            }
            else
            {
                System.out.println("Team with id "
                        + code +" was not found");
                return false;
            }
        } catch (SQLException e)
        {
            System.out.println(
                    "Error while deleting team with id "+code);
            System.out.println(" >> "+e.getMessage());
            return false;
        }

    }

    public List<Team> showTeams() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT ID_T, NAME FROM TEAMS";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Teams:");
            while (rs.next())
            {
                int id = rs.getInt("ID_T");
                String name = rs.getString("NAME");

                Team team = new Team();
                team.name = name;
                team.code = id;
                teams.add(team);

                System.out.println(" >> "+ id + " - " + name);
            }
            rs.close();
        } catch (SQLException e)
        {
            System.out.println(
                    "Error while getting teams");
            System.out.println(" >> "+e.getMessage());
            return null;
        }

        return teams;
    }

    public boolean addPlayer(int code, String name, boolean isCaptain, int salary, int teamCode) {
        String sql = "INSERT INTO PLAYERS (ID_P, ID_T, NAME, ISCAPTAIN, SALARY)" +
                "VALUES ("+code+", "+teamCode+", '"+name+"', "+(isCaptain?"1":"0")+", "+salary+")";
        try
        {
            stmt.executeUpdate(sql);
            System.out.println("Player "+name+
                    " added");
            return true;
        } catch (SQLException e)
        {
            System.out.println("Error: team  "+name+
                    " was not added");
            System.out.println(" >> "+e.getMessage());
            return false;
        }
    }

    public boolean updatePlayer(int code, String name, Boolean isCaptain, Integer salary, Integer teamCode) {
        String sql = "UPDATE PLAYERS " +
                "SET ";
        boolean previous = false;
        if (name != null) {
            sql += "NAME = '"+name+"'";
            previous = true;
        }
        if (isCaptain != null) {
            if (previous) sql += ", ";
            sql += "ISCAPTAIN = " + (isCaptain?"1":"0");
            previous = true;
        }
        if (salary != null) {
            if (previous) sql += ", ";
            sql += "SALARY = " + salary;
            previous = true;
        }
        if (teamCode != null) {
            if (previous) sql += ", ";
            sql += "ID_T = " + teamCode;
            previous = true;
        }

        if (!previous) {
            System.out.println("Nothing to update");
            return false;
        }

        sql += " WHERE ID_P = " + code;

        try
        {
            stmt.executeUpdate(sql);
            System.out.println("Player with id "+code+
                    " updated");
            return true;
        } catch (SQLException e)
        {
            System.out.println("Error: player  "+name+
                    " was not updated");
            System.out.println(" >> "+e.getMessage());
            return false;
        }
    }

    public Player getPlayer(int code) {
        String sql = "SELECT *" +
                "FROM PLAYERS T1" +
                "WHERE T1.ID_P = " + code;

        Player player = new Player();
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                int id = rs.getInt("ID_P");
                player.code = id;

                int idT = rs.getInt("ID_T");
                player.teamCode = idT;

                String name = rs.getString("NAME");
                player.name = name;

                boolean isCaptain = (rs.getString("ISCAPTAIN").equals("1"));
                player.isCaptain = isCaptain;

                int salary = rs.getInt("SALARY");
                player.salary = salary;

                System.out.println(" >> "+ id + " - " + idT + " - " + name + " - " + (isCaptain?"1":"0") +
                        " - " + salary);
            } else {
                System.out.println("Player with id " + code + " not found");
                return null;
            }

        } catch (SQLException e)
        {
            System.out.println("Error while looking for a player");
            System.out.println(" >> "+e.getMessage());
            return null;
        }

        return player;
    }

    public boolean deletePlayer(int code) {
        String sql = "DELETE FROM PLAYERS WHERE ID_P = "+code;
        try
        {
            int c = stmt.executeUpdate(sql);
            if (c>0)
            {
                System.out.println("Player with id "
                        + code +" deleted successfully");
                return true;
            }
            else
            {
                System.out.println("Player with id "
                        + code +" was not found");
                return false;
            }
        } catch (SQLException e)
        {
            System.out.println(
                    "Error while deleting player with id "+code);
            System.out.println(" >> "+e.getMessage());
            return false;
        }
    }



    public List<Player> showPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT ID_P, ID_T, NAME, ISCAPTAIN, SALARY FROM PLAYERS";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Players:");
            while (rs.next())
            {
                int id = rs.getInt("ID_P");
                int idT = rs.getInt("ID_T");
                String name = rs.getString("NAME");
                boolean isCaptain = (rs.getString("ISCAPTAIN").equals("1"));
                int salary = rs.getInt("SALARY");

                Player player = new Player();
                player.code = id;
                player.teamCode = idT;
                player.name = name;
                player.isCaptain = isCaptain;
                player.salary = salary;
                players.add(player);

                System.out.println(" >> "+ id + " - " + idT + " - " + name + " - " + (isCaptain?"1":"0") +
                        " - " + salary);
            }
            rs.close();
        } catch (SQLException e)
        {
            System.out.println(
                    "Error while getting players");
            System.out.println(" >> "+e.getMessage());
            return null;
        }

        return players;
    }
}
