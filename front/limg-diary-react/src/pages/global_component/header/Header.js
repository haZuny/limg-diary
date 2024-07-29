import css from './Header.module.scss'

import { useEffect, useState } from 'react';

import logo_icon from './../../resource/img/logo.png'
import title_img from './title.png'
import profile from './profile.jpeg'




function Header({authorized, parendBodyRef}){

    const [visible, setVisible] = useState(true)
    let lastScrollY = 0

    function scrollHandle(){
        // 스크롤 업
        if (lastScrollY > parendBodyRef.current.scrollTop){
            setVisible(true)
        }
        // 스크롤 다운
        else{
            setVisible(false)
        }
        lastScrollY = parendBodyRef.current.scrollTop
    }

    useEffect(()=>{
        parendBodyRef.current.addEventListener('scroll', scrollHandle)
        return ()=>{parendBodyRef.current.removeEventListener("scroll", scrollHandle)}
    }, [])

    return (
        <div className={[css.container, css.root_container, !visible&&css.root_container_unvisible].join(" ")}>
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