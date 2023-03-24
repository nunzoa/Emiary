$(document).ready(function () {
  $("#check").change(function () {
    if ($("#check").is(":checked")) {
      $("#profile_access").css("background", "#adb5db").html("프로필 비공개");
    } else {
      $("#profile_access").css("background", "transparent").html("프로필 공개");
    }
  });
});
