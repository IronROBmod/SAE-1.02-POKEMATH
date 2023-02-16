<POKEMATH>
===========

Développé par <Hocine CHEBOUT> & <Théo CATTANEO> : Groupe A  
Contacts : <hocine.chebout.etu@univ-lille.fr> , <theo.cattaneo.etu@univ-lille.fr>

# Présentation de POKEMATH

<Remis en contexte>
Avec la popularité de l'univers pokémon (jeux , série animé, cartes à jouer, etc...) nous voulions avoir un jeu qui serait a la fois connu de tous : du parfait maître Pokemon en passant par ceux qui connaisse que Pikachu ou dracaufeu.
Nous avons eu donc l'idée de crée un jeu qui aurait comme théme les pokémons.
Ensuite, aprés plusieur analyse, (en particulier celui du TIMMS : Le dernier classement TIMMS, classement international sur l'enseignement des maths), nous apris avec tristesse que la France se place au dernier rang de l'Europe, et 40e dans les pays de l'OCDE, derrière l'Albanie et le Kazakhstan en mathématiques, ILS FALLAIT REMEDIER À ÇA !
C'est en grande partie pour cela que nous avons comme idées de prendre comme "théme pédagogique" les mathématiques, pour etre plus précis les calculs.

<Description Pokemath>
Comme expliqué précédemment, Pokemath est jeu basée sur le calcul, chaque niveau correspond a un théme particulier adapté pour un niveau entre le début et la fin primaire (donc des additions/soustractions aux multiplications/divisions) et référence un type de calcul ( exemple : Niveau 1 basée sur une soustraction), le tout mis en situation via un combat pokémon : Et oui, cela ressemble exactement aux probleme de mathématiques qu'on avait lors de notre enfance du style "la baignoire qui fuit" ou encore "Michel prendre le train à 14h45...".
Pour encore plus d'amusement, des modes de jeu (pb de lancement pour le moment) 
Des captures d'écran illustrant le fonctionnement du logiciel sont proposées dans le répertoire shots.


# Utilisation de Pokemath

Afin d'utiliser Pokemath, il suffit de taper les commandes suivantes dans un terminal :

```
./compile.sh
```
Permet la compilation des fichiers présents dans 'src' et création des fichiers '.class' dans 'classes'

```
./run.sh Pokemath
```
Permet le lancement du jeu

# Activer le mode duel

Un mode duel a été crée mais nous n'avons pas réussi à le faire se lancer depuis le menu principal
Pour le faire se lancer automatiquement après la saisie du nom :
 -> décommentez "//modeDuel(idxJoueur);" dans l'algrithme principale (Pokemath.java - ligne 717)
