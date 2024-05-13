<?php

require_once '../model/dao/Param.php';
require_once '../model/dao/DBConnex.php';
require_once '../model/dao/CommandeDAO.php';

print(json_encode(CommandeDAO::afficherCommandes()));
print(json_encode(CommandeDAO::afficherUneCommande($_POST['idCommande'])));

print(CommandeDAO::creerCommande($_POST['dateService'], $_POST['numTable'], $_POST['idService']));
print(CommandeDAO::modifierCommande($_POST['idCommande'], $_POST['dateService'], $_POST['numTable'], $_POST['idService']));
print(CommandeDAO::supprimerCommande($_POST['idCommande']));
print(CommandeDAO::reglerCommande($_POST['idCommande']));