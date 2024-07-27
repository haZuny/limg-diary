import css from './LoginPage.module.scss'

import {useState} from 'react'

import TitleImage from './img/title_img.png'
import HelpTextImage from './img/help_text.png'

import Header from './../global_component/header/Header'
import Modal from '../global_component/modal/Modal'
import { SingleButton, TextButton } from './../global_component/button/Button'
import SignupModalBody from './SignupModalBody'

function LoginPage(){
    // 모달 제어
    const [signupModalState, setSignupModalState] = useState(false)

    return (
        <div id={css.root_container}>
            <div id={css.container} className={css.container}>
                <Header/>
                {/* 상단 컨테이너 */}
                <div id={css.upper_container} className={css.container}>
                    {/* 공백 */}
                    <div/>
                    {/* 림그일기 */}
                    <div id={css.title_img_box}>
                        <img src={TitleImage}/>
                    </div>
                    {/* 하단 정보 */}
                    <div id={css.help_title_box}>
                        <img src={HelpTextImage}/>
                    </div>
                </div>

                {/* 하단 컨테이너 */}
                <div id={css.lower_container} className={css.container}>
                    <div id={css.input_box} className={css.container}>
                        <input id={css.input} className={css.input} placeholder='이메일'/>
                        <input id={css.input} className={css.input} type='password' placeholder='비밀번호'/>
                    </div>

                    <div id={css.btn_box} className={css.container}>
                        <SingleButton text='로그인'/>
                        <TextButton text='회원가입' func={()=>setSignupModalState(true)}/>
                    </div>
                </div>
            </div>

            {/* 회원가입 모달 */}
            {signupModalState &&
            <Modal title={'회원가입'} body={<SignupModalBody modal_off_handle={()=>setSignupModalState(false)}/>} modalOffHandle={()=>setSignupModalState(false)}/>}
        </div>
    )
}

export default LoginPage