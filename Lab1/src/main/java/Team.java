import java.util.ArrayList;
import java.util.List;

public class Team {         //don't use constructor since we're filling elements one by one
    public int code;
    public String name;
    public List<Player> players = new ArrayList<>();

    String getXml() {
        StringBuilder result = new StringBuilder("\t<team id=\"" + code + "\" name=\"" + name + "\">\n");

        for (Player player : players) {
            result.append(player.getXml());
        }

        result.append("\t</team>\n");

        return result.toString();
    }

    public String toString() {
        StringBuilder result = new StringBuilder(name + ":\n");

        for (Player player : players) {
            result.append(player.toString());
        }

        return result.toString();
    }
}
