import { useEffect, useState } from 'react'
import css from './Menu.module.scss'

import DefaultProfileImg from '../../resource/img/profile.jpeg'

function Menu({setUnvisible}){

    const [visible, setVisible] = useState(false)

    // 등장 애니메이션
    useEffect(()=>{
        const startTimer = setInterval(()=>{
            setVisible(true)
            clearInterval(startTimer)
        }, 100)
    }, [])

    return (
        <div id={css.root_container}>
            {/* 반투명 박스 */}
            <div id={css.bg_box} className={css.container} onClick={(e)=>{
                if (e.target == e.currentTarget){
                    // 메뉴 사라지는 애니메이션
                    setVisible(false)
                    // 메뉴 제거
                    setTimeout(()=>setUnvisible(), 200)
                }
            }}>

                {/* 메뉴 박스 */}
                <div className={[!visible&&css.menu_box_unvisible, css.menu_box, css.container].join(" ")}>
                    {/* 상단 프로필 */}
                    <div id={css.upper_box} className={css.container}>
                        <div id={css.profile_img_box}><img src={DefaultProfileImg}/></div>
                        <div id={css.email}>hj3175791@gmail.com</div>
                        <div id={css.nickname}>Hayden</div>
                    </div>

                    {/* 하단 메뉴들 */}
                    <div id={css.lower_box}>
                        <div className={[css.menu_text, css.container].join(" ")}>개인 정보 설정</div>
                        <div className={[css.menu_text, css.container].join(" ")}>이전 일기 보기</div>
                        <div className={[css.menu_text, css.container].join(" ")}>내가 취득한 업적</div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Menu