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

                // 삭제 버튼 추가
                const deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                deleteButton.className = 'delete-board-btn';
                deleteButton.setAttribute('data-board-id', board.id); // 삭제 버튼에 보드 ID 설정
                deleteButton.addEventListener('click', deleteBoard); // 삭제 버튼 클릭 이벤트 리스너 추가


                newBoard.appendChild(deleteButton); // 보드 요소에 삭제 버튼 추가
                listView.appendChild(newBoard);
            });
        })
        .catch(error => {
            alert(error.message);
        });
}
// 보드 삭제 함수
function deleteBoard(event) {
    const boardId = event.target.getAttribute('data-board-id');
    const auth = getToken();
    fetch(`/api/board/${boardId}`, {
        method: 'DELETE',
        headers: {
            'AccessToken': auth,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('보드 삭제에 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            // 삭제 성공 시 UI에서 보드 제거
            event.target.closest('.board').remove();
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

                // 삭제 버튼 추가
                const deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                deleteButton.className = 'delete-board-btn';
                deleteButton.setAttribute('data-board-id', data.id); // 삭제 버튼에 보드 ID 설정
                deleteButton.addEventListener('click', deleteBoard); // 삭제 버튼 클릭 이벤트 리스너 추가

                newBoard.appendChild(deleteButton); // 보드 요소에 삭제 버튼 추가

                document.querySelector('.list-view').appendChild(newBoard);
            })
            .catch(error => {
                alert(error.message);
            });
    } else {
        alert('제목과 한 줄 설명을 모두 입력해야 합니다.');
    }
});


// 컬럼 추가 버튼 클릭 이벤트 리스너
document.getElementById('col-add').addEventListener('click', function() {
    const colView = document.querySelector('.col-view');
    const existingCols = colView.querySelectorAll('.col-box').length;
    if (existingCols >= 4) {
        alert('최대 4개의 컬럼만 추가할 수 있습니다.');
        return;
    }

    const columnTypes = ['시작전', '진행중', '완료', '긴급'];
    const name = prompt('시작전, 진행중, 완료, 긴급 중 하나를 입력하세요:');
    if (name !== null && columnTypes.includes(name.trim())) {
        const obj = { name: name.trim() };
        const auth = getToken();

        fetch(`/api/board${boardId}/col`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'AccessToken': auth
            },
            body: JSON.stringify(obj)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('컬럼 생성 실패');
                }
                return response.json();
            })
            .then(data => {
                // 컬럼이 성공적으로 생성된 경우 UI에 반영
                const newCol = document.createElement('div');
                newCol.className = 'col' + (existingCols + 1) + ' cell col-box';
                newCol.setAttribute('data-col-id', data.id); // 컬럼 ID 설정
                newCol.innerHTML = `
                    <div class="col-text">${name.trim()}</div>
                    <button class="delete-col-btn">삭제</button>
                    <button class="add-card-btn">카드 추가</button>
                    <div class="card-container"></div> <!-- 카드를 담을 컨테이너 -->
                `;
                colView.appendChild(newCol);

                // 삭제 버튼 클릭 이벤트 리스너 추가
                const deleteButton = newCol.querySelector('.delete-col-btn');
                deleteButton.addEventListener('click', function() {
                    deleteColumn(newCol); // 삭제 함수 호출
                });

                // 카드 추가 버튼 클릭 이벤트 리스너 추가
                const addCardButton = newCol.querySelector('.add-card-btn');
                addCardButton.addEventListener('click', function() {
                    addCard(newCol);
                });
            })
            .catch(error => {
                alert(error.message);
            });
    } else {
        alert('올바른 컬럼 유형을 입력하세요.');
    }
});
function deleteColumn(colElement) {
    const colId = colElement.getAttribute('data-col-id');
    const auth = getToken();

    fetch(`/api/board${boardId}/col/${colId}`, {
        method: 'DELETE',
        headers: {
            'AccessToken': auth,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('컬럼 삭제에 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            colElement.remove(); // 삭제 성공 시 UI에서 컬럼 제거
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
        fetchColumnList(boardId);
    }
});

let columnList = [];
function fetchColumnList(boardId) {

    const colListView = document.querySelector('.col-view');
    colListView.innerHTML = ''; // 기존 목록 초기화

    const colView = document.querySelector('.col-view');
    const existingCols = colView.querySelectorAll('.col-box').length;

    const auth = getToken();
    fetch(`/api/board/${boardId}/col`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json', 'AccessToken': auth
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('컬럼 조회 실패');
            }
            return response.json();
        })
        .then(data => {
            columnList = data;
        })
        .catch(error => {
            alert(error.message);
        });

    const newCol = document.createElement('div');
    for(let i = 0; i < columnList.length; i++) {
        const className = 'col' + (existingCols + i) + ' cell col-box';
        newCol.innerHTML += `<div class="${className}">
                            <div class="col-text">${columnList[i].name.trim()}</div>
                            </div>`;
        console.log(columnList[i]);
        colView.appendChild(newCol);
    }

}


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



