import css from './Header.module.scss'

import { useEffect, useState } from 'react';

import logo_icon from './../../resource/img/logo.png'
import title_img from './title.png'
import profile from './profile.jpeg'




function Header({authorized}){

    return (
        <div className={css.container} id={css.container}>
            <div className={css.container} id={css.title_container}>
                {/* 로고 이미지 */}
                <div id={css.logo_box}><img src={logo_icon}/></div>
                {/* 공백 */}
                <div id={css.title_margin_box}></div>
                {/* 타이틀 */}
                <div id={css.title_img_box}><img src={title_img}/></div>
            </div>
            {/* 프로필 이미지 */}
            <div id={css.user_img_box}>
                <img src={profile}/>
            </div>
        </div>
    )
}

export default Header