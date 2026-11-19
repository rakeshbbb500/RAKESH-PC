const form = document.getElementById("login-form");
const password = document.getElementById("password");
const toggle = document.getElementById("toggle-password");
const message = document.getElementById("message");

toggle.addEventListener("click", () => {
  const revealing = password.type === "password";
  password.type = revealing ? "text" : "password";
  toggle.textContent = revealing ? "Hide" : "Show";
  toggle.setAttribute("aria-label", revealing ? "Hide password" : "Show password");
});

form.addEventListener("submit", (event) => {
  event.preventDefault();
  message.textContent = "Login authentication will be connected securely in the next step.";
});