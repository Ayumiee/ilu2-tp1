package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMarche);
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for(int i=0;i<nbEtal;i++) {
				etals[i]=new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			int i;
			int resultat=-1;
			for (i = 0; i < (etals.length); i++) {
				if (!etals[i].isEtalOccupe()) {
					resultat=i;
					break;
				}
			}
			return resultat;
		}

		public Etal[] trouverEtals(String produit) {
			int i;
			int indice = 0;
			Etal[] etalslibre = new Etal[etals.length];
			for (i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalslibre[indice] = etals[i];
					indice++;
				}
			}
			return etalslibre;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			int i;
			for (i = 0; i < etals.length; i++) {
				if ((etals[i].getVendeur()) == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private void afficherMarche() {
			int i;
			int nbEtalVide = 0;
			for (i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				} else {
					nbEtalVide++;
				}
				System.out.println("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
		}

	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		System.out.println(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit +" "+ produit);
		int disponible = marche.trouverEtalLibre();
		marche.utiliserEtal(disponible, vendeur, produit, nbProduit);
		
		return "Le vendeur "+vendeur.getNom()+" vend des "+produit+" "+nbProduit+" à l'étal n°"+(disponible+1);
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] vendeurs = marche.trouverEtals(produit);
		System.out.println("Les vendeurs qui proposent des "+produit+" sont :");
		for (int i=0;i<vendeurs.length;i++) {
			System.out.println("- "+vendeurs[i]);
		}
		return "";
	}
	

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

}