import css from './Password_change_body.module.scss'

import { SingleSmallButton, TextButton } from '../../global_component/button/Button'
import { useRef } from 'react'

import RestApiHelper from '../../../Authentication'

function Password_change_body({modal_off_handle}){
    // ref
    const newPasswordRef = useRef()
    const newPasswordCheckRef = useRef()
    const passwordRef = useRef()

    return (
        <div id={css.modal_body_box} className={css.container}>

            <div id={css.input_box} className={css.container}>
                <input className={css.input} placeholder='새로운 패스워드' type='password' ref={newPasswordRef}/>
                <input className={css.input} placeholder='패스워드 확인' type='password' ref={newPasswordCheckRef}/>
                <input className={css.input} placeholder='이전 패스워드' type='password' ref={passwordRef}/>
            </div>

            <div id={css.button_box} className={css.container}>
                <SingleSmallButton text={'변경 완료'} func={async ()=>{
                    const newPassword = newPasswordRef.current.value
                    const newPasswordCheck = newPasswordCheckRef.current.value
                    const password = passwordRef.current.value

                    if (newPassword == '' || newPasswordCheck == '' || password == ''){
                        alert('빈 항목이 존재합니다.')
                        return
                    }

                    if (newPassword != newPasswordCheck){
                        alert('패스워드가 서로 일치하지 않습니다.')
                        return
                    }

                    const res = await RestApiHelper.sendRequest("/user/modify", "PATCH", {body:{
                        'new_password' : newPassword,
                        'new_password_check' : newPasswordCheck,
                        'password': password
                    }})

                    console.log("[patch] /user/modify", res);

                    if (res != null && res.status == '200'){
                        alert('패스워드가 변경되었습니다.')
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

export default Password_change_body