function toggleDetails(event) {
  var details = event.currentTarget.nextElementSibling;
  details.classList.toggle('show-details');
}

document.addEventListener('DOMContentLoaded', function() {
  var points = document.querySelectorAll('.point-header');
  points.forEach(function(point) {
    point.addEventListener('click', toggleDetails);
  });
});