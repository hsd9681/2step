document.getElementById('status-btn').addEventListener('click', function() {
    document.querySelector('.col1 .col-text').textContent = '시작전';
    document.querySelector('.col2 .col-text').textContent = '진행중';
    document.querySelector('.col3 .col-text').textContent = '완료';
    document.querySelector('.col4 .col-text').textContent = '긴급';
});

document.getElementById('member-btn').addEventListener('click', function() {
    const members = ['홍길동', '김철수', '이영희', '박영수'];
    document.querySelector('.col1 .col-text').textContent = members[0];
    document.querySelector('.col2 .col-text').textContent = members[1];
    document.querySelector('.col3 .col-text').textContent = members[2];
    document.querySelector('.col4 .col-text').textContent = members[3];
});

// 보드 목록 가져오기 함수
function fetchBoardList() {
    const auth = getToken();
    fetch('/api/board', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'AccessToken': auth
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('보드 목록을 가져오는 데 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            const listView = document.querySelector('.list-view');
            listView.innerHTML = ''; // 기존 내용을 지우고 새로 추가
            data.forEach(board => {
                const newBoard = document.createElement('div');
                newBoard.className = 'board';
                newBoard.setAttribute('data-board-id', board.id); // 보드 ID 추가
                newBoard.textContent = board.title; // 백엔드에서 반환한 제목을 사용
                listView.appendChild(newBoard);
            });
        })
        .catch(error => {
            alert(error.message);
        });
}

// DOMContentLoaded 이벤트 리스너
document.addEventListener('DOMContentLoaded', function() {
    fetchBoardList(); // 페이지 로드 시 보드 목록 가져오기
});

document.querySelector('.board-add').addEventListener('click', function() {
    const auth = getToken();
    console.log(auth)
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        jqXHR.setRequestHeader('AccessToken', auth);
    });
    const title = prompt('보드 제목을 입력하세요:');
    const intro = prompt('보드 한 줄 설명을 입력하세요:');

    if (title !== null && title.trim() !== '' && intro !== null && intro.trim() !== '') {
        const boardData = {
            title: title.trim(),
            description: intro.trim()
        };

        // 서버로 데이터를 전송하는 Ajax 요청
        fetch('/api/board', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'AccessToken': auth
            },
            body: JSON.stringify(boardData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('보드 생성에 실패했습니다.');
                }
                return response.json();
            })
            .then(data => {
                // 성공적으로 보드가 생성된 경우 UI에 반영
                const newBoard = document.createElement('div');
                newBoard.className = 'board';
                newBoard.setAttribute('data-board-id', data.id); // 보드 ID 추가
                newBoard.textContent = data.title; // 백엔드에서 반환한 제목을 사용
                document.querySelector('.list-view').appendChild(newBoard);
            })
            .catch(error => {
                alert(error.message);
            });
    } else {
        alert('제목과 한 줄 설명을 모두 입력해야 합니다.');
    }
});


// 유저 목록을 가져오는 함수
function fetchUserList(boardId) {
    const auth = getToken();
    fetch(`/api/user/${boardId}/users`, {
        method: 'GET',
        headers: {
            'AccessToken': auth,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('유저 목록을 가져오는데 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            const userListView = document.querySelector('.user-list .user-view');
            userListView.innerHTML = ''; // 기존 목록 초기화
            const userListTitle = document.createElement('div');
            userListTitle.textContent = '유저목록'; // 유저 목록 제목 추가
            userListView.appendChild(userListTitle);
            data.forEach(user => {
                const userItem = document.createElement('div');
                userItem.textContent = user.username;
                userListView.appendChild(userItem);
            });
        })
        .catch(error => {
            alert(error.message);
        });
}
let boardId;
// 보드 클릭 이벤트에 유저 목록을 가져오는 기능 추가
document.querySelector('.list-view').addEventListener('click', function(event) {
    const board = event.target.closest('.board');
    if (board) {

        boardId = board.getAttribute('data-board-id');
        fetchUserList(boardId); // 클릭한 보드의 ID를 넘겨서 유저 목록을 가져옵니다.
    }
});
document.addEventListener('DOMContentLoaded', function() {
    // 초대 버튼 클릭 이벤트 리스너 설정
    const inviteButtons = document.querySelectorAll('.invite-user-btn');
    inviteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            const invitedUsername = prompt('초대할 사용자의 이름을 입력하세요:');
            if (invitedUsername) {
                const inviteData = {
                    username: invitedUsername.trim()
                };
                inviteUser(boardId, inviteData);
            } else {
                alert('초대할 사용자의 이름을 입력해주세요.');
            }
        });
    });
});

// 유저를 초대하는 함수
function inviteUser(boardId, inviteData) {
    console.log(boardId)
    console.log(inviteData)
    const auth = getToken();
    fetch(`/api/board/${boardId}/invite`, {
        method: 'POST',
        headers: {
            'AccessToken': auth,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(inviteData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('사용자 초대에 실패했습니다.');
            }
            return response.text(); // 성공 메시지를 받아옵니다.
        })
        .then(message => {
            alert(message); // 성공 메시지를 알립니다.
            fetchUserList(boardId); // 초대 후에 유저 목록을 다시 가져옵니다.
        })
        .catch(error => {
            alert(error.message);
        });
}



document.getElementById('col-add').addEventListener('click', function() {
    const colView = document.querySelector('.col-view');
    const existingCols = colView.querySelectorAll('.col-box').length;
    if (existingCols >= 4) {
        alert('최대 4개의 컬럼만 추가할 수 있습니다.');
        return;
    }

    const columnTypes = ['시작전', '진행중', '완료', '긴급'];
    const text = prompt('시작전, 진행중, 완료, 긴급 중 하나를 입력하세요:');
    if (text !== null && columnTypes.includes(text.trim())) {
        const newCol = document.createElement('div');
        newCol.className = 'col' + (existingCols + 1) + ' cell col-box';
        newCol.innerHTML = `<div class="col-text">${text.trim()}</div>`;
        colView.appendChild(newCol);
    } else {
        alert('올바른 컬럼 유형을 입력하세요.');
    }
});

function getToken() {
    let auth = Cookies.get('AccessToken');
    if(auth === undefined) {
        return '';
    }
    return auth;
}
