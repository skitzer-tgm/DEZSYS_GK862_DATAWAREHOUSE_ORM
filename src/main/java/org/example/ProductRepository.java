package org.example;
import org.example.warehouse.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, String> {
    // Erfüllt die Anforderung: Collect a single product of a data warehouse specified by datawarehouseID and productID.
    Optional<Product> findByWarehouse_WarehouseIDAndProductID(String warehouseID, String productID);
}