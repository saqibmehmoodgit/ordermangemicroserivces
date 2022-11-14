package repository;


import com.prodig.micro.stock.domain.Product;
import com.prodig.micro.stock.repository.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest
{

    @Autowired
    TestEntityManager em;

    @Autowired
     ProductRepository repository;


    @Test
   public void verifyBootstrappingByPersistingAnEmployee() {
        Product product = new Product();
        product.setName("saqib");

        Assertions.assertNull(product.getId());
        em.persist(product);
        Assertions.assertNotNull(product.getName());
    }

    @Test
    public void verifyRepositoryByPersistingAnEmployee() {
        Product product = new Product();
        product.setName("saqib");


        Assertions.assertNull(product.getId());



        repository.save(product);
        Assertions.assertNotNull(product.getName());
    }

}
