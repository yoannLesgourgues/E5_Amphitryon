<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/TableRestoDAO.php';

print(json_encode(TableRestoDAO::afficherTablesServeur($_POST['idUtilisateur'])));

?>