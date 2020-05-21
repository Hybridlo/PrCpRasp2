import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Football {
    private List<Team> teams;
    private List<Player> players;
    private int playerMaxId;
    private int teamMaxId;

    public void saveToFile(String filename) {
        StringBuilder result = new StringBuilder("<?xml version=\"1.0\" encoding=\"WINDOWS-1251\"?>\n\n<football>\n");

        for (Team team : teams) {
            result.append(team.getXml());
        }

        result.append("</football>");

        try (FileWriter fr = new FileWriter(filename)) {
            fr.write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        FootballParser fp = new FootballParser();

        try {
            SchemaFactory scFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = scFactory.newSchema(new File("football.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(filename)));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Validation success");

        parser.parse(new File(filename), fp);

        teams = fp.getTeams();
        players = fp.getAllPlayers();
        playerMaxId = fp.getPlayerMaxId();
        teamMaxId = fp.getTeamMaxId();
    }

    public void addTeam(int code, String name) throws Exception {
        for (Team team : teams) {
            if (team.code == code)
                throw new Exception();
        }

        Team team = new Team();
        team.code = code;
        team.name = name;
        teams.add(team);
        if (code > teamMaxId)
            teamMaxId = code;
    }

    public Team getTeam(int code) throws Exception {
        for (Team team : teams) {
            if (team.code == code)
                return team;
        }

        throw new Exception();
    }

    public Team getTeamInd(int index) throws Exception {
        if (index >= teams.size())
            throw new Exception();

        return teams.get(index);
    }

    public int countTeams() {
        return teams.size();
    }

    public void deleteTeam(int code) throws Exception {
        for (Team team : teams) {
            if (team.code == code) {
                for (Player player : team.players) {
                    players.remove(player);
                }
                teams.remove(team);
                return;
            }
        }

        throw new Exception();
    }

    int getTeamMaxId() {
        return teamMaxId;
    }

    public void addPlayer(int code, String name, boolean isCaptain, int salary, int teamCode) throws Exception {
        for (Player player : players) {
            if (player.code == code)
                throw new Exception();
        }

        Team team = getTeam(teamCode);      //will raise exception itself

        Player player = new Player();
        player.code = code;
        player.name = name;
        player.isCaptain = isCaptain;
        player.salary = salary;

        team.players.add(player);
        players.add(player);
        if (code > playerMaxId)
            playerMaxId = code;
    }


    public Player getPlayer(int code) throws Exception {
        for (Player player : players) {
            if (player.code == code)
                return player;
        }

        throw new Exception();
    }

    public Player getPlayerInd(int index) throws Exception {
        if (index >= players.size())
            throw new Exception();

        return players.get(index);
    }

    public int countPlayers() {
        return players.size();
    }

    public void deletePlayer(int code) throws Exception {
        for (Player player : players) {
            if (player.code == code) {

                for (Team team : teams) {
                    if (team.players.indexOf(player) > -1) {
                        team.players.remove(player);
                        break;
                    }
                }
                players.remove(player);

                return;
            }
        }

        throw new Exception();
    }

    int getPlayerMaxId() {
        return playerMaxId;
    }
}
