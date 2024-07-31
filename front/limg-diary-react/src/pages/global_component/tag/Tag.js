import css from './Tag.module.scss'

import { useState } from 'react'

import DeleteIconImage from './img/delete_icon.png';

// 블루태그(선택됨)
function SellectedBlueTag({setTagArr, tag}){

    return (
        <button id={css.tag_selected} onClick={()=>{
            setTagArr()
        }}>
            {tag.tag}
        </button>
    )
}
// 블루태그(선택 안됨)
function UnsellectedBlueTag({tag, setTagArr}){

    return (
        <button id={css.tag_unselected} onClick={()=>{
            setTagArr()
        }}>
            {tag.tag}
        </button>
    )
}

// 블루태그(선택 안됨)
function NoActionBlueTag({tag}){

    return (
        <button id={css.tag_unselected}>
            {tag}
        </button>
    )
}

// 회색태그
function GrayTag({tag}){
    return (
        <div id={css.gray_tag}>
            {tag}
        </div>
    )
}

// 삭제되는 회색 태그
function RemoveableGrayTag({tag, func}){

    const [sellected, setSellected] = useState(tag.state)

    return (
        <div id={css.delete_gray_tag} className={css.container}>
            {tag}
            <div id={css.deleteBox} className={css.container} onClick={()=>{
                func()
            }}>
                <img id={css.deleteImg} src={DeleteIconImage}/>
            </div>
        </div>
    )
}

export {SellectedBlueTag, UnsellectedBlueTag, NoActionBlueTag, GrayTag, RemoveableGrayTag}