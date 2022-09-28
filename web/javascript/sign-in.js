const logButton = document.getElementById("loginBtn");
logButton.addEventListener('click', async (event) => {
    var loginIdentifier = document.getElementById('identifier').value;
    var loginPassword = document.getElementById('password').value; 

    let login = {
        identifier: loginIdentifier,
        password: loginPassword
    }

    console.log(login);

    try {
        const raw_response = await fetch(
          `http://localhost:8080/api/User/login`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify(login),
          }
        );
    
        if (!raw_response.ok) {
          throw new Error(raw_response.status);
        }
    
        const user_data = await raw_response.json();
    
        console.log(user_data);
    
        //save token into a sessionStorage variable
        sessionStorage.setItem("user-auth-token", JSON.stringify(user_data));
       
        if (user_data.role.role == 'Employee') {
            window.location.replace("/web/candidate-account.html");
        } else {
            window.location.replace("/web/employer-account.html")
        }
        
      } catch (error) {
        console.log(error);
      }
});

const regButton = document.getElementById("registerBtn");
regButton.addEventListener('click', async (event) => {
    let xhr = new XMLHttpRequest();
    console.log(xhr);

    var registerUsername = document.getElementById('registerUsername').value
    var registerPassword = document.getElementById('registerPassword').value
    var registerEmail = document.getElementById('registerEmail').value
    var registerRole
    if (document.getElementById('flexRadioJobSeeker').checked) {
        registerRole = "Employee"
    } else {
        registerRole = "Employer"
    }

    var newUser = {
        user: {
            username: registerUsername,
            password: registerPassword,
            email: registerEmail
        },
        role: registerRole
    }

    console.log(newUser);

    xhr.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
          let data = JSON.parse(xhr.responseText);
          console.log(data);
          sessionStorage.setItem('message', xhr.responseText);
          window.alert("you may now do you a login please");
        }else {
          console.log(xhr.status);
        }

        console.log("Processing")
    };

    xhr.open("POST", 'http://localhost:8080/api/User/account/register', true);
    xhr.setRequestHeader("Content-Type", "application/json");
    console.log("help");
    xhr.send(JSON.stringify(newUser));
});
