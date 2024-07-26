import css from './LoginPage.module.scss'

import Header from './../global_component/header/Header'

import TitleImage from './img/title_img.png'
import HelpTextImage from './img/help_text.png'


function LoginPage(){
    return (
        <div id={css.root_container} className={css.container}>
            <Header id={css.header}/>

            {/* 상단 컨테이너 */}
            <div id={css.upper_container} className={css.container}>
                <div id={css.title_img_box}>
                    <img src={TitleImage}/>
                </div>
                <div id={css.help_title_box}>
                    <img src={HelpTextImage}/>
                </div>
            </div>

            {/* 하단 컨테이너 */}
            <div id={css.lower_container} className={css.container}>
                <div id={css.input_box} className={css.container}>
                    <input className={css.input} placeholder='이메일'/>
                    <input className={css.input} type='password' placeholder='비밀번호'/>
                </div>

                <div id={css.btn_box} className={css.container}>
                    <button id={css.login_button}>로그인</button>
                    <button id={css.signup_button}>회원가입</button>
                </div>
            </div>
        </div>
    )
}

export default LoginPage