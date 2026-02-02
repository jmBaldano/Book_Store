document.getElementById('registerForm').addEventListener('submit', function (e) {
    e.preventDefault(); // prevent page reload

    //get the registered field
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;


    fetch('/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            if(msg === "User registered successfully!"){
                window.location.href = "/auth/login"; //redirect to login after registration
            }
        })
        .catch(err => console.error(err));
});