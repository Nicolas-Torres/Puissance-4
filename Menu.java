// Le Menu du Jeu

public class Menu {
	public static void menu() {
		String Newligne = System.getProperty("line.separator");

		// Noms des joueurs
		String player1;
		String player2;

		// Configuration pour les modes personnalisés
		int [] config = new int [3];
		
		// Demande le mode de jeu puis le lance via le switch
		System.out.println("Bienvenue dans le Puissance 4" + Newligne + Newligne + "Choisissez le mode de jeu."
				+ Newligne + Newligne + "1. 1vs1 classique" + Newligne + "2. 1vs1 personnalisé" + Newligne
				+ "3. 1vsIA classique" + Newligne + "4. 1vsIA personnalisé");
		int choice = MethodesPuissanceQuatre.enterANumber(1, 4);

		switch (choice) {
		case 1:
			// Saisie noms Joueurs puis affiche Bonjour _Joueur_ !
			player1 = MethodesPuissanceQuatre.playerName(true);
			player2 = MethodesPuissanceQuatre.playerName(false);
			// Lance le puissance 4 avec le mode de jeu choisie (ici 1 : 1vs1)
			MethodesPuissanceQuatre.j1VsJ2(Constant.DEFAULT_COLUMN, Constant.DEFAULT_LINE, Constant.DEFAULT_NB_TO_WIN,
					player1, player2);
			break;
			
		case 2:
			player1 = MethodesPuissanceQuatre.playerName(true);
			player2 = MethodesPuissanceQuatre.playerName(false);
			config = MethodesPuissanceQuatre.personalize();
			// Puissance 4 à 2 joueurs : nombre de colonne, ligne, jeton personnalisé
			MethodesPuissanceQuatre.j1VsJ2(config[0], config[1], config[2], player1, player2);
			break;
		case 3:
			player1 = MethodesPuissanceQuatre.playerName(true);
			MethodesPuissanceQuatre.j1VsIa(Constant.DEFAULT_COLUMN, Constant.DEFAULT_LINE, Constant.DEFAULT_NB_TO_WIN, 
					player1);
		case 4:
			player1 = MethodesPuissanceQuatre.playerName(true);
			player2 = "Ia";
			config = MethodesPuissanceQuatre.personalize();
			// Puissance 4 1VSIA : nombre de colonne, ligne, jeton personnalisé
			MethodesPuissanceQuatre.j1VsIa(config[0], config[1], config[2], player1);
			break;
		}
	}
}