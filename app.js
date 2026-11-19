const SUPABASE_URL = "https://bgakkkbavdtcndylpwgd.supabase.co";
const SUPABASE_PUBLISHABLE_KEY = "sb_publishable_fcs7MDIxJomgpkkuHLH1eg_Zt_UcHMh";

const supabaseClient = window.supabase.createClient(
  SUPABASE_URL,
  SUPABASE_PUBLISHABLE_KEY
);

const form = document.getElementById("login-form");
const emailInput = document.getElementById("email");
const passwordInput = document.getElementById("password");
const toggle = document.getElementById("toggle-password");
const message = document.getElementById("message");
const signInButton = document.getElementById("sign-in-button");

toggle.addEventListener("click", () => {
  const revealing = passwordInput.type === "password";
  passwordInput.type = revealing ? "text" : "password";
  toggle.textContent = revealing ? "Hide" : "Show";
  toggle.setAttribute("aria-label", revealing ? "Hide password" : "Show password");
});

async function checkExistingSession() {
  const { data: { session } } = await supabaseClient.auth.getSession();
  if (session) window.location.replace("desktop.html");
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  message.textContent = "";
  signInButton.disabled = true;
  signInButton.textContent = "Signing in...";

  const email = emailInput.value.trim();
  const password = passwordInput.value;

  const { error } = await supabaseClient.auth.signInWithPassword({ email, password });

  if (error) {
    message.textContent = error.message === "Invalid login credentials"
      ? "Gmail અથવા Password ખોટો છે. ફરી પ્રયાસ કરો."
      : error.message;
    signInButton.disabled = false;
    signInButton.innerHTML = 'Sign in <span aria-hidden="true">→</span>';
    return;
  }

  window.location.href = "desktop.html";
});

checkExistingSession();