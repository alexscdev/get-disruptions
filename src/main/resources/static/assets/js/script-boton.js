var layerButton = document.createElement('button');
layerButton.setAttribute('type', 'button');
layerButton.setAttribute('id', 'customButton');
layerButton.classList.add('btn', 'btn-dark', 'position-absolute', 'top-0', 'end-0', 'm-3');

var layerIcon = document.createElement('i');
layerIcon.classList.add('bi', 'bi-layers'); 
layerIcon.setAttribute('aria-hidden', 'true');

layerButton.appendChild(layerIcon);

map.getContainer().appendChild(layerButton);

layerButton.addEventListener('click', function (event) {

    event.stopPropagation();

    if (map.hasLayer(baseMaps["Color"])) {
        map.removeLayer(baseMaps["Color"]);
        baseMaps["Blanco y Negro"].addTo(map);
        document.cookie = "defaultBaseLayer=Blanco y Negro";
    } else {
        map.removeLayer(baseMaps["Blanco y Negro"]);
        baseMaps["Color"].addTo(map);
        document.cookie = "defaultBaseLayer=Color";
    }
});