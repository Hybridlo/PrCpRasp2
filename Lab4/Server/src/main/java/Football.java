import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Football extends UnicastRemoteObject implements IFootball {
    private Connection con;
    private Statement stmt;

    Football(Connection con) throws SQLException, RemoteException {
        super();
        this.con = con;
        this.stmt = con.createStatement();
    }

    public void stop() throws SQLException, RemoteException {
        con.close();
    }

    public String addTeam(int code, String name) throws RemoteException {
        String result;
        String sql = "INSERT INTO TEAMS (ID_T, NAME)" +
                "VALUES ("+code+", '"+name+"')";
        try
        {
            stmt.executeUpdate(sql);
            result = "Team "+name+ " added";
        } catch (SQLException e)
        {
            result = "Error: team  "+name+ " was not added: "+e.getMessage();
        }

        return result;
    }

    public String updateTeam(int code, String name) throws RemoteException {
        String sql = "UPDATE TEAMS " +
                "SET ";

        String result;
        boolean previous = false;
        if (name != null) {
            sql += "NAME = '"+name+"'";
            previous = true;
        }

        if (!previous) {
            result = "Nothing to update";
            return result;
        }

        sql += " WHERE ID_T = " + code;

        try
        {
            stmt.executeUpdate(sql);
            result = "Team with id "+code+ " updated";
        } catch (SQLException e)
        {
            result = "Error: team  "+name+ " was not updated: "+e.getMessage();
        }

        return result;
    }

    public Team getTeam(int code) throws RemoteException {
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

    public String deleteTeam(int code) throws RemoteException {
        String sql1 = "DELETE FROM PLAYERS WHERE ID_T = "+code;

        String sql = "DELETE FROM TEAMS WHERE ID_T = "+code;

        String result;
        try
        {
            int c1 = stmt.executeUpdate(sql1);  //delete players of the team
            int c = stmt.executeUpdate(sql);
            if (c>0)
            {
                result = "Team with id " + code +" deleted successfully";
            }
            else
            {
                result = "Team with id " + code +" was not found";
            }
        } catch (SQLException e)
        {
            result = "Error while deleting team with id "+code+": "+e.getMessage();
        }

        return result;
    }

    public List<Team> showTeams() throws RemoteException {
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

    public String addPlayer(int code, String name, boolean isCaptain, int salary, int teamCode) throws RemoteException {
        String sql = "INSERT INTO PLAYERS (ID_P, ID_T, NAME, ISCAPTAIN, SALARY)" +
                "VALUES ("+code+", "+teamCode+", '"+name+"', "+(isCaptain?"1":"0")+", "+salary+")";
        String result;
        try
        {
            stmt.executeUpdate(sql);
            result = "Player "+name+ " added";
        } catch (SQLException e)
        {
            result = "Error: team  "+name+" was not added: "+e.getMessage();
            System.out.println(" >> "+e.getMessage());
        }

        return result;
    }

    public String updatePlayer(int code, String name, Boolean isCaptain, Integer salary, Integer teamCode) throws RemoteException {
        String sql = "UPDATE PLAYERS " +
                "SET ";

        String result;
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
            result = "Nothing to update";
            return result;
        }

        sql += " WHERE ID_P = " + code;

        try
        {
            stmt.executeUpdate(sql);
            result = "Player with id "+code+" updated";
        } catch (SQLException e)
        {
            result = "Error: player  "+name+" was not updated: "+e.getMessage();
        }

        return result;
    }

    public Player getPlayer(int code) throws RemoteException {
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

    public String deletePlayer(int code) throws RemoteException {
        String sql = "DELETE FROM PLAYERS WHERE ID_P = "+code;

        String result;
        try
        {
            int c = stmt.executeUpdate(sql);
            if (c>0)
            {
                result = "Player with id " + code +" deleted successfully";
            }
            else
            {
                result = "Player with id " + code +" was not found";
            }
        } catch (SQLException e)
        {
            result = "Error while deleting player with id "+code+": "+e.getMessage();
        }

        return result;
    }



    public List<Player> showPlayers() throws RemoteException {
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
