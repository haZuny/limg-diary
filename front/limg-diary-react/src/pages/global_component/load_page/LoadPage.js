import { useEffect, useState } from 'react'
import css from './LoadPage.module.scss'
import Icon from './loadicon.png'

function LoadPage(){

    const fullText = '그림을 생성 중 입니다. 잠시만 기다려 주세요.'

    let [loadText, setLoadText] = useState('')
    let [rotate, setRotate] = useState(0)

    // font animation
    function textAnimation(){
        if(loadText.length == fullText.length){
            loadText = fullText.charAt(0)
            setLoadText(loadText)
        }
        else{
            let addChar = '';
            do{
                addChar = fullText.charAt(loadText.length)
                loadText += addChar
                setLoadText(loadText)
            } while(addChar == ' ')
        }
    }

    // rotate animation
    function rotateAnimation(){
        rotate += 1
        if (rotate > 360) rotate = 0
        setRotate(rotate)
    }

    useEffect((()=>{
        const textTimer = setInterval((textAnimation), 150)
        const rotateTimer = setInterval((rotateAnimation), 1)

        return(()=>{
            clearInterval(textTimer)
            clearInterval(rotateTimer)
        })
    }), [])

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <div id={css.text}>{loadText}</div>
            <div id={css.icon_box} style={{
                transform: `rotate( ${rotate}deg)`
            }}>
                <img src={Icon}/>
            </div>
        </div>
    )
}

export default LoadPage