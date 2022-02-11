package se.lexicon.data;

import se.lexicon.model.Product;
import se.lexicon.model.ShoppingCartItem;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ShoppingCartItemDAOImpl extends AbstractDAO implements ShoppingCartItemDAO{


    private String url= "jdbc:mysql://localhost:3306/shopping_practice?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private Connection connection =null;
    private PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public ShoppingCartItem save(ShoppingCartItem cartItem) {
        try {
            Optional<ShoppingCartItem> foundShoppingCartItem = findById(cartItem.getId());
            connection = DriverManager.getConnection(url, "root", "root");
            if (foundShoppingCartItem.isPresent() && foundShoppingCartItem.get().getId()>0){
                preparedStatement= connection.prepareStatement("UPDATE shopping_cart_item  SET amount=(?), total_price=(?),product_id=(?),shopping_cart_id=(?) WHERE id = (?)");
                preparedStatement.setInt(1,cartItem.getAmount());
                preparedStatement.setDouble(2,cartItem.getTotalPrice());
                preparedStatement.setInt(3,cartItem.getItem().getId());
                preparedStatement.setInt(4,cartItem.getCart().getId());
                preparedStatement.setInt(5,cartItem.getId());
            }else {
                preparedStatement= connection.prepareStatement("INSERT INTO shopping_cart_item (id,amount,total_price,product_id,shopping_cart_id) VALUES (?,?,?,?,?)");
                preparedStatement.setInt(1,cartItem.getId());
                preparedStatement.setInt(2,cartItem.getAmount());
                preparedStatement.setDouble(3,cartItem.getTotalPrice());
                preparedStatement.setInt(4,cartItem.getItem().getId());
                preparedStatement.setInt(5,cartItem.getCart().getId());
            }

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(preparedStatement,connection);
        }
        return cartItem;
    }

    @Override
    public Optional<ShoppingCartItem> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<ShoppingCartItem> findAll() {
        return null;
    }

    @Override
    public List<ShoppingCartItem> findByCartId(int cartId) {
        return null;
    }

    @Override
    public List<ShoppingCartItem> findByProductId(int productId) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
