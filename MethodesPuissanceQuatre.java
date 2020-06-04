import java.util.Scanner;

public class MethodesPuissanceQuatre {
	static Scanner saisie = new Scanner(System.in);

	// * 1. Les différents Puissance 4 *\\

	// Le Puissance 4 à deux joueurs humains
	public static void j1VsJ2(int column, int line, int nbToWin, String player1, String player2) {
		boolean isIa = false; // indique à la fonction shoot que c'est un joueur

		// Création du Plateau du jeu
		char[][] board = MethodesPuissanceQuatre.gameBoardCreation(column, line);

		// On demande qui veut commencer
		int choice = 0;

		System.out.println("Qui commence la partie ? 1 ou 2 ?");
		choice = enterANumber(1, 2);

		// Affichage de qui commence
		boolean isPlayerOne;

		if (choice == 1) {
			System.out.println(player1 + " tu commences !");
			isPlayerOne = true;
			
		} else {
			System.out.println(player2 + " tu commences !");
			isPlayerOne = false;
		}

		System.out.println();

		boolean win = false; // boolean vrai si un joueur gagne
		int nbTurns = 1; // compte le nombre de tour pour l'affichage
		do {
			// Affichage du numero du tour
			System.out.println("Tour : " + nbTurns + " Le plateau : ");
			nbTurns++;

			// Affichage plateau
			display(board, column, line);

			// Affichage de qui doit jouer
			turnDisplay(isPlayerOne, player1, player2);

			// Le joueur actuel place son jeton
			// recupere coordonne du tir pour checkWin
			int coordinates[] = shoot(board, isPlayerOne, column, line, isIa);
			int positionC = coordinates[0];
			int positionL = coordinates[1];

			// Verifie si gagnant
			if (checkWin(board, isPlayerOne, nbToWin, column, line, positionC, positionL)) {
				display(board, column, line);
				endOfGame(isPlayerOne, player1, player2);
				win = true;
			}
			isPlayerOne = !isPlayerOne; // inverser à chaque tour le joueur qui joue
		} while (!win && nbTurns <= column * line);

		// Si pas de gagnant alors que le plateau est rempli, égalité
		if (!win) {
			display(board, column, line);
			System.out.println(endOfGame());
		}
		// Choisir si recommence une partiE
		int choiceReplay = replay();
		if (choiceReplay == 1)
			j1VsJ2(column, line, nbToWin, player1, player2);
		else if (choiceReplay == 3)
			Menu.menu();
		else
			System.out.println("Au revoir !");
	}

	// Le Puissance 4 joueur humain contre l'IA
	public static void j1VsIa(int column, int line, int nbToWin, String player1) {
		boolean isIa = false; // faux quand l'humain joue, vrai quand Ia joue
		// Creation du Plateau du jeu
		char[][] board = MethodesPuissanceQuatre.gameBoardCreation(column, line);

		boolean isPlayerOne = true; // vrai quand le Joueur 1 joue

		boolean win = false; // boolean vrai si un joueur gagne
		int nbTurns = 1; // compte le nombre de tour pour l'affichage
		do {
			// Affichage du numero du tour
			System.out.println("Tour : " + nbTurns + " Le plateau : ");
			nbTurns++;

			// Affichage plateau
			display(board, column, line);

			// Affichage de qui doit jouer
			turnDisplay(isPlayerOne, player1, "Ia");

			// Le joueur actuel place son jeton
			// puis on recupere les coordonnées du tir pour checkWin
			int coordinates[] = shoot(board, isPlayerOne, column, line, isIa);
			int positionC = coordinates[0];
			int positionL = coordinates[1];

			// Verifie si gagnant
			if (checkWin(board, isPlayerOne, nbToWin, column, line, positionC, positionL)) {
				display(board, column, line);
				endOfGame(isPlayerOne, player1, "Ia");
				win = true;
			}
			isPlayerOne = !isPlayerOne; // inverser à chaque tour le joueur qui joue
			isIa = !isIa; // l'IA joue une fois sur deux
		} while (!win && nbTurns <= column * line);

		// Si pas de gagnant alors que le plateau est rempli, égalité
		if (!win) {
			display(board, column, line);
			System.out.println(endOfGame());
		}
		// Choisir si recommence une partie
		int choiceReplay = replay();
		if (choiceReplay == 1)
			j1VsIa(column, line, nbToWin, player1);
		else if (choiceReplay == 3)
			Menu.menu();
		else
			System.out.println("Au revoir !");
	}

