<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommandeDAO.php';

print(CommandeDAO::modifierCommande($_POST['idCommande'], $_POST['numTable'], $_POST['idService']));

?>