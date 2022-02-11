package se.lexicon.data;

import se.lexicon.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl extends AbstractDAO implements ProductDAO {

    private String url= "jdbc:mysql://localhost:3306/shopping_practice?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private Connection connection =null;
    private PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @Override
    public Product save(Product product) {
        try {
            Optional<Product> foundProduct= findById(product.getId());
            connection = DriverManager.getConnection(url, "root", "root");
            if (foundProduct.isPresent() && foundProduct.get().getId()>0){
                preparedStatement= connection.prepareStatement("UPDATE product  SET name=(?), price=(?) WHERE id = (?)");
                preparedStatement.setString(1,product.getName());
                preparedStatement.setDouble(2,product.getPrice());
                preparedStatement.setInt(3,product.getId());
            }else {
                preparedStatement= connection.prepareStatement("INSERT INTO product (id,name,price) VALUES (?,?,?)");
                preparedStatement.setInt(1,product.getId());
                preparedStatement.setString(2,product.getName());
                preparedStatement.setDouble(3,product.getPrice());
            }

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }
        return product;
    }

    @Override
    public Optional<Product> findById(int id) {
        Optional<Product> product = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, "root", "root");
            preparedStatement= connection.prepareStatement("SELECT * FROM product WHERE id = (?)");
            preparedStatement.setInt(1, id);
            resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){

                 products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                ));
            }
             return products.stream().findFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, "root", "root");
            preparedStatement= connection.prepareStatement("SELECT * FROM product");
            resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                ));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, "root", "root");
            preparedStatement= connection.prepareStatement("SELECT * FROM product WHERE name = (?)");
            preparedStatement.setString(1,name);
            resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                ));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }
        return products;
    }

    @Override
    public List<Product> findByPriceBetween(double low, double high) {
        List<Product> products = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, "root", "root");
            preparedStatement= connection.prepareStatement("SELECT * FROM product WHERE price > (?) AND price <(?)");
            preparedStatement.setDouble(1,low);
            preparedStatement.setDouble(2,high);
            resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                ));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }
        return products;
    }

    @Override
    public void delete(int id) {
        try {
            connection = DriverManager.getConnection(url, "root", "root");
            preparedStatement= connection.prepareStatement("DELETE  FROM product WHERE id = (?)");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }

    }


}
