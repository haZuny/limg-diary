import css from './SettingPage.module.scss'

import { useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import Header from '../global_component/header/Header'
import Footer from '../global_component/fotter/Fotter'
import WhiteBox from '../global_component/white_box/WhiteBox'
import { TextButton } from '../global_component/button/Button'
import Modal from '../global_component/modal/Modal'

import ArrowImg from './img/arrow.png'
import Password_change_body from './password_change_body/Password_change_body'
import Nickname_change_body from './nickname_change_body copy/Nickname_change_body'

function SettingPage() {

    // Ref
    const bodyRef = useRef()

    // Navigate
    const navigate = useNavigate()

    // Modal state
    // 비밀번호 변경
    const [changePasswordModalState, setChangePasswordModalState] = useState(false)
    // 닉네임 변경
    const [changeNicknameModalState, setChangeNicknameModalState] = useState(false)

    return (
        <div id={CSS.root_container} className={css.page_root_container}>

            <Header parentBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />

            <div id={css.body_container} className={css.container} ref={bodyRef}>

                <WhiteBox title={'개인 정보 설정'} child={
                    <div id={css.setting_container}>
                        {/* 닉네임 변경 */}
                        <div className={[css.setting_list_box, css.container].join(" ")} onClick={()=>{
                            setChangeNicknameModalState(true)
                        }}>
                            <div className={[css.setting_list_text, css.container].join(" ")}>닉네임 변경</div>

                            <div className={css.setting_list_arrow_box}>
                                <img src={ArrowImg} />
                            </div>
                        </div>
                        <hr className={css.divider} />

                        {/* 비밀번호 변경 */}
                        <div className={[css.setting_list_box, css.container].join(" ")} onClick={() => {
                            setChangePasswordModalState(true)
                        }}>
                            <div className={[css.setting_list_text, css.container].join(" ")}>비밀번호 변경</div>

                            <div className={css.setting_list_arrow_box}>
                                <img src={ArrowImg} />
                            </div>
                        </div>
                        <hr className={css.divider} />


                    </div>
                } />

                <TextButton text={'로그아웃'} func={() => {
                    navigate('/login', { replace: true })
                }} />

                <div id={css.bottom_space} />

            </div>

            {/* 닉네임 변경 모달 */}
            {changeNicknameModalState && <Modal title={'닉네임 변경'} body={
                <Nickname_change_body modal_off_handle={()=>setChangeNicknameModalState(false)}/>
            } modalOffHandle={()=>setChangeNicknameModalState(false)}/>}

            {/* 비밀번호 변경 모달 */}
            {changePasswordModalState && <Modal title={'비밀번호 변경'} body={
                <Password_change_body modal_off_handle={() => setChangePasswordModalState(false)} />
            } modalOffHandle={() => setChangePasswordModalState(false)} />}


        </div>
    )
}


export default SettingPage