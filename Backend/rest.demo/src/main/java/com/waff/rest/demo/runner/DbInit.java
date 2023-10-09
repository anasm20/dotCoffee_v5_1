package com.waff.rest.demo.runner;

import com.waff.rest.demo.model.*;
import com.waff.rest.demo.service.CartService;
import com.waff.rest.demo.service.CategoryService;
import com.waff.rest.demo.service.ProductService;
import com.waff.rest.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DbInit(UserService userService, CategoryService categoryService, ProductService productService, CartService cartService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        try {
            //userService.deleteUsers();
            User admin = null;
            User user = null;
            if(!userService.hasUsers()) {
                admin = new User()
                        .setUsername("admin")
                        .setEmail("admin@test.com")
                        .setFirstname("Admin")
                        .setLastname("Admin")
                        .setUserType(UserType.admin)
                        .setPassword("admin")
                        .setEnabled(true);

                user = new User()
                        .setUsername("user")
                        .setEmail("user@test.com")
                        .setFirstname("Lars")
                        .setLastname("Müller")
                        .setUserType(UserType.user)
                        .setPassword("user")
                        .setEnabled(true);

                userService.createUser(admin);
                userService.createUser(user);
            }

            if(!categoryService.hasCategories() && !productService.hasProducts()) {
                var arabica = new Category()
                        .setName("Kaffee - Arabica")
                        .setImagePath("/uploads/images/category/arabico.jpg");

                var robusta = new Category()
                        .setName("Kaffee - Robusta")
                        .setImagePath("/uploads/images/category/robusta.jpg");

                var cup = new Category()
                        .setName("Kaffee - Becher")
                        .setImagePath("/uploads/images/category/cup.jpg");
                // Only for test
                // need to save manually image on the upload directory
                // or over the FrontEnd Modul
                categoryService.createCategory(arabica, null).ifPresent(p -> arabica.setId(p.getId()));
                categoryService.createCategory(robusta, null).ifPresent(p -> robusta.setId(p.getId()));
                categoryService.createCategory(cup, null).ifPresent(p -> cup.setId(p.getId()));;

                var arabica_250 = new Product()
                        .setTitel("Kaffee Web - 250mg")
                        .setDescription("Hochwertiger Arabica Kaffee hat einen leicht " +
                                "süßlichen Geschmack mit floralen Noten und fruchtigen, beerigen Aromen. " +
                                "Je nach persönlichem Geschmack wird die fruchtige Säure und Abwesenheit von " +
                                "Bitterkeit als angenehm empfunden.")
                        .setImagePath("/uploads/images/product/coffee_bag.JPG")
                        .setPrice("15,00")
                        .setQuantity(10)
                        .setCategory(arabica);


                var arabica_750 = new Product()
                        .setTitel("Kaffee Web - 750mg")
                        .setDescription("Hochwertiger Arabica Kaffee hat einen leicht " +
                                "süßlichen Geschmack mit floralen Noten und fruchtigen, beerigen Aromen. " +
                                "Je nach persönlichem Geschmack wird die fruchtige Säure und Abwesenheit von " +
                                "Bitterkeit als angenehm empfunden.")
                        .setImagePath("/uploads/images/product/coffee_bag.JPG")
                        .setPrice("20,00")
                        .setQuantity(10)
                        .setCategory(arabica);

                var robusta_250 = new Product()
                        .setTitel("Kaffee Web - 250mg")
                        .setDescription("Hochwertiger Robusta Kaffee hat einen leicht süßlichen Geschmack mit floralen " +
                                "Noten und fruchtigen, beerigen Aromen. Je nach persönlichem Geschmack wird die fruchtige " +
                                "Säure und Abwesenheit von Bitterkeit als angenehm empfunden.")
                        .setImagePath("/uploads/images/product/coffee_bag.JPG")
                        .setPrice("10,00")
                        .setQuantity(10)
                        .setCategory(robusta);


                var robusta_500 = new Product()
                        .setTitel("Kaffee Web - 500mg")
                        .setDescription("Hochwertiger Robusta Kaffee hat einen leicht süßlichen Geschmack mit floralen " +
                                "Noten und fruchtigen, beerigen Aromen. Je nach persönlichem Geschmack wird die fruchtige " +
                                "Säure und Abwesenheit von Bitterkeit als angenehm empfunden.")
                        .setImagePath("/uploads/images/product/coffee_bag.JPG")
                        .setPrice("15,00")
                        .setQuantity(10)
                        .setCategory(robusta);

                productService.createProduct(arabica_250, null).ifPresent(p -> arabica_250.setId(p.getId()));
                productService.createProduct(arabica_750, null).ifPresent(p -> arabica_750.setId(p.getId()));
                productService.createProduct(robusta_250, null).ifPresent(p -> robusta_250.setId(p.getId()));
                productService.createProduct(robusta_500, null).ifPresent(p -> robusta_500.setId(p.getId()));

                Cart cart = cartService.getCartByUserId(user.getId()).get();
                cartService.addItemToCart(cart.getId(), arabica_250.getId());
                cartService.addItemToCart(cart.getId(), arabica_750.getId());

                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0");

            }
        } catch (Exception e ){
          e.printStackTrace();
        }
    }
}
