// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            location.replace('/articles');
        }
        function fail() {
            location.reload();
        }
        httpRequest('DELETE', `/api/articles/${id}`, null, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search); // 파라미터에서 값 찾기
        let id = params.get('id');
        body = JSON.stringify({ //JavaScript 객체나 배열을 JSON 문자열로 변환하는 메서드입니다
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        Swal.fire({
            title: "수정하시겠습니까?",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#6495ed",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                setTimeout(() => {
                    httpRequest('PUT',`/api/articles/${id}`, body, success, fail);
                }, 1000);
           }
        });
        function success() {
            Swal.fire({
               title: "수정 완료!",
               icon: "success",
               showConfirmButton:false
            });
            location.replace(`/articles/${id}`);
        }
        function fail() {
            alert('ERROR');
            location.replace(`/articles/${id}`);
        }
    });
}
// 유저 정보 수정하기 버튼
const UserModifyButton = document.getElementById('modify-userInfo');
if (UserModifyButton) {
    UserModifyButton.addEventListener('click', event => {
        let nickname = document.getElementById('userNickname').value;
        let reason = document.getElementById('userReason').value;
        let year = document.getElementById('year').value;
        body = JSON.stringify({
            nickname : nickname,
            reason: reason,
            year: year
        });
        function success(){
            Swal.fire({
                position: "center",
                icon: "success",
                title: "정보 수정이 완료 되었습니다.",
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                location.reload();
            });
        }
        function fail(){
            Swal.fire({
                 position: "center",
                 icon: "error",
                 title: "형식에 맞게 다시 입력해주세요.",
                 showConfirmButton: false,
                 timer: 1500
           }).then(() => {
                 location.reload();
           });
        }
        function checkInputs(nickname, reason, year) {
            if (nickname === null || reason === null || year === null) {
                fail();
            } else {
                // 모든 입력이 유효한 경우 success() 메서드 호출 등 추가 작업 수행
                httpRequest('POST' , '/api/myProFil' , body , success , fail);
            }
        }
        checkInputs(nickname,reason,year);
    });
}

// 좋아요 기능
const likeUpButton = document.getElementById('LikeUp-btn');
if (likeUpButton) {
    likeUpButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success(){
            location.reload();
        }
        function fail(){
            location.reload();
        }
        httpRequest('POST',`/api/likes/up/${id}`, null, success, fail)
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        Swal.fire({
            title: "글을 등록 하시겠습니까?",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#6495ed",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "등록 완료!",
                    icon: "success",
                    showConfirmButton:false
                });
           }
           setTimeout(() => {
                httpRequest('POST','/api/articles', body, success, fail)
           }, 1000);
        });
        function success() {
            location.replace('/articles');
        };
        function fail() {
            location.replace('/articles');
        };
    });
}


// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        else {
            alert(response.status);
            return fail();
        }
    });
}