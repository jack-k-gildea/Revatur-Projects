let loginForm = document.getElementById("login-form");

loginForm.onsubmit = async function(e){
    e.preventDefault();

    //get value in the textboxes
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    //sending values to backend
    let serverResponse = await fetch(`${domain}/api/login`,{
        method:"POST",
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    let responseData = await serverResponse.json();
    console.log(responseData);

    if(responseData.success){
        if(responseData.response.role_id == 1){
            window.location = `${domain}/employeepage?id=${responseData.response.user_id}`
        }
        else if(responseData.response.role_id == 2){
            window.location = `${domain}/managerpage?id=${responseData.response.user_id}`
        }
    }
    else{
        alert("Username or password incorrect");
        window.location = `${domain}`;
    }
    
}