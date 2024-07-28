import css from './Tag.module.scss'

import { useState } from 'react'

function BlueTag({text, func}){
    return (
        <button id={css.tag_selected}>
            {text}
        </button>
    )
}

export {BlueTag}