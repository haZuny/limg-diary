import {fullScreenStyle} from './../Global'

import css from './LoginPage.module.scss'

import Header from './../global_component/header/Header'

import TitleImage from './img/title_img.png'



function LoginPage(){
    return <div id={css.container} className={css.container} style={fullScreenStyle}>
        <Header id={css.header}/>

        {/* 상단 컨테이너 */}
        <div className={css.container} id={css.upper_container}>
            <div/>
            <div id={css.title_img_box} className={css.container}>
                <img src={TitleImage}/>
            </div>
            <div id={css.help_title_box}>
                This app is developped by Hayden & OnebinGo<br/>
                'Karlo' of Kakao supports
            </div>
        </div>

        {/* 하단 컨테이너 */}
        <div id={css.lower_container}>
        </div>
    </div>
}

export default LoginPage