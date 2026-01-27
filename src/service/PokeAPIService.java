package service;

import model.Pokemon;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PokeAPIService {

    public Pokemon getPokemon(String name) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + name.toLowerCase());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            String pokemonName = json.get("name").getAsString();

            // Typy
            List<String> types = new ArrayList<>();
            json.getAsJsonArray("types").forEach(typeElement -> {
                String typeName = typeElement.getAsJsonObject()
                        .getAsJsonObject("type")
                        .get("name").getAsString();
                types.add(typeName);
            });

            // Staty
            int attack = 0, defense = 0, speed = 0, hp = 0;
            for (var statElement : json.getAsJsonArray("stats")) {
                JsonObject statObj = statElement.getAsJsonObject();
                String statName = statObj.getAsJsonObject("stat").get("name").getAsString();
                int baseStat = statObj.get("base_stat").getAsInt();
                switch (statName) {
                    case "attack" -> attack = baseStat;
                    case "defense" -> defense = baseStat;
                    case "speed" -> speed = baseStat;
                    case "hp" -> hp = baseStat;
                }
            }


            double height = json.get("height").getAsDouble();
            double weight = json.get("weight").getAsDouble();
            boolean isLegendary = false; // zatím necháme false
            String spriteUrl = json.getAsJsonObject("sprites")
                    .get("front_default").getAsString();

            return new Pokemon(pokemonName, types, attack, defense, speed, hp,
                    height, weight, isLegendary, spriteUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Pokemon> getAllPokemon(int limit) {
        List<Pokemon> allPokemons = new ArrayList<>();
        try {
            URL listUrl = new URL("https://pokeapi.co/api/v2/pokemon?limit=" + limit + "&offset=0");
            HttpURLConnection conn = (HttpURLConnection) listUrl.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            JsonObject listJson = gson.fromJson(reader, JsonObject.class);

            for (var p : listJson.getAsJsonArray("results")) {
                String name = p.getAsJsonObject().get("name").getAsString();
                Pokemon pokemon = getPokemon(name); // tvoje metoda
                if (pokemon != null) allPokemons.add(pokemon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPokemons;
    }


    public static void main(String[] args) {
        PokeAPIService service = new PokeAPIService();
        Pokemon pikachu = service.getPokemon("pikachu");
        System.out.println("Jméno: " + pikachu.getName());
        System.out.println("Typy: " + pikachu.getTypes());
        System.out.println("Attack: " + pikachu.getAttack());
        System.out.println("Defense: " + pikachu.getDefense());
        System.out.println("Speed: " + pikachu.getSpeed());
        System.out.println("HP: " + pikachu.getHp());
    }
}

