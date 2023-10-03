package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controlador {
    @PostMapping("/registrar-libro")
    public String registerbook(@RequestBody Libros libros) throws SQLException, ClassNotFoundException {
        String answer = "";

        String code = libros.getCode();
        String authorname = libros.getAuthorname();
        String namebook = libros.getNamebook();
        String date = libros.getDate();
        String bussines = libros.getBussines();

        if (code.equals("") || code.length()<0 || authorname.equals("") || authorname.length() <0 ||
                namebook.equals("") || namebook.length() <0 || date.equals("") || date.length() <0 ||
                bussines.equals("") || bussines.length()<0){

            answer = "No se admiten campos vacios o nulos,";
            answer += " no se puedo registrar el libro";
        }else{
            Register(code, authorname, namebook, date, bussines);

            answer = " Libro registrado de manera exitosa";
        }

        return answer;
    }

    private void Register(String code, String authorname, String namebook, String date, String bussines) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/api_libros";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM libros");


            // Sentencia INSERT
            String sql = "INSERT INTO libros (code, authorname, namebook, date, bussines) VALUES (?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, authorname);
            preparedStatement.setString(3, namebook);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, bussines);

            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Libro registrado de manera exitosa.");
            } else {
                System.out.println("No se pudo registrar el libro");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/editar-libro")
    public String editbook(@RequestBody Libros libros) throws SQLException, ClassNotFoundException {
        String answer = "";
        String code = libros.getCode();
        String authorname = libros.getAuthorname();
        String namebook = libros.getNamebook();
        String date = libros.getDate();
        String bussines = libros.getBussines();

        if (code.equals("") || code.length()<0 || authorname.equals("") || authorname.length() <0 ||
                namebook.equals("") || namebook.length() <0 || date.equals("") || date.length() <0 ||
                bussines.equals("") || bussines.length()<0) {

            answer = "No se admiten campos vacios o nulos,";
            answer += " no se puedo editar o actualizar el libro";
        }else {

            Edit(code, authorname, namebook, date, bussines);

            answer = "Libro actualizado o editado de manera exitosa";
        }

        return answer;
    }

    private void Edit(String code, String authorname, String namebook, String date, String bussines) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/api_libros";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE libros SET authorname = ?, namebook = ?, date = ?, bussines = ? WHERE code = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, authorname);
        preparedStatement.setString(2, namebook);
        preparedStatement.setString(3, date);
        preparedStatement.setString(4, bussines);
        preparedStatement.setString(5, code);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Libro actualizado de manera exitosa");
        } else {
            System.out.println("No se encontro el libro para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }
    @GetMapping("/buscar-libro")
    public Libros searchbook(@RequestBody Libros libros) throws SQLException, ClassNotFoundException {

        String book = libros.getCode();

        if (libros.getCode().equals("") || libros.getCode().length()<0) {

            libros.setCode("No se encuentra un libro registrado con el codigo ingresado");

        }else {

            libros = Select_book(book);
        }
        return libros;
    }

    private Libros Select_book(String book) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/api_libros";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM libros WHERE code = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, book); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {

            String code = resultSet.getString("code");
            String authorname = resultSet.getString("authorname");
            String namebook = resultSet.getString("namebook");
            String date = resultSet.getString("date");
            String bussines = resultSet.getString("bussines");

            Libros libros = new Libros (code, authorname, namebook, date, bussines);
            return libros;
        }
        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();

        return null;
    }
    @GetMapping("/buscar-libros")
    public List<Libros> searchbooks() throws SQLException, ClassNotFoundException {

        List<Libros> list = Selectconsul();

        String answer = "Estoy en el metodo de buscar";


        return list;
    }

    private List<Libros> Selectconsul() throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/api_libros";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM libros");

        List<Libros> list = new ArrayList<>();

        while (resultSet2.next()) {
            String code = resultSet2.getString("code");
            String authorname = resultSet2.getString("authorname");
            String namebook = resultSet2.getString("namebook");
            String date = resultSet2.getString("date");
            String bussines = resultSet2.getString("bussines");

            Libros libros = new Libros(code, authorname, namebook, date, bussines);

            list.add(libros);

        }
        return list;
    }
    @DeleteMapping("/eliminar-libro")
    public String deletebook(@RequestBody Libros libros) throws SQLException, ClassNotFoundException {

        String answer = "";

        String codebook = libros.getCode();

        if (libros.getCode().equals("") || libros.getCode().length()<0) {

            answer = ("No se encuentra un libro registrado con el codigo ingresado para eliminar");

        }else {

            int f = Delete(codebook);
             if (f==0){
                 answer = "No se encontro un libro con el codigo especificado";
             }else {

                 answer = " libro eliminado exitosamente";
             }
        }

        return answer;
    }

    private int Delete(String codebook) throws SQLException, ClassNotFoundException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/api_libros";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM libros WHERE code = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, codebook);
        int f = prepareStatement.executeUpdate();

        System.out.println("libro eliminado correctamente");
        return f;
    }

    }


