const baseUrl = 'http://localhost:8080/admin';

// Register user
document.getElementById('userForm').addEventListener('submit', async e => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const res = await fetch(`${baseUrl}/register`, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({username, password})
    });
    const text = await res.text();
    document.getElementById('userMessage').innerText = text;
    // loadUsers();
});

// Add book
document.getElementById('bookForm').addEventListener('submit', async e => {
    e.preventDefault();
    const title = document.getElementById('title').value;
    const author = document.getElementById('author').value;
    const description = document.getElementById('description').value;
    const category = document.getElementById('category').value;
    const price = parseFloat(document.getElementById('price').value);
    const res = await fetch(`${baseUrl}/books`, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({title, author, description, category, price})
    });
    const text = await res.text();
    document.getElementById('bookMessage').innerText = text;
    // loadBooks();
});

// Load users
// async function loadUsers() {
//     const res = await fetch(`${baseUrl}/users`);
//     const users = await res.json();
//     const ul = document.getElementById('userList');
//     ul.innerHTML = '';
//     users.forEach(u => {
//         const li = document.createElement('li');
//         li.textContent = `${u.id} - ${u.username} (${u.role})`;
//         ul.appendChild(li);
//     });
// }

// Load books
// async function loadBooks() {
//     const res = await fetch(`${baseUrl}/books`);
//     const books = await res.json();
//     const ul = document.getElementById('bookList');
//     ul.innerHTML = '';
//     books.forEach(b => {
//         const li = document.createElement('li');
//         li.textContent = `${b.id} - ${b.title} by ${b.author} [${b.category.name}] $${b.price}`;
//         ul.appendChild(li);
//     });
//
// }

// Initial load
// loadUsers();
// loadBooks();