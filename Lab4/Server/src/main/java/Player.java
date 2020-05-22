import java.io.Serializable;

public class Player implements Serializable {
    public int code;
    public int teamCode;
    public String name;
    public boolean isCaptain;
    public int salary;

    public String toString() {
        return name;
    }
}
