function showCamera() {
    document.getElementById('camera').style.display = 'block';
    var video = document.getElementById('video');
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
            video.srcObject = stream;
        })
        .catch(error => {
            console.error("Error accessing the camera: ", error);
        });
}

function takePhoto() {
    var video = document.getElementById('video');
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    context.drawImage(video, 0, 0, canvas.width, canvas.height);
    var dataURL = canvas.toDataURL('image/png');
    document.getElementById('preview').src = dataURL;
}

function previewFile() {
    var preview = document.getElementById('preview');
    var file = document.getElementById('upload').files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
    }
}

function uploadPhoto() {
    var img = document.getElementById('preview').src;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'UploadServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function () {
        if (xhr.status === 200) {
            var response = xhr.responseText;
            if (response.startsWith("data:image/png;base64,")) {
                document.getElementById('preview').src = response;
            } else {
                alert(response); // Show the error message
            }
        }
    };

    xhr.send('image=' + encodeURIComponent(img));
}
function showCamera() {
    document.getElementById('camera').style.display = 'block';
    var video = document.getElementById('video');
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
            video.srcObject = stream;
        })
        .catch(error => {
            console.error("Error accessing the camera: ", error);
        });
}

function takePhoto() {
    var video = document.getElementById('video');
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    context.drawImage(video, 0, 0, canvas.width, canvas.height);
    var dataURL = canvas.toDataURL('image/png');
    document.getElementById('preview').src = dataURL;
}

function previewFile() {
    var preview = document.getElementById('preview');
    var file = document.getElementById('upload').files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
    }
}

function uploadPhoto() {
    var img = document.getElementById('preview').src;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'UploadServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function () {
        if (xhr.status === 200) {
            var response = xhr.responseText;
            if (response.startsWith("data:image/png;base64,")) {
                document.getElementById('preview').src = response;
            } else {
                alert(response); // Show the error message
            }
        }
    };

    xhr.send('image=' + encodeURIComponent(img));
}
