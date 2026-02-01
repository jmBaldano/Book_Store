document.addEventListener("DOMContentLoaded", () => {
    bindAddToCartButtons();
    bindCheckoutButton();
    loadCart();
    loadOrders();
    updateCartCount();
});

// ====================== ADD TO CART ======================
function bindAddToCartButtons() {
    const addBtns = document.querySelectorAll(".add-to-cart-btn");
    if (!addBtns) return;

    addBtns.forEach(btn => {
        btn.addEventListener("click", () => {
            const bookId = btn.dataset.id;
            if (!bookId) return;

            fetch(`/api/cart/add/${bookId}`, { method: "POST" })
                .then(res => {
                    if (!res.ok) throw new Error("Add to cart failed");
                    alert("Book added to cart!");
                    loadCart();
                    updateCartCount();
                })
                .catch(console.error);
        });
    });
}

// ====================== CART ======================
function loadCart() {
    const cartList = document.getElementById("cartList");
    const cartTotal = document.getElementById("cartTotal");

    if (!cartList && !cartTotal) return; // no cart elements on this page

    fetch("/api/cart")
        .then(res => res.json())
        .then(cartItems => {
            if (cartList) {
                cartList.innerHTML = "";
                cartItems.forEach(item => {
                    const li = document.createElement("li");

                    // Use nested book fields
                    const bookTitle = item.book?.title || "Unknown";
                    const bookPrice = item.book?.price || 0;
                    const quantity = item.quantity || 0;

                    li.textContent = `${bookTitle} - ₱${bookPrice} x ${quantity}`;

                    const removeBtn = document.createElement("button");
                    removeBtn.textContent = "Remove";
                    removeBtn.addEventListener("click", () => removeFromCart(item.id));

                    li.appendChild(removeBtn);
                    cartList.appendChild(li);
                });
            }

            if (cartTotal) {
                const total = cartItems.reduce((sum, item) => sum + (item.book?.price || 0) * item.quantity, 0);
                cartTotal.textContent = total.toFixed(2);

            }
        })
        .catch(console.error);
}

function removeFromCart(cartItemId) {
    fetch(`/api/cart/remove/${cartItemId}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok) throw new Error("Remove failed");
            loadCart();
            updateCartCount();
        })
        .catch(console.error);
}

// ====================== CHECKOUT ======================
function bindCheckoutButton() {
    const checkoutBtn = document.getElementById("checkoutBtn");
    if (!checkoutBtn) return;

    checkoutBtn.addEventListener("click", () => {
        fetch("/api/cart/checkout", { method: "POST" })
            .then(res => {
                if (!res.ok) throw new Error("Checkout failed");
                alert("Checkout successful!");
                loadCart();
                loadOrders();
                updateCartCount();
            })
            .catch(console.error);
    });
}

// ====================== ORDER HISTORY ======================
function loadOrders() {
    const orderList = document.getElementById("orderList");
    if (!orderList) return; // skip if no order list on this page

    fetch("/api/cart/history")
        .then(res => res.json())
        .then(orders => {
            orderList.innerHTML = "";

            orders.forEach(order => {
                const li = document.createElement("li");
                let html = `
                    <h3>Order #${order.id}</h3>
                    <p>Date: ${new Date(order.orderDate).toLocaleString()}</p>
                    <p>Total: ₱${order.totalAmount.toFixed(2)}</p>
                    <h4>Items:</h4><ul>
                `;

                order.orderItems.forEach(item => {
                    html += `<li>
                        <b>${item.title}</b> — ₱${item.price.toFixed(2)}
                        (x${item.quantity})
                    </li>`;
                });

                html += "</ul><hr/>";
                li.innerHTML = html;
                orderList.appendChild(li);
            });
        })
        .catch(console.error);
}

// ====================== CART COUNT ======================
function updateCartCount() {
    const cartCountEl = document.getElementById("cartCount");
    if (!cartCountEl) return;

    fetch("/api/cart")
        .then(res => res.json())
        .then(cartItems => {
            const count = cartItems.reduce((sum, item) => sum + item.quantity, 0);
            cartCountEl.textContent = count;
        })
        .catch(console.error);
}
