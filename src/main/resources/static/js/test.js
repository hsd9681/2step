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
// 보드 관련 코드
document.querySelector('.board-add').addEventListener('click', function() {
    const title = prompt('보드 제목을 입력하세요:');
    const description = prompt('보드 한 줄 설명을 입력하세요:');

    if (title !== null && title.trim() !== '' && description !== null && description.trim() !== '') {
        const boardData = {
            title: title.trim(),
            description: description.trim()
        };

        // 서버로 데이터를 전송하는 Ajax 요청
        fetch('/api/board', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
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
//보드관련코드
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