<?php

class ServiceDAO{

    public static function afficherServices(){
        $requetePrepa =DBConnex::getInstance()->prepare("SELECT * FROM SERVICE");
        $requetePrepa->execute();
        return $requetePrepa->fetchAll(PDO::FETCH_ASSOC);
    }
}