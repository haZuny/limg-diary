import { useEffect, useRef, useState } from 'react'
import css from './Fotter.module.scss'

function Footer({parentBodyRef}){  

    // 위로 스크롤 할때만 나옴
    const [visible, setVisible] = useState(true)
    let lastScrollY = 0


    // 스크롤 이벤트
    function scrollHandle(){
        // 스크롤 업
        if (lastScrollY > parentBodyRef.current.scrollTop){
            setVisible(true)
        }
        // 스크롤 다운
        else{
            setVisible(false)
        }
        lastScrollY = parentBodyRef.current.scrollTop
    }

    // 이벤트 등록
    useEffect(()=>{
        parentBodyRef.current.addEventListener('scroll', scrollHandle)
        // return ()=>{parentBodyRef.current.removeEventListener("scroll", scrollHandle)}
    }, [])

    

    return (
        <div className={[css.container, css.root_container, !visible&&css.root_container_unvisible].join(" ")}>
            <div id={css.text} className={css.container}>
                This app is developped by Hayden & OnebinGo<br/>
                ‘Karlo’ of Kakao supports<br/>
                Contect: hj3175791@gmail.com
            </div>
        </div>
    )
}

export default Footer