package com.emergentes.controlador;
import com.emergentes.modelo.Post;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PostControlador", urlPatterns = {"/PostControlador"})
public class PostControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op;
        ArrayList<Post> lista = new ArrayList<Post>();
        op = (request.getParameter("op") != null) ? request.getParameter("op") : "view";
        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;
        if (op.equals("view")) {
            try {
                String sql = "select * from post";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Post po = new Post();
                    po.setId(rs.getInt("id"));
                    po.setFecha(rs.getString("fecha"));
                    po.setTitulo(rs.getString("titulo"));
                    po.setContenido(rs.getString("contenido"));
                    po.setAutor(rs.getString("autor"));
                    lista.add(po);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("blog.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error SQL " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }
        if (op.equals("nuevo")) {
            Post post = new Post();
            int id = post.getId();
            request.setAttribute("post", post);
            request.getRequestDispatcher("nuevo.jsp").forward(request, response);
        }
        if (op.equals("editar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "select * from post where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                Post po = new Post();
                while (rs.next()) {
                    po.setId(rs.getInt("id"));
                    po.setFecha(rs.getString("fecha"));
                    po.setTitulo(rs.getString("titulo"));
                    po.setContenido(rs.getString("contenido"));
                    po.setAutor((rs.getString("autor")));
                }
                request.setAttribute("post", po);
                request.getRequestDispatcher("nuevo.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL " + ex.getMessage());
            }
        }
        if (op.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter(("id")));
            try {
                String sql = "delete from post where id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error SQL " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("PostControlador");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");
        String autor = request.getParameter("autor");
        Post po = new Post();
        po.setId(id);
        po.setFecha(fecha);
        po.setTitulo(titulo);
        po.setContenido(contenido);
        po.setAutor(autor);
        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;
        if (id == 0) {
            String sql = "insert into post (fecha,titulo,contenido,autor) values (?,?,?,?) ";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, po.getFecha());
                ps.setString(2, po.getTitulo());
                ps.setString(3, po.getContenido());
                ps.setString(4, po.getAutor());
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error de SQL " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }
        else{
            try {
                String sql = "update post set titulo=?,contenido=?,autor=?,fecha=? where id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, po.getTitulo());
                ps.setString(2, po.getContenido());
                ps.setString(3, po.getAutor());
                ps.setString(4, po.getFecha());
                ps.setInt(5, po.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error al actualizar "+ex.getMessage());
            }
        }
        response.sendRedirect("PostControlador");
    }
}
