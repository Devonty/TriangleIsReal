module com.csvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.csvsu.rasterizationfxapp to javafx.fxml;
    exports com.csvsu.rasterizationfxapp;
}