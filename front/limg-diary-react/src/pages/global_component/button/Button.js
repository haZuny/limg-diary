import css from './Button.module.scss'


// 단독 큰 버튼
function SingleButton({text, func}){
    return(
        <button id={css.single_button} className={css.boxButton} onClick={()=>func()}>
            {text}
        </button>
    )
}

// 단독 작은 버튼
function SingleSmallButton({text, func}){
    return(
        <button id={css.single_small_button} className={css.boxButton} onClick={()=>func()}>
            {text}
        </button>
    )
}

// 더블 작은 버튼
function DoubleSmallButton({text, func}){
    return(
        <button id={css.double_small_button} className={css.boxButton} onClick={()=>func()}>
            {text}
        </button>
    )
}

// 텍스트 버튼
function TextButton({text, func}){
    return(
        <button id={css.text_button} onClick={()=>func()}>
            {text}
        </button>
    )
}

export {SingleButton, SingleSmallButton, DoubleSmallButton, TextButton};