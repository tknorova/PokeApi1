package service;

import model.Pokemon;
import model.UserPreferences;
import java.util.List;

public class RecommendationEngine {
    private PokemonScorer scorer = new PokemonScorer();

    public List<Pokemon> getTopPokemon(List<Pokemon> allPokemon, UserPreferences prefs) {
        allPokemon.sort((p1, p2) -> Double.compare(
                scorer.score(p2, prefs),
                scorer.score(p1, prefs)
        ));
        return allPokemon.subList(0, Math.min(10, allPokemon.size()));
    }
}
