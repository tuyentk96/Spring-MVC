var navbarRegisterClick = document.getElementById('navbar__register-form');
var navbarLoginClick = document.getElementById('navbar__login-form');

var formLoginClick = document.getElementById('form__login-click')
var formRegisterClick = document.getElementById('form__register-click')
var formLoginCancel = document.getElementById('form__login-cancel')
var formRegisterCancel = document.getElementById('form__register-cancel')

var modalRegisterForm = document.getElementById('modal_register');
var modalLoginForm =  document.getElementById('modal_login');

navbarLoginClick.addEventListener('click',function () {
    modalLoginForm.style.display='grid';
    modalRegisterForm.style.display='none';
})

navbarRegisterClick.addEventListener('click',function(){
    modalLoginForm.style.display='none';
    modalRegisterForm.style.display='grid';
})

formLoginClick.addEventListener('click',function(){
    modalRegisterForm.style.display='grid';
    modalLoginForm.style.display='none';
})

formRegisterClick.addEventListener('click',function(){
    modalLoginForm.style.display='grid';
    modalRegisterForm.style.display='none';
})

formLoginCancel.addEventListener('click',function(){
    modalLoginForm.style.display='none';
    modalRegisterForm.style.display='none';
})

formRegisterCancel.addEventListener('click',function(){
    modalLoginForm.style.display='none';
    modalRegisterForm.style.display='none';
})