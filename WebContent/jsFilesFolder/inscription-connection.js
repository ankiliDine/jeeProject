const signUpButton = document.getElementById("signUp");
const signInButton = document.getElementById("signIn");
const ins_con_container = document.getElementById("insc-con-container");

signUpButton.addEventListener("click", () => {
  ins_con_container.classList.add("right-panel-active");
});

signInButton.addEventListener("click", () => {
  ins_con_container.classList.remove("right-panel-active");
});
function alerter() {
	  return alert("voila");
	}