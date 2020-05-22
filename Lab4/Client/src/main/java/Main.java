import java.rmi.Naming;
import java.util.List;

public class Main {
    public static void main (String[] args) throws Exception {
        String url = "//localhost:1099/Football";
        IFootball football = (IFootball) Naming.lookup(url);
        System.out.println("RMI object found");
        System.out.println("Connected!");

        scenario1(football);
        scenario2(football);
    }

    static void scenario1(IFootball football) throws Exception {
        List<Team> teams = football.showTeams();
        for (Team team : teams)
            System.out.println(team.toString());
        Thread.sleep(3000);

        System.out.println(football.addTeam(1, "SHACHTAR"));
        Thread.sleep(3000);

        System.out.println(football.addTeam(5, "SPARTAK"));
        Thread.sleep(3000);

        System.out.println(football.addTeam(6, "MADRID"));
        Thread.sleep(3000);

        System.out.println(football.deleteTeam(2));
        Thread.sleep(3000);

        System.out.println(football.deleteTeam(7));
        Thread.sleep(3000);

        System.out.println(football.updateTeam(6, "INSANE"));
        Thread.sleep(3000);

        System.out.println(football.updateTeam(1, null));
        Thread.sleep(3000);

        teams = football.showTeams();
        for (Team team : teams)
            System.out.println(team.toString());
        Thread.sleep(3000);
    }

    static void scenario2(IFootball football) throws Exception {
        List<Player> players = football.showPlayers();
        for (Player player : players)
            System.out.println(player.toString());
        Thread.sleep(3000);

        System.out.println(football.addPlayer(1,  "EVGENIY", true, 400000, 6));
        Thread.sleep(3000);

        System.out.println(football.addPlayer(6,  "KERJAKOV", true, 1000000, 5));
        Thread.sleep(3000);

        System.out.println(football.addPlayer(7, "LUCAS", false, 3000000, 6));
        Thread.sleep(3000);

        System.out.println(football.deletePlayer(2));
        Thread.sleep(3000);

        System.out.println(football.deletePlayer(8));
        Thread.sleep(3000);

        System.out.println(football.updatePlayer(3, "KOLYA", null, 500000, null));
        Thread.sleep(3000);

        System.out.println(football.updatePlayer(1, null, null, null, null));
        Thread.sleep(3000);

        players = football.showPlayers();
        for (Player player : players)
            System.out.println(player.toString());
        Thread.sleep(3000);
    }
}
