document.addEventListener("DOMContentLoaded", () => {

    // Bind Add-to-Cart buttons (Thymeleaf-rendered)
    document.querySelectorAll(".add-to-cart-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            addToCart(
                btn.dataset.id,
                btn.dataset.title,
                btn.dataset.price
            );
        });
    });

    // Checkout button (safe binding)
    const checkoutBtn = document.getElementById("checkoutBtn");
    if (checkoutBtn) {
        checkoutBtn.addEventListener("click", checkout);
    }

    loadCart();
    loadOrders();
});

// ================= CART =================

function loadCart() {
    fetch("/api/cart")
        .then(res => {
            if (!res.ok) throw new Error("Failed to load cart");
            return res.json();
        })
        .then(cartItems => {
            const cartList = document.getElementById("cartList");
            const cartTotal = document.getElementById("cartTotal");

            cartList.innerHTML = "";
            let total = 0;

            cartItems.forEach(item => {
                total += item.price * item.quantity;

                const li = document.createElement("li");
                li.textContent = `${item.title} - ₱${item.price} x ${item.quantity}`;

                const removeBtn = document.createElement("button");
                removeBtn.textContent = "Remove";
                removeBtn.addEventListener("click", () => removeFromCart(item.bookId));

                li.appendChild(removeBtn);
                cartList.appendChild(li);
            });

            cartTotal.textContent = total.toFixed(2);
        })
        .catch(err => console.error(err));
}

function addToCart(bookId, title, price) {
    fetch("/api/cart/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            bookId,
            title,
            price,
            quantity: 1
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("Add to cart failed");
            return res.json();
        })
        .then(() => {
            alert("Book added to cart!");
            loadCart();
        })
        .catch(err => console.error(err));
}

function removeFromCart(bookId) {
    fetch(`/api/cart/remove/${bookId}`, {
        method: "DELETE"
    })
        .then(() => loadCart())
        .catch(err => console.error(err));
}

// ================= CHECKOUT =================

function checkout() {
    fetch("/api/cart/checkout", {
        method: "POST"
    })
        .then(res => {
            if (!res.ok) throw new Error("Checkout failed");
            return res.json();
        })
        .then(order => {
            if (!order) {
                alert("Cart is empty!");
                return;
            }
            alert("Checkout successful!");
            loadCart();
            loadOrders();
        })
        .catch(err => console.error(err));
}

// ================= ORDER HISTORY =================

function loadOrders() {
    fetch("/api/cart/history")
        .then(res => {
            if (!res.ok) throw new Error("Failed to load orders");
            return res.json();
        })
        .then(orders => {
            const orderList = document.getElementById("orderList");
            orderList.innerHTML = ""; // clear old orders

            orders.forEach(order => {
                const li = document.createElement("li");

                // Build HTML for the order
                let orderHTML = `
                    <h3>Order #${order.id}</h3>
                    <p>Date: ${new Date(order.orderDate).toLocaleString()}</p>
                    <p>Total: ₱${order.totalAmount.toFixed(2)}</p>
                    <h4>Items:</h4>
                    <ul>
                `;

                // Loop through each order item
                order.orderItems.forEach(item => {
                    orderHTML += `
                        <li>
                            <p><b>${item.title}</b> — ₱${item.price.toFixed(2)}</p>
                            <p>Quantity: ${item.quantity}</p>
                            <p>Description: ${item.description || 'No description available'}</p>
                            <p>Author: ${item.author || 'Unknown'}</p>
                        </li>
                    `;
                });

                orderHTML += "</ul><hr/>";
                li.innerHTML = orderHTML;
                orderList.appendChild(li);
            });
        })
        .catch(err => console.error(err));
}

