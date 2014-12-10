jQuery( document ).ready(function() {
  $('#edit-user-profile').submit(function(e) {
    var stop = false;

    $('.required').each(function() {
      if (!$(this).val()) {
        stop = true;
        $(this).addClass('error');
      }
      else {
        $(this).removeClass('error');
      }
    });

    if (stop) {
      $('#required-msg').show();
    }
    else {
      $('#required-msg').hide();
    }

    var $pass1 = $('#pass1');
    var $pass2 = $('#pass2');
    var $old_pass = $('#old-password');

    // Validating old password.
    if ($old_pass.val()) {
      if ($old_pass.val() != 'bla') {
        stop = true;
        $('#old-pass-msg').show();
        $old_pass.addClass('error');
      }
      else {
        $('#old-pass-msg').hide();
        $old_pass.removeClass('error');
      }
    }

    if ($pass1.val() && $pass2.val()) {
      if ($pass1.val() != $pass2.val()) {
        stop = true;
        $('#pass-cmp-msg').show();
        $pass1.addClass('error');
        $pass2.addClass('error');
      }
      else {
        $('#pass-cmp-msg').hide();
        $pass1.removeClass('error');
        $pass2.removeClass('error');
      }
    }

    if (stop) {
      $('.messages').show();
      e.preventDefault();
    }
    else {
      $('.messages').hide();
    }
  });

  $('#back').click(function() {
    history.back();
  });
});
