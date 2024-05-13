<?php

class TableRestoDAO{

    public static function afficherTablesServeur($idUtilisateur){
        $requetePrepa = DBConnex::getInstance()->prepare("SELECT DISTINCT(NUMTABLE) FROM TABLE_RESTO WHERE DATE_SERVICE = CURRENT_DATE() AND IDUTILISATEUR = :idUtilisateur");
		$requetePrepa->bindParam(":idUtilisateur", $idUtilisateur);
		$requetePrepa->execute();
        return $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
    }
}