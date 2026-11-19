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

async function loadSharedWallpaper() {
  try {
    const db = await new Promise((resolve, reject) => {
      const req = indexedDB.open("rakeshpc-appearance", 1);
      req.onupgradeneeded = () => req.result.createObjectStore("settings");
      req.onsuccess = () => resolve(req.result);
      req.onerror = () => reject(req.error);
    });
    const blob = await new Promise((resolve, reject) => {
      const req = db.transaction("settings", "readonly").objectStore("settings").get("wallpaper");
      req.onsuccess = () => resolve(req.result || null);
      req.onerror = () => reject(req.error);
    });
    db.close();
    if (blob) {
      const imageUrl = URL.createObjectURL(blob);
      const screen = document.querySelector(".screen");
      screen.style.backgroundImage = 'linear-gradient(rgba(5, 9, 19, .48), rgba(5, 9, 19, .62)), url("' + imageUrl + '")';
      screen.style.backgroundSize = "cover";
      screen.style.backgroundPosition = "center";
      screen.style.backgroundRepeat = "no-repeat";
    }
  } catch (error) {
    console.warn("Could not load saved wallpaper:", error);
  }
}
loadSharedWallpaper();


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