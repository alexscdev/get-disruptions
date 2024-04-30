var infoButton = document.createElement('button');
infoButton.setAttribute('type', 'button');
infoButton.setAttribute('id', 'customButton');
infoButton.classList.add('btn', 'btn-dark', 'position-absolute', 'top-0', 'end-0', 'm-3', 'mt-5'); 

infoButton.style.setProperty('top', '10px', 'important');



var infoIcon = document.createElement('i');
infoIcon.classList.add('bi', 'bi-info-circle');
infoIcon.setAttribute('aria-hidden', 'true');


infoButton.appendChild(infoIcon);

map.getContainer().appendChild(infoButton);


var currentURL = window.location.href;


if (currentURL.includes("http://localhost:8080/") && currentURL !== "http://localhost:8080/") {

    infoButton.addEventListener('click', function () {
        var infoButtonPosition = infoButton.getBoundingClientRect();
        var popup = document.getElementById('popup');
        if (popup.style.display === 'none' || popup.style.display === '') {
            popup.style.left = '10px'; 
            popup.style.top = infoButtonPosition.top + 'px';
            popup.style.display = 'block';
        } else {
            popup.style.display = 'none';
        }
    });
} else {

    infoButton.style.display = 'none';
}



