$(document).ready(()=>{
    var array = [];
    const lat_unlam = -34.67061640008919;
    const long_unlam = -58.5627730162868;

    array.push({
        lat: lat_unlam,
        long: long_unlam
    });
    var map = L.map('map').setView([-34.67061640008919,-58.5627730162868],15);
    const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    L.marker([-34.67061640008919,-58.5627730162868]).addTo(map);
    /*map.on("click",function (e) {
        new L.Marker([e.latlng.lat, e.latlng.lng]).addTo(map);
        array.push([e.latlng.lat,e.latlng.lng]);
        L.polyline(array, {color: 'red'}).addTo(map);
    });*/

    map.locate();

    map.on('locationfound', (e)=>{
        array.push(e.latlng);
        new L.Marker([e.latlng.lat, e.latlng.lng]).addTo(map);
        L.polyline(array, {color: 'red'}).addTo(map);
    });
    var _length = L.GeometryUtil.distance(map,array[0],array[1]);
    document.getElementById('cargo').innerHTML = _length;
    var distancia = $('cargo');
    var montoParcial = $('#montoParcial');
    $('#montoTotal').add(distancia+montoParcial);
});


