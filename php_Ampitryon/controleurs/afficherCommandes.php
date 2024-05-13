<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommandeDAO.php';

print(json_encode(CommandeDAO::afficherCommandes($_POST['idUtilisateur'])));

?>