import java.util.ArrayList;
import java.util.List;

public class Team {         //don't use constructor since we're filling elements one by one
    public int code;
    public String name;
    public List<Player> players = new ArrayList<>();

    public String toString() {
        return name;
    }
}
