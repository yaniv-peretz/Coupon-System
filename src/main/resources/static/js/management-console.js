
(() => {
  if (window.location.search.includes('err')) {
    let element = document.querySelector('.err');
    element.style.visibility = "visible";
    element.style.opacity = 1;
    element.className = element.className + " shake";


    let head = document.querySelector('slide-up-fade-in');
    $(head).removeClass("slide-up-fade-in")
    let body = document.querySelector('slide-down-fade-in');
    $(body).removeClass("slide-down-fade-in")
  }
})();
  
function setCookie() {
  let name = document.querySelector('input[name="name"]').value;
  let password = document.querySelector('input[name="password"]').value;
  let type = document.querySelector('select[name="selector"]').value;

  document.cookie = "name="+name+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
  document.cookie = "password="+password+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
  document.cookie = "type="+type+"; expires=Fri, 31 Dec 9999 23:59:59 GMT";
}