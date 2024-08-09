import css from './Nickname_change_body.module.scss'

import { SingleSmallButton, TextButton } from '../../global_component/button/Button'
import { useEffect, useRef } from 'react'
import RestApiHelper from '../../../Authentication'

function Nickname_change_body({modal_off_handle}){
    // ref
    const newNicknameRef = useRef()
    const passwordRef = useRef()

    return (
        <div id={css.modal_body_box} className={css.container}>

            <div id={css.input_box} className={css.container}>
                <input className={css.input} placeholder='새로운 닉네임' ref={newNicknameRef}/>
                <input className={css.input} placeholder='패스워드' ref={passwordRef} type='password'/>
            </div>

            <div id={css.button_box} className={css.container}>
                <SingleSmallButton text={'변경 완료'}func={async ()=>{
                    const newNickname = newNicknameRef.current.value
                    const password = passwordRef.current.value

                    if (newNickname == '' || password == ''){
                        alert('빈 항목이 존재합니다.')
                    }

                    const res = await RestApiHelper.sendRequest("/user/modify", "PATCH", {body:{
                        'nickname' : newNickname,
                        'password': password
                    }})

                    console.log("[patch] /user/modify", res);
                    
                    if (res != null && res.status == '200'){
                        alert('닉네임이 변경되었습니다.')
                        modal_off_handle()
                        return;
                    }
                    else{
                        alert('패스워드가 일치하지 않습니다.');
                    }
                }}/>
                <TextButton text={'취소'} func={modal_off_handle}></TextButton>
            </div>
        </div>
    )
}

export default Nickname_change_body