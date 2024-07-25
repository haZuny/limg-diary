import css from './Header.module.scss'

import logo_icon from './../../resource/img/logo.png'
import profile from './profile.jpeg'

const screenStyle = {
    width : `${window.innerWidth}px`
}


function Header({authorized}){
    return <div className={css.container} id={css.container} style={screenStyle}>
        <div className={css.container} id={css.title_container}>
            <div id={css.logo_box}><img src={logo_icon}/></div>
            <div className={css.title_box} id={css.main_title}>림그일기</div>
            <div className={css.title_box} id={css.sub_title}>당신의 오늘을 그려보세요</div>
        </div>
        <div id={css.user_img_box}>
            <img src={profile}/>
        </div>
    </div>
}

export default Header