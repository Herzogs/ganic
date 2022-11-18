$(document).ready(()=>{
    var unlam_home = L.latLng(-34.67061640008919,-58.5627730162868);
    var map = L.map('map').setView(unlam_home,15);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 16,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    var unlam_marker = L.marker(unlam_home).addTo(map);
    const obj = JSON.parse(localStorage.getItem("destino"));
    var secondLatLng = L.latLng(obj.lat,obj.lng);
    L.Routing.control({
        waypoints:[
            unlam_home,
            secondLatLng
        ],
        routeWhileDragging: false,
    }).on('routesfound',function (e) {

        e.routes[0].coordinates.forEach(function (coord,index) {
            setTimeout(()=>{
                console.warn(coord[index]);
                unlam_marker.setLatLng(coord);
            }, 10000 * index);
        });

    }).addTo(map);
});