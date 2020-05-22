import java.io.Serializable;

public class Team implements Serializable {         //don't use constructor since we're filling elements one by one
    public int code;
    public String name;

    public String toString() {
        return name;
    }
}
