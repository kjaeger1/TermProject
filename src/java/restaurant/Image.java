package restaurant;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.primefaces.event.FileUploadEvent;

@Named(value = "image")
@SessionScoped
public class Image implements Serializable {

    @Resource(name = "jdbc/db1")
    private DataSource ds;

    private final List<Integer> ids = new ArrayList();
    private int index;

    public void handleFileUpload(FileUploadEvent event) {
        try {
            Connection conn = ds.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(
                        "insert into photo (itemid, name, type, size, contents) values (?, ?, ?, ?, ?)");
                ps.setInt(1, (int) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().
                        getSession(false)).getAttribute("itemId"));
                ps.setString(2, event.getFile().getFileName());
                ps.setString(3, event.getFile().getContentType());
                ps.setLong(4, event.getFile().getSize());
                try {
                    ps.setBinaryStream(5, event.getFile().getInputstream());
                } catch (IOException ex) {
                    Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
                }
                ps.executeUpdate();
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }

    public HashMap<Integer, Integer> getCounts() {
        HashMap<Integer, Integer> result = new HashMap();
        try {
            Connection conn = ds.getConnection();
            try {
                ResultSet rs = conn.createStatement().executeQuery(
                        "select itemid, count(id) c from photo group by itemid");
                while (rs.next()) {
                    result.put(rs.getInt("itemid"), rs.getInt("c"));
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public void setIds(int itemId) {
        ids.clear();
        try {
            Connection conn = ds.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(
                        "select id from photo where itemid = ?");
                ps.setInt(1, itemId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
        }
        index = 0;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public Integer getId() {
        if (ids.isEmpty()) {
            return null;
        }
        return ids.get(index);
    }

    public boolean backable() {
        return index > 0;
    }

    public void back() {
        index--;
    }

    public boolean nextable() {
        return index < ids.size() - 1;
    }

    public void next() {
        index++;
    }

    public void del(int id) {
        try {
            Connection conn = ds.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(
                        "delete from photo where id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
            } finally {
                conn.close();
            }
        } catch (SQLException e) {

        }
    }
}
