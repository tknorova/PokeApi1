package app;

import model.Pokemon;
import model.UserPreferences;
import service.PokeAPIService;
import service.PokemonScorer;
import service.RecommendationEngine;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1️⃣ Vytvoření PokeAPI služby
        PokeAPIService apiService = new PokeAPIService();

        // 2️⃣ Načtení prvních 10 Pokémonů (pro test)
        List<Pokemon> pokemons = apiService.getAllPokemon(10);

        // 3️⃣ Vytvoření testovacích preferencí uživatele
        UserPreferences prefs = new UserPreferences(
                List.of("fire", "electric"),  // oblíbené typy
                1.0, 1.0, 1.0,                // váhy statistik
                "ANY",                         // velikost
                false                          // legendární
        );

        // 4️⃣ Vytvoření scoreru a recommendation engine
        PokemonScorer scorer = new PokemonScorer();
        RecommendationEngine engine = new RecommendationEngine();

        // 5️⃣ Spočítání TOP 10
        List<Pokemon> top10 = engine.getTopPokemon(pokemons, prefs);

        // 6️⃣ Výpis výsledků do konzole
        System.out.println("=== TOP 10 Pokémonů podle testovacího dotazníku ===");
        for (Pokemon p : top10) {
            System.out.println(p.getName() + " | Score: " + scorer.score(p, prefs)
                    + " | Typy: " + p.getTypes()
                    + " | Attack: " + p.getAttack()
                    + " | Defense: " + p.getDefense()
                    + " | Speed: " + p.getSpeed());
        }
    }
}
