<?php
class CommandeDAO{

	public static function afficherCommandes($idUtilisateur){
		$sql = "SELECT COMMANDE.* 
		FROM COMMANDE, TABLE_RESTO
		WHERE COMMANDE.NUMTABLE = TABLE_RESTO.NUMTABLE
		AND COMMANDE.IDSERVICE = TABLE_RESTO.IDSERVICE
		AND COMMANDE.DATE_SERVICE = TABLE_RESTO.DATE_SERVICE
		AND COMMANDE.DATE_SERVICE = CURRENT_DATE()
		AND TABLE_RESTO.IDUTILISATEUR = :idUtilisateur;";
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
		$requetePrepa->bindParam(":idUtilisateur", $idUtilisateur);
		$requetePrepa->execute();
        return $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
	}

	public static function afficherCommande($idCommande){
		$sql = "SELECT NUMTABLE, IDSERVICE, DATE_SERVICE 
		FROM COMMANDE
		WHERE IDCOMMANDE = :idCommande";
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
		$requetePrepa->bindParam(":idCommande", $idCommande);
		$requetePrepa->execute();
        return $requetePrepa->fetch(PDO::FETCH_ASSOC);
	}
    
	public static function creerCommande($numTable, $idService){
		try{
			$sql = "INSERT INTO COMMANDE (DATE_SERVICE, NUMTABLE, IDSERVICE, HEURECOMMANDE, ETATCOMMANDE) VALUES (CURRENT_DATE(), :numTable, :idService, CURTIME(), 'non réglée'); " ;
			$requetePrepa = DBConnex::getInstance()->prepare($sql);
            $requetePrepa->bindParam(":numTable" , $numTable);
            $requetePrepa->bindParam(":idService",$idService);
			$requetePrepa->execute();
			$reponse = $requetePrepa->fetch();
		}catch(Exception $e){
			$reponse = "ca marche pas";
		}
		return $reponse;
	}

    public static function modifierCommande($idCommande, $numTable, $idService){
		$sql = "UPDATE COMMANDE SET NUMTABLE = :numTable, IDSERVICE = :idService, HEURECOMMANDE = CURTIME() WHERE IDCOMMANDE = :idCommande; " ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
        $requetePrepa->bindParam(":numTable" , $numTable);
        $requetePrepa->bindParam(":idService",$idService);
		return $requetePrepa->execute();
	}

    public static function supprimerCommande($idCommande){
		$sql = "DELETE FROM COMMANDE WHERE IDCOMMANDE = :idCommande;" ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		return $requetePrepa->execute();
	}
	
    public static function reglerCommande($idCommande){
		$sql = "UPDATE COMMANDE SET ETATCOMMANDE = 'réglée' WHERE IDCOMMANDE = :idCommande; " ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		return $requetePrepa->execute();
	}
}
?>