$(document).ready(()=>{
    var array = [];
    var home = '';
    var unlam_home = L.latLng(-34.67061640008919,-58.5627730162868);
    var marcas = 0;
    array.push(unlam_home);
    var map = L.map('map').setView(unlam_home,15);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 16,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    L.marker(unlam_home).bindPopup('Los mejores sandwiches de San Justo').addTo(map);
    var geocoder = L.Control.geocoder({
        defaultMarkGeocode: true
    }).on('markgeocode',function (e) {
        home = e.geocode.center;
        array.push(home);
        L.polyline(array, {color: 'red'}).addTo(map);
        $("#dist").val(unlam_home.distanceTo(home));
        $("#info").val(e.geocode.name);
        console.log(e.geocode.name);
    }).addTo(map);

    /*map.on('geosearch/showlocation',function (e) {
        $("#dist").val(unlam_home.distanceTo(home));
        $("#info").val(e.label);
    })*/

    /*map.on("click",function (e) {
        if(marcas === 0) {
            home = L.latLng(e.latlng.lat, e.latlng.lng);
            L.marker(home).addTo(map);
            array.push(home);
            L.polyline(array, {color: 'red'}).addTo(map);
            marcas +=1;
            $("#dist").val(unlam_home.distanceTo(home));
        }
    });*/
});


