$(document).ready(()=>{
    var unlam_home = L.latLng(-34.67061640008919,-58.5627730162868);
    var map = L.map('map').setView(unlam_home,15);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 16,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    L.marker(unlam_home).bindPopup('Los mejores sandwiches de San Justo').addTo(map);
    L.Control.geocoder({
        defaultMarkGeocode: true,
        collapsed: false,
        error: "No existe el destino",
        suggestTimeout:100,
        showResultIcons:true
    }).on('markgeocode',function (e) {
        var home = e.geocode.center;
        $("#dist").val(unlam_home.distanceTo(home));
        $("#dest").val(e.geocode.name);
    }).addTo(map);

});