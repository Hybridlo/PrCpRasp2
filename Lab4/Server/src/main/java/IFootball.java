import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface IFootball extends Remote {
    void stop() throws SQLException, RemoteException;

    String addTeam(int code, String name) throws RemoteException;

    String updateTeam(int code, String name) throws RemoteException;

    Team getTeam(int code) throws RemoteException;

    String deleteTeam(int code) throws RemoteException;

    List<Team> showTeams() throws RemoteException;

    String addPlayer(int code, String name, boolean isCaptain, int salary, int teamCode) throws RemoteException;

    String updatePlayer(int code, String name, Boolean isCaptain, Integer salary, Integer teamCode) throws RemoteException;

    Player getPlayer(int code) throws RemoteException;

    String deletePlayer(int code) throws RemoteException;

    List<Player> showPlayers() throws RemoteException;
}
