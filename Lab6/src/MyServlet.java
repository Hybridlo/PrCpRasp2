import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class MyServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Team> teams = null;
        PrintWriter out = response.getWriter();
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            out.println(e.getMessage());
        }
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&useSSL=false";
        try {
            Connection con = DriverManager.getConnection(url, "root", "5502");
            Football football = new Football(con);
            teams = football.getTeams(out);
            football.stop();
        } catch (SQLException e) {
            out.println(e.getMessage());
        }

        response.setContentType("text/html;charset=UTF-8");
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Teams</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Teams</h1>");

            for (Team team : teams) {
                out.println(team.code + " name: " + team.name + " players: <br>");
                for (Player player : team.players) {
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;"+ player.code + " - " + player.name + " - " +
                            (player.isCaptain?"1":"0") + " - " + player.salary + "<br>");
                }
            }

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    public String getServletInfo() {
        return "Teams";
    }
}
