<?php 
// step 1: connect to database
// mysqli_connect function has 4 params (host,user name, password,database_name)
$db_con = mysqli_connect("localhost","tp3User","abc123","tp3_mobile");
$response = array();
header('Content-Type: application/json');
if(mysqli_connect_errno())
{
    $response["error"] = TRUE;
    $response["message"] ="Échec de connexion à la base de données";
    echo json_encode($response);
    exit;
}
if(isset($_POST["type"]) && ($_POST["type"]=="signup") && isset($_POST["nom"]) && isset($_POST["prenom"]) && isset($_POST["courriel"])  && isset($_POST["motdepasse"]) && isset($_POST["pays"]) ){
    // signup user
    $name = $_POST["nom"];
	$surname = $_POST["prenom"];
    $email = $_POST["courriel"];
    $password = md5($_POST["motdepasse"]);
	$country = $_POST["pays"];
    //check user email whether its already regsitered
    $checkEmailQuery = "select * from user where courriel = '$email'";
    $result = mysqli_query($db_con,$checkEmailQuery);
    // print_r($result); exit;
    if($result->num_rows>0){
        $response["error"] = TRUE;
        $response["message"] ="Ce courriel existe déjà !";
        echo json_encode($response);
        exit;
    }else{
        $signupQuery = "INSERT INTO user(nom,prenom,courriel,motdepasse,pays) values('$name','$surname','$email','$password','$country')";
        $signupResult = mysqli_query($db_con,$signupQuery);
        if($signupResult){
            // Get Last Inserted ID
            $id = mysqli_insert_id($db_con);
            // Get User By ID
            $userQuery = "SELECT user_id, nom, prenom, courriel, pays FROM user WHERE user_id = ".$id;
            $userResult = mysqli_query($db_con,$userQuery);
            
            $user = mysqli_fetch_assoc($userResult);
            
            $response["error"] = FALSE;
            $response["message"] = "Inscription réussie !";
            $response["user"] = $user;
            echo json_encode($response);
            exit;
        }else{
            $response["error"] = TRUE;
            $response["message"] ="Impossible de créer un nouveau compte ! veuillez réessayer plus tard.";
            echo json_encode($response);
            exit;
        }
        
    }
}else if(isset($_POST["type"]) && ($_POST["type"]=="login") && isset($_POST["email"]) && isset($_POST["password"])){
    //login user
    $email = $_POST["email"];
    $password = md5($_POST["password"]);
    $userQuery = "select user_id, nom, prenom, courriel from user where courriel = '$email' && motdepasse = '$password'";
    $result = mysqli_query($db_con,$userQuery);
    // print_r($result); exit;
    if($result->num_rows==0){
        $response["error"] = TRUE;
        $response["message"] ="Utilisateur introuvable ou authentifiants incorrects.";
        echo json_encode($response);
        exit;
    }else{
        $user = mysqli_fetch_assoc($result);
        $response["error"] = FALSE;
        $response["message"] = "Connexion réussie !";
        $response["user"] = $user;
        echo json_encode($response);
        exit;
    }
}else {
    // Invalid parameters
    $response["error"] = TRUE;
    $response["message"] ="Paramètres invalides";
    echo json_encode($response);
    exit;
}
?>