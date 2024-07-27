import css from './Button.module.scss'

// 단독 큰 버튼
function SingleButton({text, func}){
    return(
        <button id={css.single_button}>
            {text}
        </button>
    )
}

// 텍스트 버튼
function TextButton({text, func}){
    return(
        <button id={css.text_button}>
            {text}
        </button>
    )
}

export {SingleButton, TextButton};