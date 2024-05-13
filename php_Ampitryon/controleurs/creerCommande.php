<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommandeDAO.php';

print(CommandeDAO::creerCommande($_POST['numTable'], $_POST['idService']));

?>