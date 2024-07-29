import css from './Tag.module.scss'

import { useState } from 'react'

function SellectableTag({text, tag}){

    const [sellected, setSellected] = useState(tag.state)

    return (
        <button id={sellected?css.tag_selected:css.tag_unselected} onClick={()=>{
            tag.changeState()
            setSellected(tag.state)
        }}>
            {text}
        </button>
    )
}

export {SellectableTag as BlueTag}