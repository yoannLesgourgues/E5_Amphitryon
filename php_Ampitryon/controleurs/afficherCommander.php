<?php
require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommanderDAO.php';

print(json_encode(CommanderDAO::afficherCommander($_POST['idCommande'])));
?>