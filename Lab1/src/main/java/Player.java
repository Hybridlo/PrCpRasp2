public class Player {
    public int code;
    public String name;
    public boolean isCaptain;
    public int salary;

    String getXml() {
        return "\t\t<player id=\"" + code + "\" name=\"" + name + "\" isCaptain=\""
                + ((isCaptain) ? "1" : "0") + "\" salary=\"" + salary + "\"/>\n";
    }

    public String toString() {
        return "\t" + name + "\n";
    }
}
