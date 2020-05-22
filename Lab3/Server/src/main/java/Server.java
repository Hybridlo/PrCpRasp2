import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket sock = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private Football football;

    Server(Football football) {
        this.football = football;
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Started...");

        while (true) {
            sock = server.accept();

            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());

            while (processQuery()) ;
        }
    }

    private boolean processQuery() {
        try {
            int entity = in.readInt(); // 0 - teams, 1 - players
            int oper = in.readInt();

            int code;
            String name;
            Integer isCaptain;
            Integer salary;
            Integer teamCode;
            Integer result;

            switch (entity) {
                case 0:
                    switch (oper) {
                        case 0:     //add
                            code = in.readInt();
                            name = in.readUTF();
                            result = football.addTeam(code, name)? 1 : 0;
                            out.writeInt(result);
                            break;
                        case 1:     //update
                            code = in.readInt();

                            int status;
                            status = in.readInt();
                            name = (status == 1? in.readUTF() : null);      //if status = 1 read, else set null

                            result = football.updateTeam(code, name)? 1 : 0;
                            out.writeInt(result);
                            break;
                        case 2:     //get
                            code = in.readInt();
                            Team team = football.getTeam(code);
                            if (team != null) {
                                out.writeInt(1);
                                out.writeUTF(team.name);
                            } else {
                                out.writeInt(0);
                            }
                            break;
                        case 3:     //delete
                            code = in.readInt();
                            result = football.deleteTeam(code)? 1 : 0;
                            out.writeInt(result);
                            break;
                        case 4:     //get all
                            List<Team> teams = football.showTeams();
                            if (teams != null) {
                                out.writeInt(1);
                                out.writeInt(teams.size());
                                for (Team entry : teams) {
                                    out.writeInt(entry.code);
                                    out.writeUTF(entry.name);
                                }
                            } else {
                                out.writeInt(0);
                            }
                    }
                    break;
                case 1:
                    switch (oper) {
                        case 0:     //add
                            code = in.readInt();
                            name = in.readUTF();
                            isCaptain = in.readInt();
                            salary = in.readInt();
                            teamCode = in.readInt();
                            result = football.addPlayer(code, name, isCaptain == 1, salary, teamCode)? 1 : 0;
                            out.writeInt(result);
                            break;
                        case 1:     //update
                            code = in.readInt();

                            int status;
                            status = in.readInt();
                            name = (status == 1? in.readUTF() : null);

                            status = in.readInt();
                            isCaptain = (status == 1? in.readInt() : null);

                            status = in.readInt();
                            salary = (status == 1? in.readInt() : null);

                            status = in.readInt();
                            teamCode = (status == 1? in.readInt() : null);

                            Boolean boolIsCap;
                            if (isCaptain == null) boolIsCap = null;
                            else boolIsCap = isCaptain == 1;

                            result = football.updatePlayer(code, name, boolIsCap, salary, teamCode)? 1 : 0;
                            out.writeInt(result);
                            break;
                        case 2:     //get
                            code = in.readInt();
                            Player player = football.getPlayer(code);
                            if (player != null) {
                                out.writeInt(1);
                                out.writeUTF(player.name);
                                out.writeInt(player.isCaptain? 1 : 0);
                                out.writeInt(player.salary);
                                out.writeInt(player.teamCode);
                            } else {
                                out.writeInt(0);
                            }
                            break;
                        case 3:     //delete
                            code = in.readInt();
                            result = football.deletePlayer(code)? 1 : 0;
                            out.writeInt(result);
                            break;
                        case 4:     //get all
                            List<Player> players = football.showPlayers();
                            if (players != null) {
                                out.writeInt(1);
                                out.writeInt(players.size());
                                for (Player entry : players) {
                                    out.writeInt(entry.code);
                                    out.writeUTF(entry.name);
                                    out.writeInt(entry.isCaptain? 1 : 0);
                                    out.writeInt(entry.salary);
                                    out.writeInt(entry.teamCode);
                                }
                            } else {
                                out.writeInt(0);
                            }
                    }
                    break;
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}