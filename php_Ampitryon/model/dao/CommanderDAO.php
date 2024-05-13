<?php
class CommanderDAO {
    
	public static function modifierCommander($idPlat, $idCommande, $selectedPlatId, $etatPlat, $infosComp, $quantite){
		$sql = "UPDATE COMMANDER 
		SET IDPLAT = :selectedPlatId, ETATPLAT = :etatPlat, INFOSCOMPLEMENTAIRES = :infosComp, QUANTITE = :quantite 
		WHERE IDPLAT = :idPlat 
		AND IDCOMMANDE = :idCommande; ";
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
		$requetePrepa->bindParam(":idPlat", $idPlat);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		$requetePrepa->bindParam(":selectedPlatId", $selectedPlatId);
        $requetePrepa->bindParam(":etatPlat", $etatPlat);
		$requetePrepa->bindParam(":infosComp" , $infosComp);
		$requetePrepa->bindParam(":quantite",$quantite);
		return $requetePrepa->execute();
	}

	public static function afficherCommander($idCommande){
		$sql = " SELECT COMMANDER.*, PLAT.NOMPLAT FROM COMMANDER, PLAT WHERE COMMANDER.IDPLAT = PLAT.IDPLAT AND COMMANDER.IDCOMMANDE = :idCommande;" ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		$requetePrepa->execute();
		return $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
	}

	public static function ajouterCommander($idPlat, $idCommande, $infosComp, $quantite){
		$sql = "INSERT INTO COMMANDER (IDPLAT, IDCOMMANDE, ETATPLAT, INFOSCOMPLEMENTAIRES, QUANTITE) VALUES (:idPlat, :idCommande, 'commandé', :infosComp, :quantite); " ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
		$requetePrepa->bindParam(":idPlat", $idPlat);
		$requetePrepa->bindParam(":idCommande",$idCommande);
		$requetePrepa->bindParam(":infosComp" , $infosComp);
		$requetePrepa->bindParam(":quantite",$quantite);
		return $requetePrepa->execute();
	}

	public static function supprimerCommander($idPlat, $idCommande){
		$sql = "DELETE FROM COMMANDER WHERE IDCOMMANDE = :idCommande AND IDPLAT = :idPlat;" ;
		$requetePrepa = DBConnex::getInstance()->prepare($sql);
        $requetePrepa->bindParam(":idCommande", $idCommande);
		$requetePrepa->bindParam(":idPlat", $idPlat);
		return $requetePrepa->execute();
	}
}

?>