<?php

 $message="message";
 $summaryText="Text";
 $image="http://d27bygd3qv5fha.cloudfront.net/Mar-17-2017/58cbb44034a4750d088ab70a/The-machines-don-t-lie-2017-Mar-17-15-39-59-general.jpeg";
 $value="3";
 
$path_to_fcm="https://fcm.googleapis.com/fcm/send";
$server_key="--add-api-key--";
$to="/topics/news";

$header=array("Authorization:key=".$server_key,'Content-Type:application/json');
 $load = array(
    "to" =>"$to",
	'data'=>array('message'=>"$message",'summaryText'=>"$summaryText",'picture'=>"$image","value"=>"$value","articleId"=>'58e47b26f2d6da2259ce299b')
);

 $payload=json_encode($load);

$curl_session=curl_init();
curl_setopt($curl_session,CURLOPT_URL,$path_to_fcm);
curl_setopt($curl_session,CURLOPT_POST,true);
curl_setopt($curl_session,CURLOPT_HTTPHEADER,$header);
curl_setopt($curl_session,CURLOPT_RETURNTRANSFER,true);
curl_setopt($curl_session,CURLOPT_SSL_VERIFYPEER,false);
curl_setopt($curl_session,CURLOPT_IPRESOLVE,CURL_IPRESOLVE_V4);
curl_setopt($curl_session,CURLOPT_POSTFIELDS,$payload);
echo $result=curl_exec($curl_session);
echo $httpcode = curl_getinfo($curl_session, CURLINFO_HTTP_CODE);

curl_close($curl_session);

?>