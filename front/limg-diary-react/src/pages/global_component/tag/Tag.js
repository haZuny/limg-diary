import css from './Tag.module.scss'

import { useEffect, useState } from 'react'

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

function GrayTag({tag}){
    return (
        <div id={css.gray_tag}>
            {tag}
        </div>
    )
}


function RemoveableGrayTag({tag}){

    const [sellected, setSellected] = useState(tag.state)

    return (
        <div id={css.delete_gray_tag} className={css.container}>
            {tag}
            <div id={css.deleteBox} className={css.container}>
                <img id={css.deleteImg} scr={DeleteIconImage}/>
            </div>
        </div>
    )
}

export {SellectedBlueTag, UnsellectedBlueTag, GrayTag, RemoveableGrayTag}