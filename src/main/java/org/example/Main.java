package org.example;

import org.example.warehouse.Product;
import org.example.warehouse.Warehouse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initData(WarehouseRepository warehouseRepo, ProductRepository productRepo) {
        return args -> {

            System.out.println("====== Starte Datenbank-Befüllung ======");

            Warehouse w1 = new Warehouse();
            w1.setWarehouseID("001");
            w1.setWarehouseName("Linz Bahnhof");
            w1.setWarehouseAddress("Bahnhofsstrasse 27/9");
            w1.setWarehousePostalCode("4020");
            w1.setWarehouseCity("Linz");
            w1.setWarehouseCountry("Austria");
            warehouseRepo.save(w1); // Speichert im WarehouseRepository

            Warehouse w2 = new Warehouse();
            w2.setWarehouseID("002");
            w2.setWarehouseName("Wien Zentrum");
            w2.setWarehouseAddress("Stephansplatz 1");
            w2.setWarehousePostalCode("1010");
            w2.setWarehouseCity("Wien");
            w2.setWarehouseCountry("Austria");
            warehouseRepo.save(w2); // Speichert im WarehouseRepository
            

            // --- 5 Produkte für Lager 1 (Linz) ---
            Product p1 = new Product();
            p1.setProductID("00-443175");
            p1.setProductName("Bio Orangensaft Sonne");
            p1.setProductCategory("Getraenk");
            p1.setProductQuantity(2500);
            p1.setProductUnit("Packung 1L");
            p1.setWarehouse(w1); // Zuweisung an Linz
            productRepo.save(p1); // Speichert im ProductRepository!

            Product p2 = new Product();
            p2.setProductID("00-871895");
            p2.setProductName("Bio Apfelsaft Gold");
            p2.setProductCategory("Getraenk");
            p2.setProductQuantity(3420);
            p2.setProductUnit("Packung 1L");
            p2.setWarehouse(w1);
            productRepo.save(p2);

            Product p3 = new Product();
            p3.setProductID("00-112233");
            p3.setProductName("Mineralwasser Prickelnd");
            p3.setProductCategory("Getraenk");
            p3.setProductQuantity(5000);
            p3.setProductUnit("Flasche 1.5L");
            p3.setWarehouse(w1);
            productRepo.save(p3);

            Product p4 = new Product();
            p4.setProductID("00-223344");
            p4.setProductName("Vollmilch 3.5%");
            p4.setProductCategory("Molkerei");
            p4.setProductQuantity(1200);
            p4.setProductUnit("Packung 1L");
            p4.setWarehouse(w1);
            productRepo.save(p4);

            Product p5 = new Product();
            p5.setProductID("00-334455");
            p5.setProductName("Haferdrink Barista");
            p5.setProductCategory("Molkerei");
            p5.setProductQuantity(850);
            p5.setProductUnit("Packung 1L");
            p5.setWarehouse(w1);
            productRepo.save(p5);


            // --- 5 Produkte für Lager 2 (Wien) ---
            Product p6 = new Product();
            p6.setProductID("01-998877");
            p6.setProductName("Cola Classic");
            p6.setProductCategory("Getraenk");
            p6.setProductQuantity(4000);
            p6.setProductUnit("Dose 0.33L");
            p6.setWarehouse(w2); // Zuweisung an Wien
            productRepo.save(p6);

            Product p7 = new Product();
            p7.setProductID("01-887766");
            p7.setProductName("Eistee Pfirsich");
            p7.setProductCategory("Getraenk");
            p7.setProductQuantity(2100);
            p7.setProductUnit("Flasche 0.5L");
            p7.setWarehouse(w2);
            productRepo.save(p7);

            Product p8 = new Product();
            p8.setProductID("01-776655");
            p8.setProductName("Apfelschorle Natur");
            p8.setProductCategory("Getraenk");
            p8.setProductQuantity(1500);
            p8.setProductUnit("Flasche 1L");
            p8.setWarehouse(w2);
            productRepo.save(p8);

            Product p9 = new Product();
            p9.setProductID("01-665544");
            p9.setProductName("Energy Drink Power");
            p9.setProductCategory("Getraenk");
            p9.setProductQuantity(3200);
            p9.setProductUnit("Dose 0.25L");
            p9.setWarehouse(w2);
            productRepo.save(p9);

            Product p10 = new Product();
            p10.setProductID("01-554433");
            p10.setProductName("Tomatensaft 100%");
            p10.setProductCategory("Getraenk");
            p10.setProductQuantity(400);
            p10.setProductUnit("Packung 1L");
            p10.setWarehouse(w2);
            productRepo.save(p10);

            System.out.println("====== 2 Warehouses und 10 Produkte erfolgreich gespeichert! ======");
        };
    }
}