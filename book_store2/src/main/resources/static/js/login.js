document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    fetch('/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'same-origin', // << important: ensures browser accepts Set-Cookie
        body: JSON.stringify({ username: username, password: password })
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
        .catch(err => console.error(err));
});