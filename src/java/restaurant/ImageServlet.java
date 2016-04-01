package restaurant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

    @Resource(name = "jdbc/db1")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from photo where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String name = rs.getString("name");
            String type = rs.getString("type");
            Blob contents = rs.getBlob("contents");

            response.setContentType(type);
            response.setHeader("Content-Disposition", "inline; filename=\"" + name + "\"");
            int length = 0;
            InputStream in = contents.getBinaryStream();
            OutputStream out = response.getOutputStream();
            byte[] bbuf = new byte[1024];
            while (in != null && (length = in.read(bbuf)) != -1) {
                out.write(bbuf, 0, length);
            }
        } catch (SQLException e) {
        }
    }
}
