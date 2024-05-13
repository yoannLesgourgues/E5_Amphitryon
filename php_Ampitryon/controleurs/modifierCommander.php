<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommanderDAO.php';

print(CommanderDAO::modifierCommander($_POST['idPlat'], $_POST['idCommande'], $_POST['selectedPlatId'], $_POST['etatPlat'], $_POST['infosComp'], $_POST['quantite']));
?>