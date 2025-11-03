<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Video Call</title>
    <style>
        .video-container {
            display: flex;
            justify-content: space-around;
            margin: 20px;
        }
        video {
            width: 45%;
            border: 1px solid #ccc;
        }
        .controls {
            text-align: center;
            margin-top: 10px;
        }
        button {
            padding: 10px 20px;
            margin: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>Video Call</h1>
    <div class="video-container">
        <video id="localVideo" autoplay playsinline muted></video>
        <video id="remoteVideo" autoplay playsinline></video>
    </div>
    <div class="controls">
        <button id="muteMicBtn">Tắt Microphone</button>
        <button id="toggleCamBtn">Tắt Camera</button>
    </div>

    <script>
        let localStream;
        let peerConnection;
        const configuration = {
            iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
        };
        const localVideo = document.getElementById('localVideo');
        const remoteVideo = document.getElementById('remoteVideo');
        const muteMicBtn = document.getElementById('muteMicBtn');
        const toggleCamBtn = document.getElementById('toggleCamBtn');
        let isMicMuted = false;
        let isCamOff = false;
        const toUserId = '${toUserId}'; // Lấy toUserId từ model

        // WebSocket cho signaling
        const socket = new WebSocket('ws://' + window.location.host + '/signaling');

        socket.onopen = () => {
            console.log('Kết nối WebSocket đã được thiết lập');
        };

        socket.onmessage = async (event) => {
            const message = JSON.parse(event.data);
            if (message.toUserId && message.toUserId !== '${sessionId}') {
                return; // Bỏ qua nếu không phải tin nhắn dành cho người dùng này
            }
            if (message.offer) {
                await handleOffer(message.offer);
            } else if (message.answer) {
                await peerConnection.setRemoteDescription(new RTCSessionDescription(message.answer));
            } else if (message.candidate) {
                await peerConnection.addIceCandidate(new RTCIceCandidate(message.candidate));
            }
        };

        async function startCall() {
            try {
                localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
                localVideo.srcObject = localStream;
                peerConnection = new RTCPeerConnection(configuration);

                localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

                peerConnection.ontrack = (event) => {
                    remoteVideo.srcObject = event.streams[0];
                };

                peerConnection.onicecandidate = (event) => {
                    if (event.candidate) {
                        socket.send(JSON.stringify({ candidate: event.candidate, toUserId: toUserId }));
                    }
                };

                const offer = await peerConnection.createOffer();
                await peerConnection.setLocalDescription(offer);
                socket.send(JSON.stringify({ offer: offer, toUserId: toUserId }));
            } catch (error) {
                console.error('Lỗi khi bắt đầu cuộc gọi:', error);
            }
        }

        async function handleOffer(offer) {
            peerConnection = new RTCPeerConnection(configuration);
            peerConnection.ontrack = (event) => {
                remoteVideo.srcObject = event.streams[0];
            };
            peerConnection.onicecandidate = (event) => {
                if (event.candidate) {
                    socket.send(JSON.stringify({ candidate: event.candidate, toUserId: toUserId }));
                }
            };

            await peerConnection.setRemoteDescription(new RTCSessionDescription(offer));
            const answer = await peerConnection.createAnswer();
            await peerConnection.setLocalDescription(answer);
            socket.send(JSON.stringify({ answer: answer, toUserId: toUserId }));
        }

        muteMicBtn.onclick = () => {
            isMicMuted = !isMicMuted;
            localStream.getAudioTracks().forEach(track => track.enabled = !isMicMuted);
            muteMicBtn.textContent = isMicMuted ? 'Bật Microphone' : 'Tắt Microphone';
        };

        toggleCamBtn.onclick = () => {
            isCamOff = !isCamOff;
            localStream.getVideoTracks().forEach(track => track.enabled = !isCamOff);
            toggleCamBtn.textContent = isCamOff ? 'Bật Camera' : 'Tắt Camera';
        };

        // Bắt đầu cuộc gọi khi trang được tải
        window.onload = startCall;
    </script>
</body>
</html>
