public class Main {
    public static void main (String[] args) throws Exception {
        FootballClient client = new FootballClient("localhost", 12345);
        System.out.println("Connected!");

        scenario1(client);
        scenario2(client);
    }

    static void scenario1(FootballClient football) throws Exception {
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

        football.updateTeam(6, "INSANE");
        Thread.sleep(3000);

        football.updateTeam(1, null);
        Thread.sleep(3000);

        football.showTeams();
        Thread.sleep(3000);
    }

    static void scenario2(FootballClient football) throws Exception {
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

        football.updatePlayer(3, "KOLYA", null, 500000, null);
        Thread.sleep(3000);

        football.updatePlayer(1, null, null, null, null);
        Thread.sleep(3000);

        football.showPlayers();
        Thread.sleep(3000);
    }
}
