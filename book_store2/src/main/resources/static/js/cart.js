document.addEventListener("DOMContentLoaded", () => {
    renderCart();
    renderOrders();
    updateCartCount();
});

// ====================== CART ======================
function renderCart() {
    const cartList = document.getElementById("cartList");
    const cartTotal = document.getElementById("cartTotal");

    if (!cartList && !cartTotal) return;

    fetch("/api/cart")
        .then(res => res.json())
        .then(cartItems => {
            if (cartList) {
                cartList.innerHTML = "";

                cartItems.forEach(item => {
                    const li = document.createElement("li");

                    const title = item.book?.title || "Unknown";
                    const price = item.book?.price || 0;
                    const quantity = item.quantity || 0;
                    const itemTotal = price * quantity;

                    li.textContent = `${title} - ₱${price} x ${quantity} (₱${itemTotal})`;

                    const removeBtn = document.createElement("button");
                    removeBtn.textContent = "Remove";
                    removeBtn.addEventListener("click", () => {
                        fetch(`/api/cart/remove/${item.id}`, { method: "DELETE" })
                            .then(() => {
                                renderCart();
                                updateCartCount();
                            })
                            .catch(console.error);
                    });

                    li.appendChild(removeBtn);
                    cartList.appendChild(li);
                });
            }

            if (cartTotal) {
                // Backend can optionally send a cartTotal, else calculate here
                const total = cartItems.reduce((sum, item) => sum + (item.book?.price || 0) * item.quantity, 0);
                cartTotal.textContent = total.toFixed(2);
            }
        })
        .catch(console.error);
}

// ====================== CHECKOUT ======================
const checkoutBtn = document.getElementById("checkoutBtn");
if (checkoutBtn) {
    checkoutBtn.addEventListener("click", () => {
        fetch("/api/cart/checkout", { method: "POST" })
            .then(res => res.json())
            .then(order => {
                if (!order) {
                    alert("Cart is empty!");
                    return;
                }
                alert("Checkout successful!");
                renderCart();
                renderOrders();
                updateCartCount();
            })
            .catch(console.error);
    });
}

// ====================== ORDER HISTORY ======================
function renderOrders() {
    const orderList = document.getElementById("orderList");
    if (!orderList) return;

    fetch("/api/cart/history")
        .then(res => res.json())
        .then(orders => {
            orderList.innerHTML = "";

            orders.forEach(order => {
                const li = document.createElement("li");
                li.classList.add("order-card");
                let html = `
                
                    <h3>Order</h3>
                    <p>Date: ${new Date(order.orderDate).toLocaleString()}</p>
                    <p>Total: ₱${order.totalAmount.toFixed(2)}</p>
                    <h4>Items:</h4>
                    <ul>
                `;

                order.orderItems.forEach(item => {
                    html += `<li>
                        <b>${item.title}</b> — ₱${item.price.toFixed(2)} (x${item.quantity})
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

// ====================== ADD TO CART BUTTONS ======================
document.querySelectorAll(".add-to-cart-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        const bookId = btn.dataset.id;
        if (!bookId) return;

        fetch(`/api/cart/add/${bookId}`, { method: "POST" })
            .then(() => {
                alert("Book added to cart!");
                renderCart();
                updateCartCount();
            })
            .catch(console.error);
    });
});
