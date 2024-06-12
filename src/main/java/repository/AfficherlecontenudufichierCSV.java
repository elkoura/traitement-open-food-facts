package repository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AfficherlecontenudufichierCSV {

	    public static void main(String[] args) {
	        String cheminFichier = "C://Users//fouad//OneDrive//Bureau//diginamic2024-M02-main//testfichier.csv";
	        lireEtAfficherCSV(cheminFichier);
	    }

	    public static void lireEtAfficherCSV(String cheminFichier) {
	        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
	            String ligne;
	            boolean isFirstLine = true;
	            String[] enTetes = null;

	            while ((ligne = br.readLine()) != null) {
	                String[] champs = ligne.split("\\|");

	                if (isFirstLine) {
	                    enTetes = champs;
	                    System.out.println("En-têtes :");
	                    for (int i = 0; i < enTetes.length; i++) {
	                        System.out.println("Colonne " + i + " : " + enTetes[i]);
	                    }
	                    System.out.println();
	                    isFirstLine = false;
	                } else {
	                    System.out.println("Ligne de données :");
	                    for (int i = 0; i < champs.length; i++) {
	                        System.out.println(enTetes[i] + " : " + champs[i]);
	                    }
	                    System.out.println();
	                }

	                // Limiter l'affichage à quelques lignes de données pour l'exemple
	                if (!isFirstLine && br.lines().count() > 5) {
	                    break;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