	// * 2. Procédures permettant le fonctionnement du Puissance 4 *\\
	
	// Retourne un tableau contenant les paramètres personnalisés du Jeu
	public static int [] personalize () {
		int [] config = new int [3];
		
		// Saisie des parametres personnalisés du Jeu
		System.out.println("Saisissez le nombre de colonne du plateau, minimum 3, maximum 30");
		config[0] = enterANumber(3, 30);
		System.out.println("Saisissez le nombre de ligne du plateau, minimum 3, maximum 30");
		config[1] = enterANumber(3, 30);
		
		do {
			if (config [0] <= 5 && config[1] <= 5) {
				if (config[0] > config[1]) {
					System.out.println("Veuillez saisir un nombre de jeton à aligner pour gagner entre 3 et " + config[0]);
					config[2] = enterANumber(3, config[0]);
				}
				else {
					System.out.println("Veuillez saisir un nombre de jeton à aligner pour gagner entre 3 et " + config[1]);
					config[2] = enterANumber(3, config[1]);
				}
			}
			
			else {
				System.out.println("Saisissez le nombre de jeton à aligner pour gagner, minimum 3, maximum 5");
				config[2] = enterANumber(3, 5);
			}
		} while (config[2] > config[0] && config[2] > config[1]);
		
		return config;
	}

	// Méthode vérifiant si la saisie du nombre est possiblle puis le retourne quand
	// il est valide
	public static int enterANumber(int min, int max) {
		int choice = 0;
		int i = 0;

		do {
			if (i > 0)
				System.out.print("\nSaisissez un chiffre entre " + min + " et " + max);
			try {
				choice = Integer.parseInt(saisie.nextLine());
			} catch (NumberFormatException e) {
				System.out.println();
			}
			i++;
		} while (choice < min || choice > max);

		System.out.println();
		return choice;
	}

	// Fonction permettant de créer un tableau de char contenant des . partout puis
	// le retourne
	public static char[][] gameBoardCreation(int column, int line) {
		char[][] board = new char[column][line];

		for (int i = 0; i < column; i++)
			for (int y = 0; y < line; y++)
				board[i][y] = '.';

		return board;
	}

	// Fonction permettant de saisir le string du nom du joueur puis le retourne
	public static String playerName(boolean isPlayerOne) {
		int playerNum;
		String player;

		if (isPlayerOne) {
			playerNum = 1;
		} else {
			playerNum = 2;
		}

		System.out.println("Quel est le nom du joueur " + playerNum + " ?");
		player = saisie.nextLine();
		System.out.println("Bonjour " + player + " !");
		System.out.println();

		return player;
	}

	// Méthode affichant le plateau du jeu
	public static void display(char[][] board, int column, int line) {
		for (int i = 0; i < column + 2 + 2 * column; i++) {
			System.out.print("-");
		}
		System.out.println();

		for (int y = 0; y < line; y++) {
			System.out.print("|");
			for (int x = 0; x < column; x++) {
				System.out.print(" " + board[x][y] + " ");
			}
			System.out.print("|");
			System.out.println();
		}
		for (int i = 0; i < column + 2 + 2 * column; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i = 1; i < column + 1; i++) {
			if (i < 10)
				System.out.print("  " + i);
			else
				System.out.print(" " + i);
		}
		System.out.println();
	}

	// Affiche qui doit jouer
	public static void turnDisplay(boolean isPlayerOne, String player1, String player2) {
		if (isPlayerOne) {
			System.out.println(player1 + " à toi de jouer !");

			System.out.println();
		} else {
			System.out.println(player2 + " à toi de jouer !");
			System.out.println();
		}
	}

	// Méthode affichant la fin du jeu avec le nom du gagnant
	public static void endOfGame(boolean isPlayerOne, String player1, String player2) {
		System.out.println("\n" + "*****FIN DE LA PARTIE******");
		System.out.println("**************************");
		System.out.println();
		if (isPlayerOne)
			System.out.println("***    " + player1 + " a gagné !" + "    ***");
		else
			System.out.println("***    " + player2 + " a gagné !" + "    ***");
	}

