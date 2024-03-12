// Crea el botón
var button = document.createElement('button');
button.setAttribute('type', 'button');
button.setAttribute('id', 'customButton');
button.classList.add('btn', 'btn-dark', 'position-absolute', 'top-0', 'end-0', 'm-3');

// Crea el icono y agrega clases de Bootstrap 
var icon = document.createElement('i');
icon.classList.add('bi', 'bi-layers'); 
icon.setAttribute('aria-hidden', 'true');

// Agrega el icono al botón
button.appendChild(icon);



// Agrega el botón al contenedor del mapa
map.getContainer().appendChild(button);

// Agrega un controlador de eventos para el clic en el botón
button.addEventListener('click', function () {
  // Por ejemplo, cambia entre dos capas base predefinidas
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
