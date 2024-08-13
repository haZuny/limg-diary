import css from './SignupModalBody.module.scss'

import { SingleSmallButton, TextButton } from '../global_component/button/Button'
import { useRef } from 'react'
import RestApiHelper from '../../Authentication'
import { useNavigate } from 'react-router-dom'

function SignupModalBody({ modal_off_handle }) {

    // ref
    const emailRef = useRef()
    const nicknameRef = useRef()
    const passwordRef = useRef()
    const passwordCheckRef = useRef()

    // navigate
    const navigate = useNavigate()

    return (
        <div id={css.modal_body_box} className={css.container}>

            <div id={css.input_box} className={css.container}>
                <input className={css.input} placeholder='이메일' ref={emailRef} />
                <input className={css.input} placeholder='닉네임' ref={nicknameRef} />
                <input className={css.input} placeholder='패스워드' ref={passwordRef} type='password' />
                <input className={css.input} placeholder='패스워드 확인' ref={passwordCheckRef} type='password' />
            </div>

            <div id={css.button_box} className={css.container}>
                <SingleSmallButton text={'회원가입'} func={async (e) => {
                    const email = emailRef.current.value;
                    const nickname = nicknameRef.current.value;
                    const password = passwordRef.current.value;
                    const passwordCheck = passwordCheckRef.current.value;

                    // null check
                    if (email == '' || nickname == '' || password == '' || passwordCheck == '') {
                        alert("공백인 항목이 존재합니다.");
                        return;
                    }

                    // email form check
                    const email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
                    if (!email.match(email_regex)) {
                        alert('이메일 형식이 올바르지 않습니다.')
                        return;
                    }

                    // password check
                    if (password != passwordCheck) {
                        alert('패스워드가 일치하지 않습니다.')
                        return;
                    }

                    const res = await RestApiHelper.sendRequest("/user/signup", "POST", {
                        body: {
                            'username': email,
                            'nickname': nickname,
                            'password': password,
                            'password_check': passwordCheck
                        }
                    })
                    console.log("[post] /user/signup", res)
                    if (res != null && res.status == '201') {
                        alert('회원가입 완료')
                        modal_off_handle()
                        navigate('/login', { replace: true })
                    }
                    else {
                        alert('동일한 이메일이 존재합니다.')
                    }


                }} />
                <TextButton text={'취소'} func={modal_off_handle}></TextButton>
            </div>
        </div>
    )
}

export default SignupModalBody