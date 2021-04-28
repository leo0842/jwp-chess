import {chessRoomList, closeRoomList} from "./firstPage.js";
import {askUserToLogin} from "./userLogin.js";

let joinRoomBtn = document.getElementById("joinRoomBtn");
joinRoomBtn.addEventListener("click", joinRoom);

export function checkFetchLogin(response) {
    if (!response.ok) {
        console.log(response.status)
        if (response.status === 401) {
            alert("로그인을 먼저 해주세요");
            askUserToLogin();
        } else {
            alert("서버와의 통신에 실패했습니다.");
        }
        throw new Error(response.status);
    }
    return response.json();
}

function joinRoom() {
    //Fetch로 현재 방들 받아오기
    fetch("/games")
        .then(response => {
            return checkFetchLogin(response);
        })
        .then(data => {
            chessRoomList.style.display = "flex";
            showRoomStatus(data);
        })
        .catch(error => {
        });
}

function showRoomStatus(data) {
    let rooms = data.gameRooms;
    rooms.forEach(room => showRoom(room));
    addCloseBtn();
}

function showRoom(room) {
    let id = room.roomId;
    let name = room.roomName;
    let chessRoom = document.createElement('div');
    chessRoom.setAttribute("class", "chessRoom");
    chessRoom.setAttribute("id", id);

    let chessRoomName = document.createElement("h2");
    chessRoomName.setAttribute("class", "chessRoomName");
    chessRoomName.innerText = name;

    let chessRoomBtn = document.createElement('div');
    chessRoomBtn.setAttribute("class", "chessRoomBtn");

    let chessRoomJoinBtn = document.createElement('button');
    chessRoomJoinBtn.setAttribute("class", "chessRoomJoinBtn");
    chessRoomJoinBtn.innerText = "Join";

    let chessRoomDeleteBtn = document.createElement('button');
    chessRoomDeleteBtn.setAttribute("class", "chessRoomDeleteBtn");
    chessRoomDeleteBtn.innerText = "Delete";

    chessRoom.appendChild(chessRoomName);
    chessRoomBtn.appendChild(chessRoomJoinBtn);
    chessRoomBtn.appendChild(chessRoomDeleteBtn);
    chessRoom.appendChild(chessRoomBtn);
    chessRoomList.appendChild(chessRoom);
}

function addCloseBtn() {
    let closeRoomListBtn = document.createElement("button");
    closeRoomListBtn.setAttribute("class", "closeRoomListBtn");
    closeRoomListBtn.setAttribute("id", "closeRoomListBtn");
    closeRoomListBtn.innerText = "close";
    closeRoomListBtn.addEventListener("click", closeRoomList);
    chessRoomList.appendChild(closeRoomListBtn);
}