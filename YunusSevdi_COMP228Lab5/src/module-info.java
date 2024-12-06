/**
 * 
 */
module ArjunSaji_COMP228Lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc; // Add Oracle JDBC module

    opens exercise1 to javafx.fxml;
    exports exercise1;
}

