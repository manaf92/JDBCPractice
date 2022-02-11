package se.lexicon;

import se.lexicon.data.*;
import se.lexicon.model.Product;
import se.lexicon.model.ShoppingCart;
import se.lexicon.model.ShoppingCartItem;

import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ProductDAOImpl productDAO= new ProductDAOImpl();;

        // productDAO.findByPriceBetween(10000,20005).forEach(System.out::println);

        //productDAO.delete(2);
      // productDAO.save(new Product(6,"car6", 60000));
      //  productDAO.save(new Product(1,"car1", 600));
//        ShoppingCartDAOImpl shoppingCartDAO = new ShoppingCartDAOImpl();
//        shoppingCartDAO.save(new ShoppingCart(
//                        1, LocalDateTime.now(),
//                        "created",
//                        "Mangatan",
//                        "customer1",
//                        false));
//
//
        ShoppingCartItemDAOImpl shoppingCartItemDAO = new ShoppingCartItemDAOImpl();
        shoppingCartItemDAO.save(
                new ShoppingCartItem(
                        1,
                        3,
                        700,
                        new Product(1,"car",6000),
                        new ShoppingCart(
                                1,
                                LocalDateTime.now(),
                        "created",
                        "Mangatan",
                       "customer1",
                        false)
                ));
   }
}
