const SUPABASE_URL = "https://bgakkkbavdtcndylpwgd.supabase.co";
const SUPABASE_PUBLISHABLE_KEY = "sb_publishable_fcs7MDIxJomgpkkuHLH1eg_Zt_UcHMh";
const supabaseClient = window.supabase.createClient(SUPABASE_URL, SUPABASE_PUBLISHABLE_KEY);

async function protectDesktop() {
  const { data: { session }, error } = await supabaseClient.auth.getSession();
  if (error || !session) {
    window.location.replace("index.html");
    return;
  }
  document.getElementById("user-email").textContent = session.user.email || "Signed in";
}

document.getElementById("logout-button").addEventListener("click", async () => {
  const button = document.getElementById("logout-button");
  button.disabled = true;
  const { error } = await supabaseClient.auth.signOut();
  if (error) {
    button.disabled = false;
    alert("Sign out failed. Please try again.");
    return;
  }
  window.location.replace("index.html");
});

protectDesktop();