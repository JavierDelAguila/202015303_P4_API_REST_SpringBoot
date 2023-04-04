var check = function() {
    if (document.getElementById('password').value ==
      document.getElementById('password_repeat').value) {
      document.getElementById('message').style.color = 'green';
      document.getElementById('message').innerHTML = 'Las contrasenas son iguales';
    } else {
      document.getElementById('message').style.color = 'red';
      document.getElementById('message').innerHTML = 'Las contrasenas NO son iguales';
    }
  }



  var usernameInput = document.getElementById('username');
  usernameInput.addEventListener('keypress', function(event) {
    var regex = /^[a-zA-Z0-9]+$/;
    if (!regex.test(event.key)) {
        event.preventDefault();
      }
  });


  locationInput = document.getElementById('location');
  locationInput.addEventListener('keypress', function(event) {
    var regex = /^[a-zA-Z]+$/;
    if (!regex.test(event.key)) {
        event.preventDefault();
      }
  });


