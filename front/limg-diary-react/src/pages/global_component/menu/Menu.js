import { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import css from './Menu.module.scss'

import DefaultProfileImg from '../../resource/img/profile.jpeg'
import RestApiHelper from '../../../Authentication'


function Menu({setUnvisible}){

    // visible state
    const [visible, setVisible] = useState(false)

    // ref
    const [emailState, setEmailState] = useState('...')
    const [nicknameState, setNicknameState] = useState('...')

    // navigate
    const navigate = useNavigate()

    // 등장 애니메이션
    useEffect(()=>{
        setTimeout(()=>setVisible(true), 1)
        loadData().then((res)=>{
            if(!res){
                alert("정보 로드 실패")
            }
        })
    }, [])

    // 정보 로드
    async function loadData(){
        const res = await RestApiHelper.sendRequest("/user/self", "GET", {});
        console.log("[GET] /user/self", res)
        if (res != null && res.status == '200'){
            setEmailState(res.data.data.username);
            setNicknameState(res.data.data.nickname);
            return true;
        }
        else{
            return false;
        }
    }

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
                        <div id={css.email}>{emailState}</div>
                        <div id={css.nickname}>{nicknameState}</div>
                    </div>

                    {/* 하단 메뉴들 */}
                    <div id={css.lower_box}>
                        <div className={[css.menu_text, css.container].join(" ")} onClick={()=>{
                            navigate('/setting')
                        }}>개인 정보 설정</div>

                        <div className={[css.menu_text, css.container].join(" ")} onClick={()=>{
                            navigate('/search')
                        }}>이전 일기 보기</div>

                        <div className={[css.menu_text, css.container].join(" ")} onClick={()=>{
                            navigate('/challenge')
                        }}>내가 취득한 업적</div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Menu