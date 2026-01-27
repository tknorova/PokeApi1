package service;               // protože soubor je ve složce service

import model.Pokemon;          // import třídy Pokemon
import model.UserPreferences;  // import třídy UserPreferences
public class PokemonScorer {
    public double score(Pokemon p, UserPreferences prefs) {
        double score = 0;

        // Statistiky
        score += p.getAttack() * prefs.getAttackWeight();
        score += p.getDefense() * prefs.getDefenseWeight();
        score += p.getSpeed() * prefs.getSpeedWeight();

        // Typy
        for (String type : p.getTypes()) {
            if (prefs.getPreferredTypes().contains(type)) score += 10; // bonus za typ
        }

        // Velikost
        if (!prefs.getPreferredSize().equals("ANY")) {
            String size = p.getHeight() < 10 ? "SMALL" : p.getHeight() < 20 ? "MEDIUM" : "LARGE";
            if (size.equals(prefs.getPreferredSize())) score += 5;
        }

        // Legendary
        if (prefs.wantsLegendary() && p.isLegendary()) score += 20;

        return score;
    }
}
