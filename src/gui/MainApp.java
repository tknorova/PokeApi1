package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import model.Pokemon;
import model.UserPreferences;
import service.RecommendationEngine;
import service.PokemonScorer;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    // Prozatím pár testovacích Pokémonů
    private List<Pokemon> allPokemon = List.of(
            new Pokemon("Charizard", List.of("fire","flying"), 84,78,100,78,17,90,false,""),
            new Pokemon("Blastoise", List.of("water"), 83,100,78,79,16,85,false,""),
            new Pokemon("Venusaur", List.of("grass","poison"),82,83,80,80,20,90,false,"")
    );

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PokeAPI Recommendation");

        // --- DOTAZNÍK ---
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // Typy
        Label typeLabel = new Label("Vyber oblíbené typy:");
        CheckBox fire = new CheckBox("Fire");
        CheckBox water = new CheckBox("Water");
        CheckBox grass = new CheckBox("Grass");
        HBox typeBox = new HBox(10, fire, water, grass);

        // Váhy statistik
        Label weightLabel = new Label("Nastav váhy statistik:");
        Slider attackSlider = new Slider(0,5,1);
        Slider defenseSlider = new Slider(0,5,1);
        Slider speedSlider = new Slider(0,5,1);
        VBox weightBox = new VBox(5,
                new Label("Attack"), attackSlider,
                new Label("Defense"), defenseSlider,
                new Label("Speed"), speedSlider
        );

        // Velikost
        Label sizeLabel = new Label("Vyber preferovanou velikost:");
        ToggleGroup sizeGroup = new ToggleGroup();
        RadioButton small = new RadioButton("SMALL"); small.setToggleGroup(sizeGroup);
        RadioButton medium = new RadioButton("MEDIUM"); medium.setToggleGroup(sizeGroup);
        RadioButton large = new RadioButton("LARGE"); large.setToggleGroup(sizeGroup);
        RadioButton any = new RadioButton("ANY"); any.setToggleGroup(sizeGroup); any.setSelected(true);
        HBox sizeBox = new HBox(10, small, medium, large, any);

        // Legendární
        CheckBox legendary = new CheckBox("Chci legendární Pokémony");

        // Tlačítko
        Button findButton = new Button("Najdi mého Pokémona");

        root.getChildren().addAll(typeLabel, typeBox,
                weightLabel, weightBox,
                sizeLabel, sizeBox,
                legendary, findButton
        );

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- AKCE TLAČÍTKA ---
        findButton.setOnAction(e -> {
            List<String> types = new ArrayList<>();
            if(fire.isSelected()) types.add("fire");
            if(water.isSelected()) types.add("water");
            if(grass.isSelected()) types.add("grass");

            double attackW = attackSlider.getValue();
            double defenseW = defenseSlider.getValue();
            double speedW = speedSlider.getValue();

            String selectedSize = ((RadioButton) sizeGroup.getSelectedToggle()).getText();
            boolean wantsLegendary = legendary.isSelected();

            UserPreferences prefs = new UserPreferences(types, attackW, defenseW, speedW, selectedSize, wantsLegendary);

            RecommendationEngine engine = new RecommendationEngine();
            PokemonScorer scorer = new PokemonScorer();
            List<Pokemon> top10 = engine.getTopPokemon(allPokemon, prefs);

            System.out.println("=== TOP 10 Pokémonů podle dotazníku ===");
            for(Pokemon p : top10){
                System.out.println(p.getName() + " | Score: " + scorer.score(p,prefs)
                        + " | Typy: " + p.getTypes());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
