(function(){
//Initialize Firebase
    const config = {
            apiKey: "AIzaSyADe9Jmwc1-5QFdhivkGCZDln53L24iLXE",
            authDomain: "project-id.firebaseapp.com",
            databaseURL: "https://project-id.firebaseio.com",
            storageBucket: "project-id.appspot.com",
        };
    firebase.initializeApp(config);

    //get elements
    const txtEmail = document.getElementById('txtEmail');
    const txtPassword = document.getElementById('txtPassword');
    const btnLogin = document.getElementById('btnLogin');
    const btnLogout = document.getElementById('btnLogout');

}
());
