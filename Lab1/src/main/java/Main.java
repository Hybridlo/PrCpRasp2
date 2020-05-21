import java.util.Random;

public class Main {
    public static void main (String[] args) throws Exception {
        Football football = new Football();
        String filename = "football.xml";
        football.loadFromFile(filename);

        System.out.println("Scenarios:\n1 - add new team (always the same)\n" +
                "2 - add new player to random team (always the same)\n" +
                "3 - remove random team\n4 - remove random player" +
                "5 - change random team name(always the same, different from (1))\n" +
                "6 - get team by id\n7 - get player by id\n8 - get all teams\n" +
                "9 - get all players of team with id");

        Thread.sleep(10000);

        scenario1(football);
        scenario2(football);
        scenario3(football);
        scenario4(football);
        scenario5(football);
        scenario6(football);
        scenario7(football);
        scenario8(football);
        scenario9(football);

        football.saveToFile("football2.xml");
    }

    static void scenario1(Football football) throws Exception {
        System.out.println("\n\nScenario 1:");

        int code = football.getTeamMaxId() + 1;
        String name = "HA";

        football.addTeam(code, name);

        System.out.println("Team with name \"HA\" and id " + code + " added");

        Thread.sleep(3000);
    }

    static void scenario2(Football football) throws Exception {
        System.out.println("\n\nScenario 2:");

        String name = "Lol";
        int code = football.getPlayerMaxId() + 1;
        boolean isCaptain = true;
        int salary = 10000;
        Random random = new Random();
        int teamCode = football.getTeamInd(random.nextInt(football.countTeams())).code;

        football.addPlayer(code, name, isCaptain, salary, teamCode);

        System.out.println("Player with name \"Lol\" and id " + code + " added to team with id " + teamCode);

        Thread.sleep(3000);
    }

    static void scenario3(Football football) throws Exception {
        System.out.println("\n\nScenario 3:");

        Random random = new Random();
        Team team = football.getTeamInd(random.nextInt(football.countTeams()));

        System.out.println("Team with name " + team.name + " id " + team.code + " removed");

        football.deleteTeam(team.code);

        Thread.sleep(3000);
    }

    static void scenario4(Football football) throws Exception {
        System.out.println("\n\nScenario 4:");

        Random random = new Random();
        Player player = football.getPlayerInd(random.nextInt(football.countPlayers()));

        System.out.println("Player with name " + player.name + " id " + player.code + " removed");

        football.deletePlayer(player.code);

        Thread.sleep(3000);
    }

    static void scenario5(Football football) throws Exception {
        System.out.println("\n\nScenario 5:");

        Random random = new Random();
        Team team = football.getTeamInd(random.nextInt(football.countTeams()));
        String newName = "Oof";

        System.out.println("Team with name " + team.name + " id " + team.code + " changed name to " + newName);

        team.name = newName;

        Thread.sleep(3000);
    }

    static void scenario6(Football football) throws Exception {
        System.out.println("\n\nScenario 6:");

        Random random = new Random();
        int id = football.getTeamInd(random.nextInt(football.countTeams())).code;

        System.out.println("Getting team with id " + id);

        Team team = football.getTeam(id);

        System.out.println(team.toString());

        Thread.sleep(3000);
    }

    static void scenario7(Football football) throws Exception {
        System.out.println("\n\nScenario 7:");

        Random random = new Random();
        int id = football.getPlayerInd(random.nextInt(football.countPlayers())).code;

        System.out.println("Getting player with id " + id);

        Player player = football.getPlayer(id);

        System.out.println(player.toString());

        Thread.sleep(3000);
    }

    static void scenario8(Football football) throws Exception {
        System.out.println("\n\nScenario 8:");

        System.out.println("Getting all teams:");

        for (int i = 0; i < football.countTeams(); i++) {
            System.out.println(football.getTeamInd(i).toString());
        }

        Thread.sleep(3000);
    }

    static void scenario9(Football football) throws Exception {
        System.out.println("\n\nScenario 9:");

        Random random = new Random();
        int id = football.getTeamInd(random.nextInt(football.countTeams())).code;

        System.out.println("Getting players of team with id " + id);
        Team team = football.getTeam(id);

        for (int i = 0; i < team.players.size(); i++) {
            System.out.println(team.players.get(i).toString());
        }

        Thread.sleep(3000);
    }
}
