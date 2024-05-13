<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/ServiceDAO.php';

print(json_encode(ServiceDAO::afficherServices()));

?>