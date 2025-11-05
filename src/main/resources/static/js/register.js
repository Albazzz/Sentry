// ===== CONSTANTS =====
const CONFIG = {
    EMAIL_CHECK_DEBOUNCE_MS: 500,
    OTP_RESEND_COOLDOWN_SECONDS: 60,
    MESSAGE_AUTO_HIDE_MS: 8000,
    OTP_LENGTH: 6,
    MIN_PASSWORD_LENGTH: 6,
    MAX_CERTIFICATE_FILE_SIZE: 10 * 1024 * 1024, // 10MB
    
    // Selectors
    SELECTORS: {
        VALIDATION_MSG_CLASS: 'validation-msg',
        REGISTER_FORM: 'registerForm',
        OTP_FORM: 'otpForm',
        EMAIL: 'email',
        PASSWORD: 'password',
        CONFIRM_PASSWORD: 'confirmPassword',
        FULL_NAME: 'fullName',
        PHONE_NUMBER: 'phoneNumber',
        ROLE: 'role',
        CERTIFICATE: 'certificate',
        CERTIFICATE_BOX: 'certificateBox',
        OTP_CODE: 'otpCode',
        VERIFY_BTN: 'verifyBtn',
        RESEND_OTP_BTN: 'resendOtpBtn',
        COUNTDOWN_TIMER: 'countdownTimer',
        OTP_EMAIL_DISPLAY: 'otpEmailDisplay',
        CSRF_TOKEN: 'meta[name="_csrf"]',
        CSRF_HEADER: 'meta[name="_csrf_header"]',
        JAPANESE_LEVEL: 'japaneseLevel',
        GENDER: 'gender'
    },
    
    // Values
    VALUES: {
        ROLE_TEACHER: 'teacher',
        ROLE_STUDENT: 'student',
        GENDER_DEFAULT: 'Khác',
        ROLE_ID_TEACHER: 3,
        ROLE_ID_STUDENT: 1
    },
    
    // Messages
    MESSAGES: {
        EMAIL_INVALID: 'Email không hợp lệ',
        PASSWORD_TOO_SHORT: 'Mật khẩu phải có ít nhất 6 ký tự',
        PASSWORD_WEAK: 'Mật khẩu phải chứa ít nhất 1 chữ cái và 1 số',
        PASSWORD_WEAK_WARNING: 'Mật khẩu yếu - Nên thêm ký tự đặc biệt',
        PASSWORD_MEDIUM: 'Mật khẩu trung bình',
        PASSWORD_STRONG: 'Mật khẩu mạnh',
        PASSWORD_MISMATCH: 'Mật khẩu xác nhận không khớp',
        PASSWORD_MATCH: 'Mật khẩu khớp',
        PHONE_INVALID: 'Số điện thoại không hợp lệ (VD: 0912345678)',
        PHONE_VALID: 'Số điện thoại hợp lệ',
        NAME_TOO_SHORT: 'Họ tên phải có ít nhất 2 ký tự',
        NAME_TOO_LONG: 'Họ tên không được quá 100 ký tự',
        NAME_NO_NUMBERS: 'Họ tên không được chứa số',
        NAME_VALID: 'Họ tên hợp lệ',
        EMAIL_ALREADY_EXISTS: 'Email này đã được sử dụng',
        CONFIRM_PASSWORD_MISMATCH: 'Mật khẩu xác nhận không khớp!',
        ROLE_REQUIRED: 'Vui lòng chọn vai trò (Học sinh/Giáo viên)!',
        CERTIFICATE_REQUIRED: 'Vui lòng upload chứng chỉ giảng dạy!',
        FIELDS_REQUIRED: 'Vui lòng điền đầy đủ thông tin bắt buộc!',
        OTP_INVALID_LENGTH: 'Mã OTP phải có 6 số!',
        EMAIL_INVALID_OTP: 'Email không hợp lệ!',
        CSRF_TOKEN_MISSING: 'Lỗi bảo mật: Không tìm thấy CSRF token. Vui lòng tải lại trang.',
        SERVER_ERROR: 'Có lỗi xảy ra khi kết nối đến server. Vui lòng thử lại!',
        OTP_ERROR: 'Có lỗi xảy ra. Vui lòng thử lại!'
    },
    
    // Endpoints
    ENDPOINTS: {
        CHECK_EMAIL: '/api/validation/check-email',
        REGISTER: '/register/user',
        OTP_VERIFY: '/otp/verify',
        OTP_RESEND: '/otp/resend'
    }
};