	// Méthode affichant la fin du jeu lors d'une égalité
	public static String endOfGame() {
		String equality = "\n" + "*****FIN DE LA PARTIE******\n" + "**************************\n"
				+ "***********EGALITE***********\n";
		return equality;
	}

	// Retourne le choix du replay
	public static int replay() {
		int choiceReplay = 0;

		System.out.println();
		System.out.println("Voulez vous rejouer ? 1 : oui, 2 : non, 3 : retourner au Menu");
		choiceReplay = enterANumber(1, 3);

		return choiceReplay;
	}

	// Procédure permettant de saisir le tir du joueur puis retourne les coordonnées
	// du tir
	public static int[] shoot(char[][] board, boolean player, int column, int line, boolean isIa) {
		int columnPosition = -1; // variable qui va prendre la position du tir

		System.out.println("Saisissez la position où vous voulez tirer, entre 1 et " + column);

		// Verification que le tir est possible
		boolean isShoot = false;
		do {
			if (isIa)
				columnPosition = (int) (Math.random() * column) + 1;
			else
				columnPosition = enterANumber(1, column);
			if (board[columnPosition - 1][0] != '.') { // si depasse le plateau
				System.out.println("La colonne est deja pleine, recommencez");
			} else {
				isShoot = true; // jeton placé
			}
		} while (!isShoot); // Tant que le jeton n'est pas placé

		// Placement du jeton
		int linePosition = line - 1;

		while (board[columnPosition - 1][linePosition] != '.') { // Tant que la case est pleine
			linePosition--; // Monte d'un étage en partant du bas
		}
		if (player) {
			board[columnPosition - 1][linePosition] = 'X';
		} else {
			board[columnPosition - 1][linePosition] = 'O';
		}

		// Renvoyer la position du tir pour checkWin
		int[] coordinates;
		coordinates = new int[2];

		coordinates[0] = columnPosition;
		coordinates[1] = linePosition;

		return coordinates;
	}

	public static boolean checkWin(char[][] board, boolean isPlayerOne, int nbToWin, int column, int line, int columnPosition,
			int linePosition) {

		int sum;
		int x, y; // check x horizontal, y vertical
		char symbol; // voir si le même symbole se répète

		if (isPlayerOne)
			symbol = 'X';
		else
			symbol = 'O';
		

		// Test Horizontal
		x = columnPosition - 1;
		y = linePosition;
		sum = -1;

		while (x >= 0 && board[x][y] == symbol) {
			x--;
			sum++;
		}
		x = columnPosition - 1;
		while (x < column && board[x][y] == symbol) {
			x++;
			sum++;
		}
		if (sum >= nbToWin)
			return true;

		// Test Vertical
		x = columnPosition - 1;
		y = linePosition;
		sum = 0;
		while (y >= 0 && board[x][y] == symbol) {
			y--;
			sum++;
			System.out.println(sum);
		}

		x = columnPosition - 1;
		y = linePosition;
		sum = 0;
		while (y < line && board[x][y] == symbol) {
			y++;
			sum++;
		}

		if (sum >= nbToWin)
			return true;

		// Test Diagonale gauche
		x = columnPosition - 1;
		y = linePosition;
		sum = -1;
		while (y >= 0 && x < column && board[x][y] == symbol) {
			x++;
			y--;
			sum++;
		}
		x = columnPosition - 1;
		y = linePosition;
		while (y < line && x >= 0 && board[x][y] == symbol) {
			x--;
			y++;
			sum++;
		}
		if (sum >= nbToWin)
			return true;

		// Test Diagonale droite
		x = columnPosition - 1;
		y = linePosition;
		sum = -1;
		while (y >= 0 && x >= 0 && board[x][y] == symbol) {
			y--;
			x--;
			sum++;
		}
		x = columnPosition - 1;
		y = linePosition;
		while (y < line && x < column && x >= 0 && board[x][y] == symbol) {
			x++;
			y++;
			sum++;
		}

		if (sum >= nbToWin)
			return true;
		else
			return false;
	}
}