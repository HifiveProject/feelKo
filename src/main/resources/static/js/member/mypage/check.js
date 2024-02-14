var phoneValidationFailed = false;
var emailValidationFailed = false;

function showDefaultMessage(field) {
    event.preventDefault(); // 폼 제출 방지

    // 유효성 검사에 실패한 적이 없으면 기본 메시지 출력
    if (field === 'phone' && !phoneValidationFailed) {
        document.getElementById(field + 'Default').style.display = 'block';
        document.getElementById(field + 'Error').style.display = 'none';
    } else if (field === 'email' && !emailValidationFailed) {
        document.getElementById(field + 'Default').style.display = 'block';
        document.getElementById(field + 'Error').style.display = 'none';
    }
}

function handleBeforeUnload(event) {
    // 변경사항이 저장되지 않았을 때만 경고 메시지를 표시
    if (status === 'incomplete') {
        var confirmationMessage = '변경사항이 저장되지 않았습니다. 페이지를 벗어나시겠습니까?';
        event.returnValue = confirmationMessage; // 일부 브라우저에서는 이 메시지를 사용자에게 표시
        return confirmationMessage; // 웹 표준을 따르는 브라우저에서는 이 메시지를 사용자에게 표시
    }
}

function submitLoginForm(event) {
    event.preventDefault(); // 폼 기본 제출 방지

    isValid = true;
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
        requestEmailVerification();
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
    if ($('body').data('page') === 'register') {
        // 비밀번호 필드 공백 검사
        var passwordInput = document.getElementById('passwordInput');
        if (!passwordInput.value.trim()) {
            document.getElementById('passwordError').style.display = 'block';
            isValid = false;
        } else {
            document.getElementById('passwordError').style.display = 'none';
        }
    }
    // 이름 필드 공백 검사
    var nameInput = document.getElementById('nameInput');
    if (!nameInput.value.trim()) {
        document.getElementById('nameError').style.display = 'block';
        isValid = false;
    } else {
        document.getElementById('nameError').style.display = 'none';
    }

    // 생일 필드 공백 검사
    var birthdayInput = document.getElementById('birthdayInput');
    if (!birthdayInput.value.trim()) {
        document.getElementById('birthdayError').style.display = 'block'; // 에러 메시지 표시
        isValid = false;
    } else {
        document.getElementById('birthdayError').style.display = 'none'; // 에러 메시지 숨김
    }

    if (!isValid) {
        alert('입력 형식을 확인해 주세요.');
        return false;
    } else {
        requestEmailVerification().then(isDuplicate => {
            if (isDuplicate && (provider == 'KAKAO' || provider == 'NONE') && status == 'incomplete') {
                alert('이미 가입된 이메일입니다.');
                return false; // 중복된 이메일로 인해 폼 제출 방지
            } else {
                // 중복되지 않은 이메일인 경우 폼 제출 진행
                window.removeEventListener('beforeunload', handleBeforeUnload);
                document.getElementById('loginForm').submit();
            }
        }).catch(error=> {
            alert(error);
        })
        ;
    }

}

function requestEmailVerification() {
    return new Promise((resolve, reject) => {
        var email = $('#emailInput').val();
        if (!email) {
            $('#emailEmpty').show();
            reject('이메일 주소를 입력해 주세요.');
            return;
        } else {
            $('#emailEmpty').hide();
        }

        var encodedEmail = encodeURIComponent(email);
        var encodeProvider = encodeURIComponent(provider);
        var encodeStatus = encodeURIComponent(status);
        var url = '/member/email/verification?email=' + encodedEmail + '&provider=' + encodeProvider + '&status=' + encodeStatus;

        $.ajax({
            url: url,
            type: 'GET'
        }).done(function(result) {
            if (result.success) {
                $('#emailEmpty').hide();
                resolve(false); // 중복되지 않음
            } else {
                resolve(true); // 중복됨
            }
        }).fail(function(jqXHR, textStatus) {
            if (jqXHR.status === 409) {
                $('#emailEmpty').text("이미 가입된 이메일입니다.").show();
                resolve(true); // 중복된 이메일
            } else {
                $('#emailEmpty').text("서버 에러가 발생했습니다. 다시 시도해주세요.").show();
                reject('서버 에러가 발생했습니다.');
            }
        });
    });
}

$(document).ready(function () {
    $('#emailInput').on('input', function () {
        var email = $(this).val();

        $('#emailError').hide();

        if (email === '') {
            $('#emailEmpty').text("이메일 주소를 입력해 주세요.").show();
        } else {
            $('#emailEmpty').hide();
            requestEmailVerification(function (isDuplicate) {
                if (isDuplicate) {
                    $('#emailEmpty').text("이미 가입된 이메일입니다.").show();
                    return false;
                } else {
                    $('#emailEmpty').hide();
                }
            });
        }
    });
});