// ============ HELPER FUNCTIONS - GLOBAL SCOPE ============

// Helper function: Validate email format
function isValidEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
}

// Helper function: Calculate password strength
function calculatePasswordStrength(password) {
    let strength = 0;

    if (password.length >= 6) strength++;
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++; // Chữ hoa
    if (/[a-z]/.test(password)) strength++; // Chữ thường
    if (/\d/.test(password)) strength++;    // Số
    if (/[@$!%*#?&]/.test(password)) strength++; // Ký tự đặc biệt

    if (strength < 3) return 'weak';
    if (strength < 5) return 'medium';
    return 'strong';
}

// Helper function: Clear validation message
function clearValidationMessage(inputElement) {
    const existingMsg = inputElement.parentElement.querySelector(`.${CONFIG.SELECTORS.VALIDATION_MSG_CLASS}`);
    if (existingMsg) existingMsg.remove();
    inputElement.style.borderColor = '';
}

// Helper function: Show validation message under field
function showFieldValidation(inputElement, message, type) {
    // Remove existing message
    clearValidationMessage(inputElement);

    // Create new message
    const msgDiv = document.createElement('div');
    msgDiv.className = CONFIG.SELECTORS.VALIDATION_MSG_CLASS;
    msgDiv.textContent = message;
    msgDiv.style.fontSize = '12px';
    msgDiv.style.marginTop = '5px';
    msgDiv.style.marginLeft = '5px';
    msgDiv.setAttribute('role', 'alert');
    msgDiv.setAttribute('aria-live', 'polite');

    if (type === 'error') {
        msgDiv.style.color = '#dc3545';
        inputElement.style.borderColor = '#dc3545';
    } else if (type === 'success') {
        msgDiv.style.color = '#28a745';
        inputElement.style.borderColor = '#28a745';
    } else if (type === 'warning') {
        msgDiv.style.color = '#ffc107';
        inputElement.style.borderColor = '#ffc107';
    }

    inputElement.parentElement.appendChild(msgDiv);
}

// ===== UNIFIED MESSAGE DISPLAY FUNCTION =====
/**
 * Hiển thị message (thay thế showMessage và showOtpMessage)
 * @param message - nội dung message
 * @param type - 'success' hoặc 'error'
 * @param targetElementId - id của element để hiển thị (mặc định 'message')
 */
function displayMessage(message, type, targetElementId = 'message') {
    let target = document.getElementById(targetElementId);

    if (!target) {
        target = document.createElement('div');
        target.id = targetElementId;
        const formId = targetElementId === 'message' ? CONFIG.SELECTORS.REGISTER_FORM : CONFIG.SELECTORS.OTP_FORM;
        const form = document.getElementById(formId);
        if (form && form.parentElement) {
            form.parentElement.insertBefore(target, form);
        } else {
            document.body.prepend(target);
        }
    }

    target.textContent = message;
    target.style.padding = '10px';
    target.style.borderRadius = '8px';
    target.style.textAlign = 'center';
    target.style.fontSize = '14px';
    target.style.fontWeight = '500';
    target.style.marginTop = '10px';

    if (type === 'success') {
        target.style.backgroundColor = '#d4edda';
        target.style.color = '#155724';
        target.style.border = '1px solid #c3e6cb';
    } else if (type === 'error') {
        target.style.backgroundColor = '#f8d7da';
        target.style.color = '#721c24';
        target.style.border = '1px solid #f5c6cb';
    }

    // Tự động ẩn sau CONFIG.MESSAGE_AUTO_HIDE_MS ms
    setTimeout(() => {
        target.textContent = '';
        target.style.padding = '0';
    }, CONFIG.MESSAGE_AUTO_HIDE_MS);
}

// Helper function: Get CSRF token with error handling
function getCsrfToken() {
    const tokenEl = document.querySelector(CONFIG.SELECTORS.CSRF_TOKEN);
    const headerEl = document.querySelector(CONFIG.SELECTORS.CSRF_HEADER);
    
    if (!tokenEl || !headerEl) {
        return { 
            token: null, 
            header: null, 
            error: true 
        };
    }
    
    const token = tokenEl.getAttribute('content');
    const header = headerEl.getAttribute('content');
    
    if (!token || !header) {
        return { 
            token: null, 
            header: null, 
            error: true 
        };
    }
    
    return {
        token: token,
        header: header,
        error: false
    };
}

// ===== VALIDATION FUNCTIONS - GLOBAL SCOPE =====

// Validate password match
function validatePasswordMatch(passwordInput, confirmPasswordInput) {
    const password = passwordInput.value;
    const confirmPassword = confirmPasswordInput.value;

    if (confirmPassword.length === 0) return;

    if (password !== confirmPassword) {
        showFieldValidation(confirmPasswordInput, CONFIG.MESSAGES.PASSWORD_MISMATCH, 'error');
        confirmPasswordInput.dataset.valid = 'false';
    } else {
        showFieldValidation(confirmPasswordInput, CONFIG.MESSAGES.PASSWORD_MATCH, 'success');
        confirmPasswordInput.dataset.valid = 'true';
    }
}

// ===== OTP COUNTDOWN MODULE =====
const OtpCountdown = {
    interval: null,
    
    start() {
        let countdown = CONFIG.OTP_RESEND_COOLDOWN_SECONDS;
        const resendOtpBtn = document.getElementById(CONFIG.SELECTORS.RESEND_OTP_BTN);
        const countdownTimer = document.getElementById(CONFIG.SELECTORS.COUNTDOWN_TIMER);
        
        if (resendOtpBtn) resendOtpBtn.disabled = true;
        if (this.interval) clearInterval(this.interval);

        this.interval = setInterval(() => {
            if (countdownTimer) {
                countdownTimer.textContent = `⏰ Vui lòng đợi ${countdown} giây trước khi gửi lại mã OTP.`;
            }

            countdown--;

            if (countdown < 0) {
                clearInterval(this.interval);
                this.interval = null;
                if (resendOtpBtn) {
                    resendOtpBtn.disabled = false;
                    resendOtpBtn.style.background = 'transparent';
                }
                if (countdownTimer) {
                    countdownTimer.textContent = 'Bạn có thể gửi lại mã OTP.';
                    countdownTimer.style.color = 'green';
                }
            }
        }, 1000);
    }
};

// Export for external use
window.startOtpCountdown = () => OtpCountdown.start();

// ===== SINGLE DOMContentLoaded - ALL INITIALIZATION =====
document.addEventListener("DOMContentLoaded", function () {
    // Clean URL from password parameters
    if (window.location.search && /password=|confirmPassword=/.test(window.location.search)) {
        try {
            window.history.replaceState({}, document.title, window.location.pathname);
        } catch (_) {}
    }

    // ===== 1. ANIMATION HANDLERS =====
    const container = document.querySelector('.container');
    const registerBtn = document.querySelector('.register-btn');
    const loginBtn = document.querySelector('.login-btn');

    if (registerBtn) {
        registerBtn.addEventListener('click', (e) => {
            e.preventDefault();
            if (container) container.classList.add('active');
        });
    }

    if (loginBtn) {
        loginBtn.addEventListener('click', (e) => {
            e.preventDefault();
            window.location.href = '/login';
        });
    }

    // ===== 2. CERTIFICATE HANDLER =====
    const roleSelect = document.getElementById(CONFIG.SELECTORS.ROLE);
    const certificateBox = document.getElementById(CONFIG.SELECTORS.CERTIFICATE_BOX);
    const certificateInput = document.getElementById(CONFIG.SELECTORS.CERTIFICATE);

    if (roleSelect && certificateBox) {
        roleSelect.addEventListener("change", function () {
            const isTeacher = this.value === CONFIG.VALUES.ROLE_TEACHER;
            certificateBox.style.display = isTeacher ? "block" : "none";
            if (certificateInput) {
                certificateInput.required = isTeacher;
                if (!isTeacher) certificateInput.value = "";
            }
        });
    }

    if (certificateInput) {
        certificateInput.addEventListener("change", function() {
            const file = this.files[0];
            if (file) {
                if (file.size > CONFIG.MAX_CERTIFICATE_FILE_SIZE) {
                    alert("File quá lớn! Kích thước tối đa là 10MB. File hiện tại: " +
                        (file.size / (1024 * 1024)).toFixed(2) + "MB");
                    this.value = "";
                    return;
                }
                if (!file.name.toLowerCase().endsWith('.pdf')) {
                    alert("Chỉ chấp nhận file PDF! File hiện tại: " + file.name);
                    this.value = "";
                    return;
                }
                console.log("File hợp lệ:", file.name, "Size:", (file.size / 1024).toFixed(2) + "KB");
            }
        });
    }

    // ===== 3. REALTIME VALIDATION =====
    const emailInput = document.getElementById(CONFIG.SELECTORS.EMAIL);
    const passwordInput = document.getElementById(CONFIG.SELECTORS.PASSWORD);
    const confirmPasswordInput = document.getElementById(CONFIG.SELECTORS.CONFIRM_PASSWORD);
    const phoneInput = document.getElementById(CONFIG.SELECTORS.PHONE_NUMBER);
    const fullNameInput = document.getElementById(CONFIG.SELECTORS.FULL_NAME);
    
    let emailCheckTimeout = null;

    // Email validation
    if (emailInput) {
        emailInput.addEventListener('input', function() {
            clearTimeout(emailCheckTimeout);
            const email = this.value.trim();
            if (email.length === 0) return;

            emailCheckTimeout = setTimeout(async () => {
                if (!isValidEmail(email)) {
                    showFieldValidation(emailInput, CONFIG.MESSAGES.EMAIL_INVALID, 'error');
                    return;
                }

                try {
                    const response = await fetch(`${CONFIG.ENDPOINTS.CHECK_EMAIL}?email=${encodeURIComponent(email)}`);
                    const result = await response.json();

                    if (result.exists) {
                        showFieldValidation(emailInput, result.message, 'error');
                        emailInput.dataset.valid = 'false';
                    } else {
                        showFieldValidation(emailInput, result.message, 'success');
                        emailInput.dataset.valid = 'true';
                    }
                } catch (error) {
                    console.error('Error checking email:', error);
                }
            }, CONFIG.EMAIL_CHECK_DEBOUNCE_MS);
        });
    }

    // Password validation
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            const password = this.value;
            if (password.length === 0) return;

            if (password.length < CONFIG.MIN_PASSWORD_LENGTH) {
                showFieldValidation(passwordInput, CONFIG.MESSAGES.PASSWORD_TOO_SHORT, 'error');
                passwordInput.dataset.valid = 'false';
            } else if (!/(?=.*[A-Za-z])(?=.*\d)/.test(password)) {
                showFieldValidation(passwordInput, CONFIG.MESSAGES.PASSWORD_WEAK, 'error');
                passwordInput.dataset.valid = 'false';
            } else {
                const strength = calculatePasswordStrength(password);
                if (strength === 'weak') {
                    showFieldValidation(passwordInput, CONFIG.MESSAGES.PASSWORD_WEAK_WARNING, 'warning');
                    passwordInput.dataset.valid = 'false';
                } else if (strength === 'medium') {
                    showFieldValidation(passwordInput, CONFIG.MESSAGES.PASSWORD_MEDIUM, 'success');
                    passwordInput.dataset.valid = 'true';
                } else {
                    showFieldValidation(passwordInput, CONFIG.MESSAGES.PASSWORD_STRONG, 'success');
                    passwordInput.dataset.valid = 'true';
                }
            }

            if (confirmPasswordInput && confirmPasswordInput.value) {
                validatePasswordMatch(passwordInput, confirmPasswordInput);
            }
        });
    }

    // Confirm password validation
    if (confirmPasswordInput) {
        confirmPasswordInput.addEventListener('input', () => {
            validatePasswordMatch(passwordInput, confirmPasswordInput);
        });
    }

    // Phone validation
    if (phoneInput) {
        phoneInput.addEventListener('input', function() {
            const phone = this.value.trim();
            if (phone.length === 0) return;

            if (!/^(\+84|0)[0-9]{8,9}$/.test(phone)) {
                showFieldValidation(phoneInput, CONFIG.MESSAGES.PHONE_INVALID, 'error');
                phoneInput.dataset.valid = 'false';
            } else {
                showFieldValidation(phoneInput, CONFIG.MESSAGES.PHONE_VALID, 'success');
                phoneInput.dataset.valid = 'true';
            }
        });
    }

    // Full name validation
    if (fullNameInput) {
        fullNameInput.addEventListener('input', function() {
            const name = this.value.trim();
            if (name.length === 0) return;

            if (name.length < 2) {
                showFieldValidation(fullNameInput, CONFIG.MESSAGES.NAME_TOO_SHORT, 'error');
                fullNameInput.dataset.valid = 'false';
            } else if (name.length > 100) {
                showFieldValidation(fullNameInput, CONFIG.MESSAGES.NAME_TOO_LONG, 'error');
                fullNameInput.dataset.valid = 'false';
            } else if (/\d/.test(name)) {
                showFieldValidation(fullNameInput, CONFIG.MESSAGES.NAME_NO_NUMBERS, 'error');
                fullNameInput.dataset.valid = 'false';
            } else {
                showFieldValidation(fullNameInput, CONFIG.MESSAGES.NAME_VALID, 'success');
                fullNameInput.dataset.valid = 'true';
            }
        });
    }

    // ===== 4. REGISTRATION FORM HANDLER =====
    const registerForm = document.getElementById(CONFIG.SELECTORS.REGISTER_FORM) || 
                        document.querySelector('form[action="/signup"], form[action="/register/user"]');

    if (registerForm) {
        if (!registerForm.getAttribute('method') || registerForm.getAttribute('method').toLowerCase() !== 'post') {
            registerForm.setAttribute('method', 'post');
        }
        if (registerForm.getAttribute('action') !== CONFIG.ENDPOINTS.REGISTER) {
            registerForm.setAttribute('action', CONFIG.ENDPOINTS.REGISTER);
        }

        registerForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const password = document.getElementById(CONFIG.SELECTORS.PASSWORD).value;
            const confirmPassword = document.getElementById(CONFIG.SELECTORS.CONFIRM_PASSWORD).value;
            const role = document.getElementById(CONFIG.SELECTORS.ROLE).value;
            const certificateInput = document.getElementById(CONFIG.SELECTORS.CERTIFICATE);

            if (password !== confirmPassword) {
                displayMessage(CONFIG.MESSAGES.CONFIRM_PASSWORD_MISMATCH, 'error');
                return;
            }

            if (!role) {
                displayMessage(CONFIG.MESSAGES.ROLE_REQUIRED, 'error');
                return;
            }

            if (role === CONFIG.VALUES.ROLE_TEACHER && (!certificateInput.files || certificateInput.files.length === 0)) {
                displayMessage(CONFIG.MESSAGES.CERTIFICATE_REQUIRED, 'error');
                return;
            }

            // Check validation status của các field
            if (emailInput && emailInput.dataset.valid !== 'true') {
                displayMessage(CONFIG.MESSAGES.EMAIL_INVALID + '!', 'error');
                emailInput.focus();
                return;
            }

            if (passwordInput && passwordInput.dataset.valid !== 'true') {
                displayMessage(CONFIG.MESSAGES.PASSWORD_WEAK + '!', 'error');
                passwordInput.focus();
                return;
            }

            if (confirmPasswordInput && confirmPasswordInput.dataset.valid !== 'true') {
                displayMessage(CONFIG.MESSAGES.CONFIRM_PASSWORD_MISMATCH + '!', 'error');
                confirmPasswordInput.focus();
                return;
            }

            if (fullNameInput && fullNameInput.dataset.valid !== 'true') {
                displayMessage(CONFIG.MESSAGES.NAME_VALID + ' là bắt buộc!', 'error');
                fullNameInput.focus();
                return;
            }

            if (phoneInput && phoneInput.value.trim().length > 0 && phoneInput.dataset.valid !== 'true') {
                displayMessage(CONFIG.MESSAGES.PHONE_INVALID + '!', 'error');
                phoneInput.focus();
                return;
            }

            const formData = {
                email: document.getElementById(CONFIG.SELECTORS.EMAIL).value.trim(),
                password: password,
                fullName: document.getElementById(CONFIG.SELECTORS.FULL_NAME).value.trim(),
                phoneNumber: document.getElementById(CONFIG.SELECTORS.PHONE_NUMBER).value.trim() || null,
                japaneseLevel: document.getElementById(CONFIG.SELECTORS.JAPANESE_LEVEL).value || null,
                gender: document.getElementById(CONFIG.SELECTORS.GENDER).value || CONFIG.VALUES.GENDER_DEFAULT,
                roleID: role === CONFIG.VALUES.ROLE_TEACHER ? CONFIG.VALUES.ROLE_ID_TEACHER : CONFIG.VALUES.ROLE_ID_STUDENT
            };

            if (!formData.email || !formData.password || !formData.fullName) {
                displayMessage(CONFIG.MESSAGES.FIELDS_REQUIRED, 'error');
                return;
            }

            if (formData.password.length < CONFIG.MIN_PASSWORD_LENGTH) {
                displayMessage(CONFIG.MESSAGES.PASSWORD_TOO_SHORT + '!', 'error');
                return;
            }

            if (!isValidEmail(formData.email)) {
                displayMessage(CONFIG.MESSAGES.EMAIL_INVALID + '!', 'error');
                return;
            }

            const submitBtn = registerForm.querySelector('button[type="submit"]');
            submitBtn.disabled = true;
            submitBtn.textContent = 'Đang đăng ký...';

            try {
                const csrf = getCsrfToken();
                if (csrf.error) {
                    displayMessage(CONFIG.MESSAGES.CSRF_TOKEN_MISSING, 'error');
                    submitBtn.disabled = false;
                    submitBtn.textContent = 'Đăng ký';
                    return;
                }

                const response = await fetch(CONFIG.ENDPOINTS.REGISTER, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-Requested-With': 'XMLHttpRequest',
                        [csrf.header]: csrf.token
                    },
                    body: JSON.stringify(formData)
                });

                let result = {};
                try {
                    result = await response.json();
                } catch (_) {}

                if (response.ok && result.success) {
                    registerForm.style.display = 'none';
                    const otpForm = document.getElementById(CONFIG.SELECTORS.OTP_FORM);
                    if (otpForm) {
                        otpForm.style.display = 'block';
                        const otpEmailDisplay = document.getElementById(CONFIG.SELECTORS.OTP_EMAIL_DISPLAY);
                        if (otpEmailDisplay) {
                            otpEmailDisplay.textContent = formData.email;
                        }

                        sessionStorage.setItem('pendingEmail', formData.email);
                        displayMessage(result.message || 'Vui lòng kiểm tra email để lấy mã OTP.', 'success', 'otpMessage');
                        OtpCountdown.start();
                    }
                } else {
                    let errorMessage = result.message || 'Đăng ký thất bại!';

                    if (response.status === 409) {
                        errorMessage = CONFIG.MESSAGES.EMAIL_ALREADY_EXISTS + '. Vui lòng chọn email khác.';
                        const emailInput = document.getElementById(CONFIG.SELECTORS.EMAIL);
                        if (emailInput) {
                            emailInput.style.borderColor = '#dc3545';
                            emailInput.focus();
                            showFieldValidation(emailInput, CONFIG.MESSAGES.EMAIL_ALREADY_EXISTS, 'error');
                            emailInput.dataset.valid = 'false';
                        }
                    } else if (response.status === 400) {
                        errorMessage = 'Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.';
                    } else if (response.status === 500) {
                        errorMessage = CONFIG.MESSAGES.SERVER_ERROR;
                    }

                    displayMessage(errorMessage, 'error');
                    submitBtn.disabled = false;
                    submitBtn.textContent = 'Đăng ký';
                    window.scrollTo({ top: 0, behavior: 'smooth' });
                }
            } catch (error) {
                console.error('Error:', error);
                displayMessage(CONFIG.MESSAGES.SERVER_ERROR, 'error');
                submitBtn.disabled = false;
                submitBtn.textContent = 'Đăng ký';
            }
        });
    }

    // ===== 5. OTP VERIFICATION HANDLER =====
    const otpForm = document.getElementById(CONFIG.SELECTORS.OTP_FORM);
    const otpCodeInput = document.getElementById(CONFIG.SELECTORS.OTP_CODE);
    const verifyBtn = document.getElementById(CONFIG.SELECTORS.VERIFY_BTN);
    const resendOtpBtn = document.getElementById(CONFIG.SELECTORS.RESEND_OTP_BTN);

    if (otpForm && verifyBtn) {
        otpForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const email = sessionStorage.getItem('pendingEmail');
            const otpCode = otpCodeInput.value.trim();

            if (!email) {
                displayMessage(CONFIG.MESSAGES.EMAIL_INVALID_OTP, 'error', 'otpMessage');
                return;
            }

            if (otpCode.length !== CONFIG.OTP_LENGTH) {
                displayMessage(CONFIG.MESSAGES.OTP_INVALID_LENGTH, 'error', 'otpMessage');
                return;
            }

            verifyBtn.disabled = true;
            verifyBtn.textContent = 'Đang xác thực...';

            try {
                const csrf = getCsrfToken();
                const headers = {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Requested-With': 'XMLHttpRequest'
                };
                if (!csrf.error) {
                    headers[csrf.header] = csrf.token;
                }

                const response = await fetch(CONFIG.ENDPOINTS.OTP_VERIFY, {
                    method: 'POST',
                    headers: headers,
                    body: new URLSearchParams({ email: email, otp: otpCode })
                });

                const result = await response.json();

                if (result.success) {
                    displayMessage('Xác thực thành công! Tài khoản đã được kích hoạt.', 'success', 'otpMessage');
                    verifyBtn.style.display = 'none';
                    setTimeout(() => {
                        sessionStorage.removeItem('pendingEmail');
                        window.location.href = '/login';
                    }, 2000);
                } else {
                    displayMessage(result.message || 'Mã OTP không đúng!', 'error', 'otpMessage');
                    verifyBtn.disabled = false;
                    verifyBtn.textContent = 'Xác thực';
                    otpCodeInput.value = '';
                }
            } catch (error) {
                console.error('Error:', error);
                displayMessage(CONFIG.MESSAGES.OTP_ERROR, 'error', 'otpMessage');
                verifyBtn.disabled = false;
                verifyBtn.textContent = 'Xác thực';
            }
        });
    }

    if (resendOtpBtn) {
        resendOtpBtn.addEventListener('click', async function() {
            const email = sessionStorage.getItem('pendingEmail');

            if (!email) {
                displayMessage(CONFIG.MESSAGES.EMAIL_INVALID_OTP, 'error', 'otpMessage');
                return;
            }

            resendOtpBtn.disabled = true;
            resendOtpBtn.textContent = 'Đang gửi...';

            try {
                const csrf = getCsrfToken();
                const headers = {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Requested-With': 'XMLHttpRequest'
                };
                if (!csrf.error) {
                    headers[csrf.header] = csrf.token;
                }

                const response = await fetch(CONFIG.ENDPOINTS.OTP_RESEND, {
                    method: 'POST',
                    headers: headers,
                    body: new URLSearchParams({ email: email })
                });

                const result = await response.json();

                if (response.ok && result.success) {
                    displayMessage('Đã gửi lại mã OTP. Vui lòng kiểm tra email.', 'success', 'otpMessage');
                    OtpCountdown.start();
                } else {
                    displayMessage(result.message || 'Gửi lại mã OTP thất bại!', 'error', 'otpMessage');
                    resendOtpBtn.disabled = false;
                    resendOtpBtn.textContent = 'Gửi lại mã OTP';
                }
            } catch (error) {
                console.error('Error:', error);
                displayMessage(CONFIG.MESSAGES.OTP_ERROR, 'error', 'otpMessage');
                resendOtpBtn.disabled = false;
                resendOtpBtn.textContent = 'Gửi lại mã OTP';
            }
        });
    }
});
