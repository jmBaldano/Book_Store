document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    //get the input made by user
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    //fetch request for backend login
    fetch('/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'same-origin', //send user session
        body: JSON.stringify({ username: username, password: password }) //convert the data to JSON
    })
        .then(async res => {
            const msg = await res.text();
            if (res.ok) {
                alert(msg);
                window.location.href = '/books';
            }
            else {
                alert(msg);
            }
        })
        .catch(err => console.error(err)); //catches any error to console
});