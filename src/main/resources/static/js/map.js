document.addEventListener('DOMContentLoaded', function() {
    var map = L.map('map').setView([50.0647, 19.9450], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
        maxZoom: 18,
    }).addTo(map);

    fetch('/api/rentals/rentalPointsData')
        .then(response => response.json())
        .then(data => {
            data.forEach(point => {
                var marker = L.marker([point.latitude, point.longitude]).addTo(map);
                marker.bindPopup(`<b>${point.name}</b><br><a href='/api/rentals/availableScooters?rentalPointId=${point.id}'>Zobacz dostępne hulajnogi</a>`);
            });
        });
});
