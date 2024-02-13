var phoneValidationFailed = false;
var emailValidationFailed = false;
function showDefaultMessage(field) {
    // 유효성 검사에 실패한 적이 없으면 기본 메시지 출력
    if (field === 'phone' && !phoneValidationFailed) {
        document.getElementById(field + 'Default').style.display = 'block';
        document.getElementById(field + 'Error').style.display = 'none';
    } else if (field === 'email' && !emailValidationFailed) {
        document.getElementById(field + 'Default').style.display = 'block';
        document.getElementById(field + 'Error').style.display = 'none';
    }
}

function submitLoginForm(event) {
    var isValid = true; // 폼 유효성 기본값

    // 이메일 유효성 검사
    var emailInput = document.getElementById('emailInput');
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(emailInput.value)) {
        // 유효성 검사 실패
        document.getElementById('emailError').style.display = 'block';
        document.getElementById('emailDefault').style.display = 'none'; // 기본 메시지 숨기기
        emailValidationFailed = true; // 유효성 검사 실패 처리
        isValid = false;
    } else {
        // 유효성 검사에 성공하면 기본 메시지를 숨깁니다(이미 에러가 발생했었다면).
        document.getElementById('emailError').style.display = 'none';
        if (!emailValidationFailed) {
            // 에러가 한 번도 발생하지 않았다면 기본 메시지를 계속 보여줍니다.
            document.getElementById('emailDefault').style.display = 'block';
        }
    }

    // 전화번호 유효성 검사
    var phoneInput = document.getElementById('phoneInput');
    var phonePattern = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
    if (!phonePattern.test(phoneInput.value)) {
        // 유효성 검사 실패
        document.getElementById('phoneError').style.display = 'block';
        document.getElementById('phoneDefault').style.display = 'none'; // 기본 메시지 숨기기
        phoneValidationFailed = true; // 유효성 검사 실패 처리
        isValid = false;
    } else {
        // 유효성 검사에 성공하면 기본 메시지를 숨깁니다(이미 에러가 발생했었다면).
        document.getElementById('phoneError').style.display = 'none';
        if (!phoneValidationFailed) {
            // 에러가 한 번도 발생하지 않았다면 기본 메시지를 계속 보여줍니다.
            document.getElementById('phoneDefault').style.display = 'block';
        }
    }

    // 이름 필드 공백 검사
    var nameInput = document.getElementById('nameInput');
    if (!nameInput.value.trim()) {
        document.getElementById('nameError').style.display = 'block';
        isValid = false; // 유효하지 않다면 false
    } else {
        document.getElementById('nameError').style.display = 'none';
    }

    // 생일 필드 공백 검사
    var birthdayInput = document.getElementById('birthdayInput');
    if (!birthdayInput.value.trim()) {
        document.getElementById('birthdayError').style.display = 'block'; // 에러 메시지 표시
        isValid = false; // 유효하지 않다면 false
    } else {
        document.getElementById('birthdayError').style.display = 'none'; // 에러 메시지 숨김
    }

    // 유효하지 않은 입력이 있으면 폼 제출 방지
    if (!isValid) {
        event.preventDefault(); // 폼 제출 방지
        alert('입력 형식을 확인해 주세요.');
        return false; // onsubmit return false 반환
    } else {
        window.removeEventListener('beforeunload', handleBeforeUnload);
    }
    return true;
}
