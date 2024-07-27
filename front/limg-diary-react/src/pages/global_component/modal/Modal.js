import css from './Modal.module.scss'

import {useRef} from 'react'


function Modal({title, body, modalOffHandle}){
    return (
        <ModalBG title={title} body={body} modalOffHandle={modalOffHandle}/>
    )
}

// 반투명 배경
function ModalBG({title, body, modalOffHandle}){
    // modalBG_ref = useRef()

    return (
        <div id={css.modal_bg} className={css.container} onClick={(e)=>{
            if(e.target == e.currentTarget){
                modalOffHandle()
            }
        }}>
            <ModalContent title={title} body={body}/>
        </div>
    )
}

// 모달창 박스
function ModalContent({title, body}){


    return (
        <div id={css.modal_content_box} className={css.container}>
            <div id={css.title}>{title}</div>
            <div id={css.body} className={css.container}>{body}</div>
        </div>
    )
}

export default Modal