#Serveur FTP Basique

#Auteurs
Lucas Moura de Oliveira
Eliott Bricout

13/02/2017

#Introduction
Ce logiciel est une implémentation d'un serveur FTP qui respecte (en partie du moins) la RFC 959 (https://tools.ietf.org/html/rfc959).

#Architecture

## Interfaces

## Classes abstraites

## Classes polymorphiques

## Try/Catch

#Code samples

## Pattern command

L'application reconnait un certain nombre de commandes (20 à l'heure actuelle) et pour simplifier la redirection des commandes utilisateurs vers les classes traitant ces commandes, une implémentation du pattern Commande a été mise en place au travers des classes suivantes : 

### CommandPool.java

La classe CommandPool.java contient la liste des commandes reconnues par l'applications, et s'occupe de rediriger le traitement en fonction du code envoyé par l'utilisateur. Plutôt que d'écrire un énorme switch pour déterminer la bonne commande, CommandPool possède la méthode handle() suivante : 

  public FtpReply handle(String command, String data, UserState userState){
      //On récupère la commande correspondante dans la map
	  Command c = commands.get(command);
	  //Si celle ci n'est pas null (la commande existe), on l'exécute et on retourne sa réponse
	  if(c != null){
	      return c.execute(data, userState);
	  }
	  //Sinon si la commande n'existe pas, on retourne un code d'erreur 404
	  return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_404_NOT_FOUND, "Commande inconnue");
  }
  
### Command.java

L'interface Command définit une seule signature de méthode, execute(), qui est appellée par CommandPool.handle(). Les commandes de l'applications implémentent cette interface.

  /**
  * Exécute une commande et retourne un code FTP informant de la bonne exécution (ou non) de la commande.
  * 
  * @param data les paramètres de la commande, peuvent être null
  * @param userState les informations de la session utilisateur
  * 
  * @return un code de retour au format texte
  */
  public abstract FtpReply execute(String data, UserState userState);

## Singleton

## FtpReply

## DataCommandExecutor

## LoggedCommand



