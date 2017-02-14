#Serveur FTP Basique

#Auteurs
Lucas Moura de Oliveira
Eliott Bricout

13/02/2017


#Introduction

Ce logiciel est une impl�mentation d'un serveur FTP qui respecte (en partie du moins) la RFC 959 (https://tools.ietf.org/html/rfc959).


#Execution 

java -jar ftpserver.jar [FTP_PORT_COMMAND] [FTP_PORT_FILE] [path vers le dossier root]
exemple : 
java -jar ftpserver.jar 2021 2022 ./root

Ici le dossier root se cr�era automatiquement � c�t� du jar mais vous pouvez choisir un dossier autre dossier.

Il y a aussi un fichier de configuration (configuration.properties) � cot� du jar avec  par exemple:

FTP_PORT_FILE=2020
FTP_PORT_COMMAND=2021
ROOT=C:/Users/brico/Documents/GitHub/FTPServer/root

Qui permet de lancer directement le jar sans argument 
puisqu'ils seront r�cup�r�s automatiquement dans ce fichier.


#Architecture

## Interfaces

- Command.java

- DataCommandExecutor.java

## Classes abstraites

- LoggedCommand.java

## Classes polymorphiques

Toutes les classes suffix�es par Command impl�mentent l'interface Command, certains d'entre elles �tendent la classe LoggedCommand (les commandes qui n�cessitent que l'utilisateur soit connect�). Certaines commandes n�cessitent en outre de communiquer par le biais de la socket de donn�es : ces commandes impl�mentent en plus l'interface DataCommandExecutor

### Commandes non-connect�es : 

### Commandes connect�es :

## Try/Catch

#Code samples

## Pattern command

L'application reconnait un certain nombre de commandes (20 � l'heure actuelle) et pour simplifier la redirection des commandes utilisateurs vers les classes traitant ces commandes, une impl�mentation du pattern Commande a �t� mise en place au travers des classes suivantes : 

### CommandPool.java

La classe CommandPool.java contient la liste des commandes reconnues par l'applications, et s'occupe de rediriger le traitement en fonction du code envoy� par l'utilisateur. Plut�t que d'�crire un �norme switch pour d�terminer la bonne commande, CommandPool poss�de la m�thode handle() suivante : 

  public FtpReply handle(String command, String data, UserState userState){
      //On r�cup�re la commande correspondante dans la map
	  Command c = commands.get(command);
	  //Si celle ci n'est pas null (la commande existe), on l'ex�cute et on retourne sa r�ponse
	  if(c != null){
	      return c.execute(data, userState);
	  }
	  //Sinon si la commande n'existe pas, on retourne un code d'erreur 404
	  return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_404_NOT_FOUND, "Commande inconnue");
  }
  
### Command.java

L'interface Command d�finit une seule signature de m�thode, execute(), qui est appell�e par CommandPool.handle(). Les commandes de l'applications impl�mentent cette interface.

  /**
  * Ex�cute une commande et retourne un code FTP informant de la bonne ex�cution (ou non) de la commande.
  * 
  * @param data les param�tres de la commande, peuvent �tre null
  * @param userState les informations de la session utilisateur
  * 
  * @return un code de retour au format texte
  */
  public abstract FtpReply execute(String data, UserState userState);

## FtpReply

## DataCommandExecutor

## LoggedCommand



