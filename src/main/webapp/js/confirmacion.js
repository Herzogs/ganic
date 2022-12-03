$(document).ready(()=>{
    var unlam_home = L.latLng(-34.67061640008919,-58.5627730162868);
    var map = L.map('map').setView(unlam_home,13);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 16,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    L.marker(unlam_home).bindPopup('Los mejores sandwiches de San Justo').addTo(map);
    L.circle(unlam_home, {radius: 3000}).addTo(map);
    L.control.Legend({
        position: "bottomleft",
        title: "Delivery",
        legends: [{
            label: " Rango de Envio",
            type: "circle",
            radius: 6,
            color: "blue"
        }]
    }).addTo(map);
    L.Control.geocoder({
        defaultMarkGeocode: true,
        collapsed: false,
        error: "No existe el destino",
        suggestTimeout:100,
        showResultIcons:true
    }).on('markgeocode',function (e) {
        var home = e.geocode.center;
        var distancia = unlam_home.distanceTo(home);
            const json = JSON.stringify(home);
            localStorage.setItem("destino", json)
            $("#dist").val(distancia);
            $("#dest").val(e.geocode.name);
    }).addTo(map);

});
