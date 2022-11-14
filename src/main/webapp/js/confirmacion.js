$(document).ready(()=>{
    var array = [];
    var home = '';
    var unlam_home = L.latLng(-34.67061640008919,-58.5627730162868);
    array.push(unlam_home);
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
        home = e.geocode.center;
        var loc = e.geocode.name.split(',');
        console.log(loc.slice(5,5).join(' '))
        array.push(home);
        L.polyline(array, {color: 'red'}).addTo(map);
        $("#dist").val(unlam_home.distanceTo(home));
        $("#dest").val(e.geocode.name);
        $("#rec").val(calcularRecargo(unlam_home.distanceTo(home)))
    }).addTo(map);

});

const calcularRecargo = (dist) => {
    var recargo = 0;
    if(dist > 3000)
     recargo = "Imposible Calcular";
    if (dist > 300 && dist <= 1000)
    recargo = 100;
    if(dist > 1000 && dist <= 2000)
    recargo = 200;
    if(dist > 2000)
    recargo = 250;
    return recargo;
}