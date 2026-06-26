module ch.noseryoung.tamagotchi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens ch.noseryoung.tamagotchi to javafx.fxml;
    exports ch.noseryoung.tamagotchi;
}