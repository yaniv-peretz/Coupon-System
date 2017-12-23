var client = {};

(function () {
    var redirectUrl = "workbench.html";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {                
                //sessoion auth set up,
                var sessionClient = JSON.parse(this.responseText);                
                sessionClient.type = sessionClient.type.toLowerCase(sessionClient.type);
                var location =  window.location.toString();

                var locationMatchSessionAuthType = location.includes(sessionClient.type);
                if(locationMatchSessionAuthType){
                    // session, and location match => continue
                    client = this.responseText;

                }else{
                    //session auth set, but for a different type
                    window.location.replace(redirectUrl);
                }

            } else if (this.status == 401) {
                //sessoion auth forbiden
                if (document.cookie != null) {
                    //user has cookie, trying to connect from cookie
                        loginFromCookie();
                } else {
                    //cookie not exists, redirect
                    alert("here3")
                    window.location.replace(redirectUrl);
                }

            } else {
                //generally somthing is wrong, redirect
                alert("something went wrong");
                window.location.replace(redirectUrl);
            }
        }
    };

    var url = "api/login";
    xhttp.open("GET", url, true);
    xhttp.send();
})();


function loginFromCookie() {

    //compare location to cookie auth type
    var location = window.location.toString()
    var locationMatchCookieAuthType = location.includes($.cookie("type"));
    if(!locationMatchCookieAuthType){
        //redirect if cookie client type is diffrent from the location
        window.location.replace(redirectUrl);        
    }

    var client = {
        "name": $.cookie("name"),
        "password": $.cookie("password"),
        "type": $.cookie("type")
    };

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {                
                client = {
                    "name": $.cookie("name"),
                    "password": $.cookie("password"),
                    "type": $.cookie("type")
                };
                
            } else {
                //cookie doesn't include valid name & password
                var url = "workbench.html";
                window.location.replace(url);
                
            }
        }
    }    
      
    var url = "api/login";
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(client));
}