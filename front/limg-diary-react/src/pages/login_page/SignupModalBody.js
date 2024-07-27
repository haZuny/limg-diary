import css from './SignupModalBody.module.scss'

import { SingleSmallButton, TextButton } from '../global_component/button/Button'

function SignupModalBody(){
    return(
        <div id={css.modal_body_box} className={css.container}>

            <div id={css.input_box} className={css.container}>
                <input className={css.input} placeholder='이메일'/>
                <input className={css.input} placeholder='닉네임'/>
                <input className={css.input} placeholder='패스워드'/>
                <input className={css.input} placeholder='패스워드 확인'/>
            </div>

            <div id={css.button_box} className={css.container}>
                <SingleSmallButton text={'회원가입'}/>
                <TextButton text={'취소'}></TextButton>
            </div>

        </div>
    )
}

export default SignupModalBody