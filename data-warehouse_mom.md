## 1. Einleitung
Dieses Projekt demonstriert die Implementierung eines Datenzugriffsmodells mit Spring Boot und **Object Relational Mapping (ORM)**. Ziel ist es, eine Java-Anwendung mit einer MySQL-Datenbank zu verknüpfen, um Lagerhaus- und Produktdaten persistent zu speichern. Dabei wird der JPA-Standard (Java Persistence API) verwendet, um die Objekt-Beziehungen auf relationale Datenbanktabellen abzubilden.

---

## 2. Systemkonfiguration

### 2.1 Datenbank-Setup (Docker)
Die Persistenzschicht wird durch einen MySQL-Service bereitgestellt, der in einem Docker-Container läuft. Der Container wird im Host-Netzwerkmodus betrieben, um eine direkte Kommunikation zwischen der Spring-Anwendung und der Datenbank zu ermöglichen:

```bash
docker run --name mysql-db --network host \
-e MYSQL_ROOT_PASSWORD=secret \
-e MYSQL_DATABASE=example \
-e MYSQL_USER=springuser \
-e MYSQL_PASSWORD=springpw \
-d mysql:latest
```

### 2.2 Anwendungskonfiguration
Die Verbindungseinstellungen sind in der Datei `src/main/resources/application.properties` hinterlegt. Hier wird der MySQL-Treiber sowie die automatische Tabellenerstellung durch Hibernate definiert:

```properties
spring.application.name=demo
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/example
spring.datasource.username=springuser
spring.datasource.password=springpw
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
```

---

## 3. Implementierung des Datenmodells

### 3.1 Entities und Relationen
Das Herzstück der Anwendung sind die Entity-Klassen, die die Tabellenstruktur repräsentieren. Zwischen `Warehouse` und `Product` besteht eine **1:n Beziehung**.

**Warehouse Entity (Ausschnitt):**
```java
@Entity
public class Warehouse {
    @Id
    private String warehouseID;
    private String warehouseName;
    private String warehouseCity;
    
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Product> products;
    
    // Getter und Setter
}
```

**Product Entity (Ausschnitt):**
```java
@Entity
public class Product {
    @Id
    private String productID;
    private String productName;
    private int productQuantity;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    
    // Getter und Setter
}
```

### 3.2 Repository Layer
Für den Datenbankzugriff werden Repositories verwendet, die von `CrudRepository` erben. Dies ermöglicht den Zugriff auf Standard-Methoden wie `save()`, `findAll()` oder `findById()` ohne manuelles SQL.

```java
public interface ProductRepository extends CrudRepository<Product, String> {
    // Spezialisierte Abfrage für die Suche nach Produkt in einem bestimmten Lager
    Optional<Product> findByWarehouse_WarehouseIDAndProductID(String warehouseID, String productID);
}
```

---

## 4. Daten-Initialisierung (Data Seeding)
Um die Funktionalität zu testen, werden beim Start der Anwendung automatisch Testdaten erzeugt. Dies geschieht über einen `CommandLineRunner` in der `Main`-Klasse:

```java
@Bean
public CommandLineRunner initData(WarehouseRepository wRepo, ProductRepository pRepo) {
    return args -> {
        // Warehouse erstellen
        Warehouse w1 = new Warehouse();
        w1.setWarehouseID("001");
        w1.setWarehouseName("Linz Bahnhof");
        wRepo.save(w1);

        // Produkt erstellen und dem Lager zuweisen
        Product p1 = new Product();
        p1.setProductID("00-443175");
        p1.setProductName("Bio Orangensaft");
        p1.setWarehouse(w1);
        pRepo.save(p1);
        
        System.out.println("Datenbank erfolgreich initialisiert.");
    };
}
```



Hinzufügen von neuem User:
![[Pasted image 20260421162313.png]]


Abrufen nach fertigstellung:

![[Pasted image 20260421162223.png]]


---


## 5. Theorie-Fragen

**1. Was ist ORM und wie wird JPA verwendet?**
ORM (Object-Relational Mapping) ist eine Technik, um Objekte aus einer objektorientierten Sprache (Java) auf Tabellen einer relationalen Datenbank abzubilden. JPA (Java Persistence API) ist die Spezifikation in Java, die den Standard für dieses Mapping definiert. Frameworks wie Hibernate setzen diesen Standard um.

**2. Wofür wird die `application.properties` verwendet und wo muss sie gespeichert werden?**
Diese Datei enthält die zentrale Konfiguration der Anwendung (Datenbank-URL, Zugangsdaten, Hibernate-Einstellungen). Sie befindet sich im Verzeichnis `src/main/resources`.

**3. Welche Annotationen werden häufig für Entity-Typen verwendet? Welche Kernpunkte müssen beachtet werden?**
*   `@Entity`: Kennzeichnet die Klasse als persistent.
*   `@Id`: Definiert den Primärschlüssel.
*   `@ManyToOne` / `@OneToMany`: Definiert die Art der Beziehung zwischen Objekten.
*   **Kernpunkt:** Entities benötigen einen parameterlosen Konstruktor und eindeutige IDs.

**4. Welche Methoden werden für CRUD-Operationen benötigt?**
Durch das `CrudRepository` stehen folgende Methoden direkt zur Verfügung:
*   `save()`: Speichern und Aktualisieren.
*   `findById()`: Suchen über die ID.
*   `findAll()`: Alle Datensätze abrufen.
*   `deleteById()`: Löschen eines Datensatzes.

---
