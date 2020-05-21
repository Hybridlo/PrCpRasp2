import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class FootballParser extends DefaultHandler {
    List<Team> teams = new ArrayList<>();
    List<Player> allPlayers = new ArrayList<>();
    Team currTeam;
    Player currPlayer;
    int playerMaxId = 1;
    int teamMaxId = 1;

    List<Team> getTeams() {
        return teams;
    }

    List<Player> getAllPlayers() {
        return allPlayers;
    }

    int getPlayerMaxId() {
        return playerMaxId;
    }

    int getTeamMaxId() {
        return teamMaxId;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) {
        switch (qName) {
            case "team":
                currTeam = new Team();
                break;
            case "player":
                currPlayer = new Player();
                break;
        }

        int attributeLength = attrs.getLength();

        for (int i = 0; i < attributeLength; i++) {
            String attrName = attrs.getQName(i);
            String attrVal = attrs.getValue(i);

            switch (qName) {
                case "team":
                    switch (attrName) {
                        case "id":
                            currTeam.code = Integer.parseInt(attrVal);
                            if (currTeam.code > teamMaxId)
                                teamMaxId = currTeam.code;
                            break;
                        case "name":
                            currTeam.name = attrVal;
                            break;
                    }
                    break;

                case "player":
                    switch (attrName) {
                        case "id":
                            currPlayer.code = Integer.parseInt(attrVal);
                            if (currPlayer.code > playerMaxId)
                                playerMaxId = currPlayer.code;
                            break;
                        case "name":
                            currPlayer.name = attrVal;
                            break;
                        case "isCaptain":
                            currPlayer.isCaptain = attrVal.equals("1");
                            break;
                        case "salary":
                            currPlayer.salary = Integer.parseInt(attrVal);
                            break;
                    }
                    break;
            }
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) {
        switch (qName) {
            case "team":
                teams.add(currTeam);
                break;
            case "player":
                currTeam.players.add(currPlayer);
                allPlayers.add(currPlayer);
                break;
        }
    }
}
