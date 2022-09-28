auth = JSON.parse(sessionStorage.getItem("user-auth-token"));

if (auth == null) {
    console.log("seriously, youre not logged in bro.")
    document.getElementById("bod").innerHTML = "Go back you're not logged in";
}else {
    document.getElementById("greeting").innerHTML = auth.username;
    console.log(auth);
}

const updateBtn = document.getElementById('updateBtn');
updateBtn.addEventListener('click', async (event) => {

    newFirstname = document.getElementById('newFirstname').value
    newLastname  = document.getElementById('newLastname').value
    newEmail     = document.getElementById('newEmail').value
    newPassword  = document.getElementById('newPassword').value
    userId = auth.id;

    user = {
        id: userId,
        firstname: newFirstname,
        lastname: newLastname,
        email: newEmail,
        password: newPassword
    }

    try {
        const raw_response = await fetch(
          `http://localhost:8080/api/User/update`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify(user),
          }
        );
    
        if (!raw_response.ok) {
          throw new Error(raw_response.status);
        }
    
        const user_data = await raw_response.json();
    
        console.log(user_data);
    
        //save token into a sessionStorage variable
        sessionStorage.setItem("user-auth-token", JSON.stringify(user_data));
        window.alert('account update successful');
        
      } catch (error) {
        console.log(error);
      }
});