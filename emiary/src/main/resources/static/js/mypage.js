$(document).ready(function () {
  $("#check").change(function () {
    if ($("#check").is(":checked")) {
      $("#profile_access").css("background", "#adb5db");
    } else {
      $("#profile_access").css("background", "transparent");
    }
  });
});
