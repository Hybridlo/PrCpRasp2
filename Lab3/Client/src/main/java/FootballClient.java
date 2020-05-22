import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FootballClient {
    private Socket sock = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;


    public FootballClient(String ip, int port) throws IOException {
        sock = new Socket(ip, port);

        in = new DataInputStream(sock.getInputStream());
        out = new DataOutputStream(sock.getOutputStream());
    }

    public void addTeam(int code, String name) {
        try {
            int entity = 0;
            int oper = 0;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);
            out.writeUTF(name);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");
        } catch (IOException ignore) {}
    }

    public void updateTeam(int code, String name) {
        try {
            int entity = 0;
            int oper = 1;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);

            if (name != null) {
                out.writeInt(1);      //if null send 0, else 1
                out.writeUTF(name);
            } else {
                out.writeInt(0);
            }

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");
        } catch (IOException ignore) {}
    }

    public Team getTeam(int code) {
        Team team = null;

        try {
            int entity = 0;
            int oper = 2;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");

            if (result == 1) {
                team = new Team();
                team.code = code;
                team.name = in.readUTF();
            }
        } catch (IOException ignore) {}

        return team;
    }

    public void deleteTeam(int code) {
        try {
            int entity = 0;
            int oper = 3;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");
        } catch (IOException ignore) {}

    }

    public void showTeams() {
        try {
            int entity = 0;
            int oper = 4;

            out.writeInt(entity);
            out.writeInt(oper);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");

            if (result == 1) {
                int size = in.readInt();
                System.out.println("Teams:");

                for (int i = 0; i < size; i++) {
                    System.out.println("ID: " + in.readInt() + " name: " + in.readUTF());
                }
            }
        } catch (IOException ignore) {}
    }

    public void addPlayer(int code, String name, boolean isCaptain, int salary, int teamCode) {
        try {
            int entity = 1;
            int oper = 0;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);
            out.writeUTF(name);
            out.writeInt(isCaptain? 1 : 0);
            out.writeInt(salary);
            out.writeInt(teamCode);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");
        } catch (IOException ignore) {}
    }

    public void updatePlayer(int code, String name, Boolean isCaptain, Integer salary, Integer teamCode) {
        try {
            int entity = 1;
            int oper = 1;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);

            if (name != null) {
                out.writeInt(1);
                out.writeUTF(name);
            } else {
                out.writeInt(0);
            }

            if (isCaptain != null) {
                out.writeInt(1);      //if null send 0, else 1
                out.writeInt(isCaptain? 1 : 0);
            } else {
                out.writeInt(0);
            }

            if (salary != null) {
                out.writeInt(1);
                out.writeInt(salary);
            } else {
                out.writeInt(0);
            }

            if (teamCode != null) {
                out.writeInt(1);
                out.writeInt(teamCode);
            } else {
                out.writeInt(0);
            }

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");
        } catch (IOException ignore) {}
    }

    public Player getPlayer(int code) {
        Player player = null;

        try {
            int entity = 1;
            int oper = 2;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");

            if (result == 1) {
                player = new Player();

                player.code = code;
                player.name = in.readUTF();
                player.isCaptain = in.readInt() == 1;
                player.salary = in.readInt();
                player.teamCode = in.readInt();
            }
        } catch (IOException ignore) {}

        return player;
    }

    public void deletePlayer(int code) {
        try {
            int entity = 1;
            int oper = 3;

            out.writeInt(entity);
            out.writeInt(oper);

            out.writeInt(code);

            int result = in.readInt();

            System.out.println(result == 1? "Success" : "Failed");
        } catch (IOException ignore) {}
    }



    public void showPlayers() {
        try {
            int entity = 1;
            int oper = 4;

            out.writeInt(entity);
            out.writeInt(oper);

            int result = in.readInt();

            System.out.println(result == 1 ? "Success" : "Failed");

            if (result == 1) {
                int size = in.readInt();
                System.out.println("Players:");

                for (int i = 0; i < size; i++) {
                    System.out.println("ID: " + in.readInt() + " name: " + in.readUTF() +
                            " captain: " + (in.readInt() == 1 ? "1" : "0") + " salary: " +
                            in.readInt() + " teamCode: " + in.readInt());
                }
            }
        } catch (IOException ignore) { }
    }
}
