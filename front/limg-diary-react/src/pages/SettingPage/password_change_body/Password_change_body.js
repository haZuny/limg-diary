import css from './Password_change_body.module.scss'

import { SingleSmallButton, TextButton } from '../../global_component/button/Button'

function Password_change_body({modal_off_handle}){
    // 이전 비밀번호 불러오기

    return (
        <div id={css.modal_body_box} className={css.container}>

            <div id={css.input_box} className={css.container}>
                <input className={css.input} placeholder='새로운 패스워드'/>
                <input className={css.input} placeholder='패스워드 확인'/>
                <input className={css.input} placeholder='이전 패스워드'/>
            </div>

            <div id={css.button_box} className={css.container}>
                <SingleSmallButton text={'변경 완료'}/>
                <TextButton text={'취소'} func={modal_off_handle}></TextButton>
            </div>
        </div>
    )
}

export default Password_change_body