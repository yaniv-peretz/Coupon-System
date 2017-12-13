var client = {};

(function () {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {

            if (this.status == 200) {
                client = this.responseText;

            } else if (this.status == 401) {
                var url = "http://localhost:8080/workbench.html";
                window.location.replace(url);

            } else {
                alert("something went wrong");
                var url = "http://localhost:8080/workbench.html";
                window.location.replace(url);
            }
        }
    };
    var url = "/api/login";
    xhttp.open("GET", url, true);
    xhttp.send();

})();