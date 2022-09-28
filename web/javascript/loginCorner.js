auth = JSON.parse(sessionStorage.getItem("user-auth-token"));
corner = document.getElementById("login_register");
console.log(corner);
if (auth != null) {
    corner.innerHTML = "";
    corner.innerHTML =  "<button type='button' class='btn btn-default-1 btn-sm'>"
    +                        "<a class='main-btn-style2 username' href='./" + (auth.role.role == "Employee" ? "candidate-account.html" : "employer-account.html" )+"'>" + auth.username + "</a></button>"   
    +                       "<button type='button' id='logOutBtn' class='btn btn-default-2 btn-sm'>"
    +                           "<a>Log out</a></button>"
    +                       "</div>"
    +                   "</div>";



    button = document.getElementById("logOutBtn");
    button.addEventListener('click', async (event) => {
        sessionStorage.removeItem("user-auth-token");
        window.location.replace("./index.html");
    });
}
