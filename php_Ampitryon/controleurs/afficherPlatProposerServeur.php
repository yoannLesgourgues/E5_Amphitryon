<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/ProposerPlatDAO.php';

print(json_encode(ProposerPlatDAO::afficherPlatProposerServeur($_POST['idService'], $_POST['date_service'])));

?>