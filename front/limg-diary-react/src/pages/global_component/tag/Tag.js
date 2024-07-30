import css from './Tag.module.scss'

import { useEffect, useState } from 'react'

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

    const [sellected, setSellected] = useState(tag.state)

    return (
        tag.state&&<button id={css.gray_tag} onClick={()=>{
            tag.changeState()
            setSellected(tag.state)
        }}>
            {tag.tag}
        </button>
    )
}

export {SellectedBlueTag, UnsellectedBlueTag, GrayTag}