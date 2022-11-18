$(document).ready(()=>{
    var unlam_home = L.latLng(-34.67061640008919,-58.5627730162868);
    var map = L.map('map').setView(unlam_home,15);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 16,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    var unlam_marker = L.marker(unlam_home)/*.bindPopup('Los mejores sandwiches de San Justo').addTo(map)*/;
    const obj = JSON.parse(localStorage.getItem("destino"));
    L.Routing.control({
        waypoints:[
            unlam_home,
            L.latLng(obj.lat,obj.lng)
        ]
    }).on('routesfound',function (e) {
        e.routes[0].coordinates.forEach(function (coord,index) {
            setTimeout(()=>{
                unlam_marker.setLatLng(coord.lat,coord.lng);
                console.log(unlam_marker);
            }, 100* index);
        })

    }).addTo(map);
});