package se.lexicon.data;
import se.lexicon.model.ShoppingCart;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ShoppingCartDAOImpl extends AbstractDAO implements ShoppingCartDAO{

    private String url= "jdbc:mysql://localhost:3306/shopping_practice?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private Connection connection =null;
    private PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public ShoppingCart save(ShoppingCart cart) {
        try {
            Optional<ShoppingCart> shoppingCartFound= findById(cart.getId());
            connection = DriverManager.getConnection(url, "root", "root");
            if (shoppingCartFound.isPresent() && shoppingCartFound.get().getId()>0){
                preparedStatement= connection.prepareStatement("UPDATE shopping_cart  SET last_update=(?), order_status=(?), delivery_address=(?), customer_reference=(?), WHERE id = (?)");
                preparedStatement.setString(1, cart.getLastUpdate().toString());
                preparedStatement.setString(2,cart.getOrderStatus());
                preparedStatement.setString(3,cart.getDeliveryAddress());
                preparedStatement.setString(4,cart.getCustomerReference());
            }else {
                preparedStatement= connection.prepareStatement("INSERT INTO shopping_cart (id,last_update,order_status,delivery_address,customer_reference) VALUES (?,?,?,?,?)");
                preparedStatement.setInt(1,cart.getId());
                preparedStatement.setString(2,cart.getLastUpdate().toString());
                preparedStatement.setString(3,cart.getOrderStatus());
                preparedStatement.setString(4,cart.getDeliveryAddress());
                preparedStatement.setString(5,cart.getCustomerReference());
            }

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }
        return cart;
    }

    @Override
    public Optional<ShoppingCart> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> findAll() {
        return null;
    }

    @Override
    public List<ShoppingCart> findByOrderStatus(String status) {
        return null;
    }

    @Override
    public List<ShoppingCart> findByReference(String customer) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
