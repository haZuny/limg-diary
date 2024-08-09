import css from './LoginPage.module.scss'

import { useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import TitleImage from './img/title_img.png'
import HelpTextImage from './img/help_text.png'

import Header from './../global_component/header/Header'
import Modal from '../global_component/modal/Modal'
import { SingleButton, TextButton } from './../global_component/button/Button'
import SignupModalBody from './SignupModalBody'
import RestApiHelper from '../../Authentication'

function LoginPage() {
    // Navigate
    const navigate = useNavigate()

    // 모달 제어
    const [signupModalState, setSignupModalState] = useState(false)

    // input ref
    const usernameRef = useRef()
    const passwordRef = useRef()

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header nonAuth={true} />
            <div id={css.body_container} className={css.container}>
                {/* 상단 컨테이너 */}
                <div id={css.upper_container} className={css.container}>
                    {/* 공백 */}
                    <div id={css.blank} />
                    {/* 림그일기 */}
                    <div id={css.title_img_box}>
                        <img src={TitleImage} />
                    </div>
                    {/* 하단 정보 */}
                    <div id={css.help_title_box}>
                        <img src={HelpTextImage} />
                    </div>
                </div>

                {/* 하단 컨테이너 */}
                <div id={css.lower_container} className={css.container}>
                    <div id={css.input_box} className={css.container}>
                        <input id={css.input} className={css.input} placeholder='이메일' ref={usernameRef}/>
                        <input id={css.input} className={css.input} type='password' placeholder='비밀번호' ref={passwordRef}/>
                    </div>

                    <div id={css.btn_box} className={css.container}>
                        <SingleButton text='로그인' func={() => {
                            const username = usernameRef.current.value;
                            const password = passwordRef.current.value;

                            // 공백 검사
                            if (username == "" || password == ""){
                                alert("이메일 또는 비밀번호가 공백입니다.")
                                return;
                            }

                            // 로그인
                            RestApiHelper.sendRequest("/user/signin", "POST", {body:{
                                "username": username,
                                "password": password
                            }}).then((res)=>{
                                console.log("[post] user/signin",)
                                if (res != null && res.status == "200"){
                                    localStorage.setItem("Authentication", res.headers['authentication'])
                                    navigate('/')
                                }
                                else{
                                    alert("아이디 또는 패스워드가 일치하지 않습니다.")
                                }
                            })

                            
                        }} />
                        <TextButton text='회원가입' func={() => setSignupModalState(true)} />
                    </div>
                </div>

                {/* 회원가입 모달 */}
                {signupModalState &&
                    <Modal title={'회원가입'} body={<SignupModalBody modal_off_handle={() => setSignupModalState(false)} />} modalOffHandle={() => setSignupModalState(false)} />}

            </div>

        </div>
    )
}

export default LoginPage