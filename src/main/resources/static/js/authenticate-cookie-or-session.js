var client = {};

{
    let loginFromSession = () => {
        return new Promise((success, fail) => {
            
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = () => {
                if (this.readyState == 4) {
                    if (this.status == 200) {
                        //compare session auth to user loction (page)
                        let sessionClient = JSON.parse(this.responseText);
                        sessionClient.type = sessionClient.type.toLowerCase(sessionClient.type);
                        let location = window.location.toString();
                        let locationMatchSessionAuthType = location.includes(sessionClient.type);
                        
                        if (locationMatchSessionAuthType) {
                            //session auth math location
                            success(this.responseText);
                            return;
                        } else {
                            //user in differnet page
                            fail();
                        }
                    } else {
                        //status !200
                        fail();
                    }
                }
            };

            let url = "api/login";
            xhttp.open("GET", url, true);
            xhttp.send();
        });

    }

    let loginFromCookie = () => {
        return new Promise((success, fail) => {
            if (document.cookie != null) {
                //cookie exists - compare cookie auth type to user location
                let location = window.location.toString()
                let locationMatchCookieAuthType = location.includes($.cookie("type"));

                if (locationMatchCookieAuthType) {
                    //location math cookie auth type
                    var tempClient = {
                        name: $.cookie("name"),
                        password: $.cookie("password"),
                        type: $.cookie("type")
                    };

                    let xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = () => {
                        if (this.readyState == 4) {
                            if (this.status == 200) {
                                success(tempClient);
                                return;
                            } else {
                                fail();
                            }
                        }
                    }

                    xhttp.open("POST", url, true);
                    xhttp.setRequestHeader("Content-type", "application/json");
                    xhttp.send(JSON.stringify(tempClient));

                } else {
                    //cookie auth type not mathing page location
                    fail();
                }

            } else {
                //there is no cookie setup
                fail();
            }
        });
    }

    /*  ##############
        try to login
        ############# */
    loginFromSession()
        .then((sessionLoggedIn) => {
            client = JSON.parse(sessionLoggedIn);
        })
        .catch(() => {
            loginFromCookie()
                .then((cookieLoggin) => {
                    client = cookieLoggin;
                })
                .catch(() => {
                    let redirectUrl = "management-console.html";
                    window.location.replace(redirectUrl);
                })
        });

}