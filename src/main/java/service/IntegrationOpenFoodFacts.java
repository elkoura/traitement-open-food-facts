import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

public class IntegrationOpenFoodFacts {
	private EntityManager entityManager;

    private Categorie categorie;
    private Marque marque;
    private Produit produit;
    private Ingredient ingredient;
    private Allergene allergene;
    private Additif additif;

    public IntegrationOpenFoodFacts(EntityManager entityManager, Categorie categorie, Marque marque, Produit produit, Ingredient ingredient,
            Allergene allergene, Additif additif) {
    	this.entityManager=entityManager;
        this.categorie = categorie;
        this.marque = marque;
        this.produit = produit;
        this.ingredient = ingredient;
        this.allergene = allergene;
        this.additif = additif;
    }

    public void integrerProduits(String cheminFichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] attributs = ligne.split("\\|");

                String libelleCategorie = attributs[0];
                String libelleMarque = attributs[1];
                String nomProduit = attributs[2];
                String scoreNutritionnel = attributs[3];
                String[] ingredientsArray = attributs[4].split(",");
                Integer energie100g = parseInteger(attributs[5]);
                Double graisse100g = parseDouble(attributs[6]);
                Double sucres100g = parseDouble(attributs[7]);
                Double fibres100g = parseDouble(attributs[8]);
                Double proteines100g = parseDouble(attributs[9]);
                Double sel100g = parseDouble(attributs[10]);
                Double vitA100g = parseDouble(attributs[11]);
                Double vitD100g = parseDouble(attributs[12]);
                Double vitE100g = parseDouble(attributs[13]);
                Double vitK100g = parseDouble(attributs[14]);
                Double vitC100g = parseDouble(attributs[15]);
                Double vitB1100g = parseDouble(attributs[16]);
                Double vitB2100g = parseDouble(attributs[17]);
                Double vitPP100g = parseDouble(attributs[18]);
                Double vitB6100g = parseDouble(attributs[19]);
                Double vitB9100g = parseDouble(attributs[20]);
                Double vitB12100g = parseDouble(attributs[21]);
                Double calcium100g = parseDouble(attributs[22]);
                Double magnesium100g = parseDouble(attributs[23]);
                Double iron100g = parseDouble(attributs[24]);
                Double betaCarotene100g = parseDouble(attributs[25]);
                Boolean presenceHuilePalme = parseBoolean(attributs[26]);
                String[] allergenesArray = attributs[27].split(",");
                String[] additifsArray = attributs[28].split(",");

                Categorie categorie = findOrCreateCategorie(libelleCategorie);
                Marque marque = findOrCreateMarque(libelleMarque);

                List<Ingredient> ingredients = findOrCreateIngredients(ingredientsArray);
                List<Allergene> allergenes = findOrCreateAllergenes(allergenesArray);
                List<Additif> additifs = findOrCreateAdditifs(additifsArray);

                Produit produit = new Produit();
                produit.setCategorie(categorie);
                produit.setMarque(marque);
                produit.setNom(nomProduit);
                produit.setScoreNutritionnel(scoreNutritionnel);
                produit.setEnergie100g(energie100g);
                produit.setGraisse100g(graisse100g);
                produit.setSucres100g(sucres100g);
                produit.setFibres100g(fibres100g);
                produit.setProteines100g(proteines100g);
                produit.setSel100g(sel100g);
                produit.setVitA100g(vitA100g);
                produit.setVitD100g(vitD100g);
                produit.setVitE100g(vitE100g);
                produit.setVitK100g(vitK100g);
                produit.setVitC100g(vitC100g);
                produit.setVitB1100g(vitB1100g);
                produit.setVitB2100g(vitB2100g);
                produit.setVitPP100g(vitPP100g);
                produit.setVitB6100g(vitB6100g);
                produit.setVitB9100g(vitB9100g);
                produit.setVitB12100g(vitB12100g);
                produit.setCalcium100g(calcium100g);
                produit.setMagnesium100g(magnesium100g);
                produit.setIron100g(iron100g);
                produit.setBetaCarotene100g(betaCarotene100g);
                produit.setPresenceHuilePalme(presenceHuilePalme);
                produit.setIngredients(ingredients);
                produit.setAllergenes(allergenes);
                produit.setAdditifs(additifs);

                produit.save(produit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        return "1".equals(value);
    }

    private Categorie findOrCreateCategorie(String libelle) {
        Optional<Categorie> categorieOpt = Categorie.findByLibelle(entityManager, libelle);
        if (categorieOpt.isPresent()) {
            return categorieOpt.get();
        } else {
            Categorie categorie = new Categorie(libelle);
            return Categorie.save(entityManager, categorie);
        }
    }

    private Marque findOrCreateMarque(String libelle) {
        Optional<Marque> marqueOpt = marque.findByLibelle(libelle);
        if (marqueOpt.isPresent()) {
            return marqueOpt.get();
        } else {
            Marque marque = new Marque(libelle);
            return marque.save(marque);
        }
    }

    private List<Ingredient> findOrCreateIngredients(String[] libelles) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String libelle : libelles) {
            Optional<Ingredient> ingredientOpt = ingredient.findByLibelle(libelle);
            if (ingredientOpt.isPresent()) {
                ingredients.add(ingredientOpt.get());
            } else {
                Ingredient ingredient = new Ingredient(libelle);
                ingredients.add(ingredient.save(ingredient));
            }
        }
        return ingredients;
    }

    private List<Allergene> findOrCreateAllergenes(String[] libelles) {
        List<Allergene> allergenes = new ArrayList<>();
        for (String libelle : libelles) {
            Optional<Allergene> allergeneOpt = allergene.findByLibelle(entityManager,libelle);
            if (allergeneOpt.isPresent()) {
                allergenes.add(allergeneOpt.get());
            } else {
                Allergene allergene = new Allergene(libelle);
                allergenes.add(allergene.save(allergene));
            }
        }
        return allergenes;
    }

    private List<Additif> findOrCreateAdditifs(String[] libelles) {
        List<Additif> additifs = new ArrayList<>();
        for (String libelle : libelles) {
            Optional<Additif> additifOpt = additif.findByLibelle(libelle);
            if (additifOpt.isPresent()) {
                additifs.add(additifOpt.get());
            } else {
                Additif additif = new Additif(libelle);
                additifs.add(additif.save(additif));
            }
        }
        return additifs;
    }
}

