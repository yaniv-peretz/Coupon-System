(function () {
  if (window.location.search.includes('err')) {
    var element = document.querySelector('.err');
    element.style.visibility = "visible";
    element.style.opacity = 1;
    element.className = element.className + " shake";


    var element = document.querySelector('slide-up-fade-in');
    $(element).removeClass("slide-up-fade-in")
    var element = document.querySelector('slide-down-fade-in');
    $(element).removeClass("slide-down-fade-in")
  }

})();

function setCookie() {
  var name = document.querySelector('input[name="name"]').value;
  var password = document.querySelector('input[name="password"]').value;
  var type = document.querySelector('select[name="selector"]').value;

  document.cookie = "name="+name+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
  document.cookie = "password="+password+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
  document.cookie = "type="+type+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
}

function getCookie() {
  alert(document.cookie);
}