// 카드 내용 추가 함수
function addCard(columnElement) {
    const cardText = prompt('카드 내용을 입력하세요:');
    if (cardText !== null && cardText.trim() !== '') {
        const auth = getToken();
        const columnId = columnElement.getAttribute('data-col-id');
        const title = '카드 제목'; // 예시로 고정 값 입력
        const status = '시작전'; // 예시로 고정 값 입력

        fetch(`/api/board/card`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'AccessToken': auth
            },
            body: JSON.stringify({
                title: title,
                status: status,
                content: cardText.trim()
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('카드 추가 실패');
                }
                return response.json();
            })
            .then(data => {
                const newCard = document.createElement('div');
                newCard.className = 'card';

                // 카드 내용 추가
                const cardContent = document.createElement('div');
                cardContent.className = 'card-content';
                cardContent.textContent = data.content; // 백엔드에서 반환한 카드 내용 사용
                newCard.appendChild(cardContent);

                // 토글 버튼 추가
                const toggleButton = document.createElement('button');
                toggleButton.className = 'toggle-comments-btn';
                toggleButton.textContent = '▼ 댓글 보기';
                newCard.appendChild(toggleButton);

                // 수정 버튼 추가
                const editButton = document.createElement('button');
                editButton.className = 'edit-card-btn';
                editButton.textContent = '수정';
                newCard.appendChild(editButton);

                // 삭제 버튼 추가
                const deleteButton = document.createElement('button');
                deleteButton.className = 'delete-card-btn';
                deleteButton.textContent = '삭제';
                newCard.appendChild(deleteButton);

                // 댓글 섹션 추가
                const commentsSection = document.createElement('div');
                commentsSection.className = 'comments';
                commentsSection.style.display = 'none'; // 초기에는 숨김 처리

                // 댓글 달기 버튼 추가
                const commentButton = document.createElement('button');
                commentButton.className = 'add-comment-btn';
                commentButton.textContent = '댓글 달기';
                newCard.appendChild(commentButton);

                // 댓글 달기 버튼 클릭 이벤트 처리
                commentButton.addEventListener('click', function() {
                    const commentText = prompt('댓글 내용을 입력하세요:');
                    if (commentText !== null && commentText.trim() !== '') {
                        const commentElement = document.createElement('div');
                        commentElement.className = 'comment';
                        commentElement.textContent = commentText.trim();
                        commentsSection.appendChild(commentElement);
                    } else {
                        alert('댓글 내용을 입력해주세요.');
                    }
                });

                // 토글 버튼 클릭 이벤트 처리
                toggleButton.addEventListener('click', function() {
                    if (commentsSection.style.display === 'none') {
                        commentsSection.style.display = 'block';
                        toggleButton.textContent = '▲ 댓글 숨기기';
                    } else {
                        commentsSection.style.display = 'none';
                        toggleButton.textContent = '▼ 댓글 보기';
                    }
                });

                editButton.addEventListener('click', function() {
                    const newText = prompt('수정할 카드 내용을 입력하세요:', data.content);
                    if (newText !== null && newText.trim() !== '') {
                        const cardId = data.id; // 수정할 카드의 ID
                        const auth = getToken();

                        fetch(`/api/board/card/${cardId}`, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                                'AccessToken': auth
                            },
                            body: JSON.stringify({
                                content: newText.trim(),
                                title: title.trim(), // 제목 업데이트
                                status: status.trim() // 상태 업데이트
                            })
                        })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('카드 수정 실패');
                                }
                                return response.json();
                            })
                            .then(updatedData => {
                                cardContent.textContent = updatedData.content;
                            })
                            .catch(error => {
                                alert(error.message);
                            });
                    } else {
                        alert('카드 내용을 입력해주세요.');
                    }
                });



                // 삭제 버튼 클릭 이벤트 처리
                deleteButton.addEventListener('click', function() {
                    const confirmDelete = confirm('정말로 이 카드를 삭제하시겠습니까?');
                    if (confirmDelete) {
                        const cardId = data.id; // 삭제할 카드의 ID
                        const auth = getToken();

                        fetch(`/api/board/card/${cardId}`, {
                            method: 'DELETE',
                            headers: {
                                'Content-Type': 'application/json',
                                'AccessToken': auth
                            }
                        })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('카드 삭제 실패');
                                }
                                return response.text();
                            })
                            .then(result => {
                                alert(result); // 삭제 성공 메시지를 alert로 보여줄 수 있음
                                newCard.remove();
                            })
                            .catch(error => {
                                alert(error.message);
                            });
                    }
                });

                // 댓글 섹션 추가
                newCard.appendChild(commentsSection);

                // 카드 요소를 컬럼에 추가
                columnElement.appendChild(newCard);
            })
            .catch(error => {
                alert(error.message);
            });
    } else {
        alert('카드 내용을 입력해주세요.');
    }
}

function getToken() {
    let auth = Cookies.get('AccessToken');
    if(auth === undefined) {
        return '';
    }
    return auth;
}