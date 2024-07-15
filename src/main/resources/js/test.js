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

document.querySelector('.board-add').addEventListener('click', function() {
    const text = prompt('Enter text:');
    if (text !== null && text.trim() !== '') {
        const newBoard = document.createElement('div');
        newBoard.className = 'board';
        newBoard.textContent = text.trim();
        document.querySelector('.list-view').appendChild(newBoard);
    }
});

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