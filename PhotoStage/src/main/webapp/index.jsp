<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Photo App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>
    <h1>Photo App</h1>
    <button onclick="showCamera()">Take a Photo</button>
    <input type="file" id="upload" accept="image/*" onchange="previewFile()">
    <div id="camera" style="display:none;">
        <video id="video" width="640" height="480" autoplay></video>
        <button id="snap" onclick="takePhoto()">Capture</button>
        <canvas id="canvas" width="640" height="480" style="display:none;"></canvas>
    </div>
    <img id="preview" src="" alt="Image preview...">
    <button onclick="uploadPhoto()">Upload Photo</button>
</body>
</html>

